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
import java.util.List;
import java.util.Map;

import de.swoeste.demo.gen.alg.model.creature.receptor.AttributeReceptor;
import de.swoeste.demo.gen.alg.model.creature.sensor.Sensor;
import de.swoeste.demo.gen.alg.model.creature.skill.Skill;
import de.swoeste.demo.gen.alg.model.neural.network.Network;
import de.swoeste.demo.gen.alg.model.neural.network.activation.ActivationFunction;
import de.swoeste.demo.gen.alg.model.polygon.AlignedRectangle;
import de.swoeste.demo.gen.alg.position.Identifiable;
import de.swoeste.demo.gen.alg.position.Shapeaware;

/**
 * @author swoeste
 */
public class Creature implements Identifiable, Shapeaware {

    // TODO add a parent attribute?

    private final int                       id;
    private final Gender                    gender;
    private final String                    name;
    private final String                    lastname;

    private Network                         network;

    private List<Sensor>                    sensors;
    private List<AttributeReceptor>         receptors;
    private List<Skill>                     skills;

    private Map<CreatureAttribute, Integer> attributes;

    Creature(final int id, final Gender gender, final String name, final String lastname) {
        this.id = id;
        this.gender = gender;
        this.name = name;
        this.lastname = lastname;
    }

    void setNetwork(final Network network) {
        this.network = network;
    }

    void setSensors(final List<Sensor> sensors) {
        this.sensors = sensors;
    }

    void setReceptors(final List<AttributeReceptor> receptors) {
        this.receptors = receptors;
    }

    void setSkills(final List<Skill> skills) {
        this.skills = skills;
    }

    void setAttributes(final Map<CreatureAttribute, Integer> attributes) {
        this.attributes = attributes;
    }

    /** {@inheritDoc} */
    @Override
    public int getId() {
        return this.id;
    }

    public Gender getGender() {
        return this.gender;
    }

    public String getName() {
        return this.name;
    }

    public String getLastname() {
        return this.lastname;
    }

    @Override
    public AlignedRectangle getShape() {
        final int posX = getAttributeValue(CreatureAttribute.POSITION_X);
        final int posY = getAttributeValue(CreatureAttribute.POSITION_Y);
        final int size = getAttributeValue(CreatureAttribute.SIZE);
        return new AlignedRectangle(posX, posY, size, size);
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

            final ActivationFunction activationFunction = this.network.getActivationFunction();
            receptor.receive(activationFunction.normalize(output[i]));
        }

        // activate skill depending on activation attributes
        for (Skill skill : this.skills) {
            final CreatureAttribute activationAttribute = skill.getActivationAttribute();
            final int activationAttributeValue = getAttributeValue(activationAttribute);

            final ActivationFunction activationFunction = this.network.getActivationFunction();
            if (activationFunction.isActive(activationAttributeValue)) {
                skill.doPerform();
            } else {
                skill.doNotPerform();
            }
        }

        increaseAttributeByValue(CreatureAttribute.AGE, 1, Integer.MAX_VALUE);

        // TODO - Idea
        // Add a flag to only execute one skill per tick based on the highest activation value.
        // Therefore we somehow have to get the original values from the receptor!
    }

    public boolean hasAttributeValue(final CreatureAttribute attribute) {
        return this.attributes.containsKey(attribute);
    }

    public int getAttributeValue(final CreatureAttribute attribute) {
        return this.attributes.get(attribute);
    }

    public int setAttributeValue(final CreatureAttribute attribute, final int value) {
        return this.attributes.put(attribute, value);
    }

    public int increaseAttributeByValue(final CreatureAttribute attribute, final int value, final int maxValue) {
        final Integer currentValue = this.attributes.get(attribute);
        final Integer newValue = Math.min(currentValue + value, maxValue);
        this.attributes.put(attribute, newValue);
        return newValue;  // this is the new value!
    }

    public int decreaseAttributeByValue(final CreatureAttribute attribute, final int value, final int minValue) {
        final Integer currentValue = this.attributes.get(attribute);
        final Integer newValue = Math.max(currentValue - value, minValue);
        this.attributes.put(attribute, newValue);
        return newValue; // this is the new value!
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
