/*-
 * Copyright (C) 2017 Sebastian Woeste
 *
 * Licensed to Sebastian Woeste under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership. I license this file to You under
 * the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License
 * at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.swoeste.demo.gen.alg.model.creature;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import de.swoeste.demo.gen.alg.model.Rectangle;
import de.swoeste.demo.gen.alg.model.creature.receptor.AttributeReceptor;
import de.swoeste.demo.gen.alg.model.creature.sensor.Sensor;
import de.swoeste.demo.gen.alg.model.creature.skill.Skill;
import de.swoeste.demo.gen.alg.model.neural.network.Network;
import de.swoeste.demo.gen.alg.position.Identifiable;
import de.swoeste.demo.gen.alg.position.Shapeaware;

/**
 * @author swoeste
 */
public class Creature implements Identifiable, Shapeaware {

    private final int                             id;
    private final Gender                          gender;
    private final String                          name;
    private final String                          lastname;

    private final List<Sensor>                    sensors;
    private final List<AttributeReceptor>         receptors;
    private final Map<CreatureAttribute, Integer> attributes;
    private final List<Skill>                     skills;

    private Network                               network;

    public Creature(final int id, final Gender gender, final String name, final String lastname) {
        this.id = id;
        this.gender = gender;
        this.name = name;
        this.lastname = lastname;

        this.sensors = new ArrayList<>();
        this.receptors = new ArrayList<>();
        this.skills = new ArrayList<>();
        this.attributes = new EnumMap<>(CreatureAttribute.class);

        initializeAttributes();
    }

    private void initializeAttributes() {
        for (CreatureAttribute attribute : CreatureAttribute.values()) {
            this.attributes.put(attribute, attribute.getDefaultValue());
        }
    }

    public void initializeSensors(final List<Sensor> sensors) {
        checkMultipleInitialization();
        this.sensors.clear();
        this.sensors.addAll(sensors);
    }

    public void initializeReceptors(final List<AttributeReceptor> receptors) {
        checkMultipleInitialization();
        this.receptors.clear();
        this.receptors.addAll(receptors);
    }

    public void initializeSkills(final List<Skill> skills) {
        checkMultipleInitialization();
        this.skills.clear();
        this.skills.addAll(skills);
    }

    public void initializeNetwork(final int seed) {
        checkMultipleInitialization();
        final int[] hidden = { this.sensors.size() + 1, this.receptors.size() + 1 };
        this.network = new Network(this.sensors.size(), hidden, this.receptors.size(), seed);
    }

    private void checkMultipleInitialization() {
        if (this.network != null) {
            throw new IllegalStateException("The neural network has already been initialized!"); //$NON-NLS-1$
        }
    }

    public void update() {
        // input => network
        final double[] input = new double[this.sensors.size()];
        for (int i = 0; i < this.sensors.size(); i++) {
            final Sensor sensor = this.sensors.get(i);
            input[i] = sensor.getSensorValue();
        }

        // network => output
        final double[] output = this.network.feed(input);
        for (int i = 0; i < this.receptors.size(); i++) {
            final AttributeReceptor receptor = this.receptors.get(i);
            receptor.receive(output[i]);
        }

        // activate skill depending on activation attributes
        for (Skill skill : this.skills) {
            final CreatureAttribute activationAttribute = skill.getActivationAttribute();
            final int activationAttributeValue = getAttributeValue(activationAttribute);
            if (activationAttributeValue >= 0) {
                skill.doPerform();
            } else {
                skill.doNotPerform();
            }
        }

        increaseAttributeValue(CreatureAttribute.AGE, 1);

        // TODO add a flag to only execute one skill per tick based on the highest activation value.
        // Therefore we somehow have to get the original values from the receptor!
    }

    // TODO this is also a skill ?
    public void evolve() {

        // this wont happen? - a creature dies and the network may be used for a new creature but not for the dead one

        this.network.evolve();
    }

    // TODO this is also a skill ?
    public Creature reproduce(final Creature parent) {
        this.network.evolve(parent.network);
        // TODO
        // example keep 1 hidden layer of dad and one hidden layer of mother
        // switch randomly
        // + chance of x % for a mutation
        return null;
    }

    public int getAttributeValue(final CreatureAttribute attribute) {
        return this.attributes.get(attribute);
    }

    public int setAttributeValue(final CreatureAttribute attribute, final int value) {
        return this.attributes.put(attribute, value);
    }

    public int increaseAttributeValue(final CreatureAttribute attribute, final int value) {
        final Integer currentValue = this.attributes.get(attribute);
        final Integer newValue = currentValue + value;
        this.attributes.put(attribute, newValue);
        return newValue;  // this is the new value!
    }

    public int decreaseAttributeValue(final CreatureAttribute attribute, final int value) {
        final Integer currentValue = this.attributes.get(attribute);
        final Integer newValue = currentValue - value;
        this.attributes.put(attribute, newValue);
        return newValue; // this is the new value!
    }

    public String getName() {
        return this.name;
    }

    public String getLastname() {
        return this.lastname;
    }

    /** {@inheritDoc} */
    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public Rectangle getShape() {
        final int posX = getAttributeValue(CreatureAttribute.POSITION_X);
        final int posY = getAttributeValue(CreatureAttribute.POSITION_Y);
        final int size = getAttributeValue(CreatureAttribute.SIZE);
        return new Rectangle(posX, posY, size, size);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + this.id;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Creature other = (Creature) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Creature [id={0}, gender={1}, name={2}, lastname={3}]",  //$NON-NLS-1$
                this.id, this.gender, this.name, this.lastname);
    }

}
