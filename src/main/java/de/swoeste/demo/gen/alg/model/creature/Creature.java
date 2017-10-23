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

import java.util.ArrayList;
import java.util.List;

import de.swoeste.demo.gen.alg.model.Network;
import de.swoeste.demo.gen.alg.model.creature.sensor.Sensor;
import de.swoeste.demo.gen.alg.model.creature.skill.Skill;

/**
 * @author swoeste
 */
public class Creature {

    // XXX: important all values must range from -1.0 to 1.0

    private final Gender       gender;
    private final String       name;
    private final String       lastname;

    private final List<Sensor> sensors;
    private final List<Skill>  skills;

    private Network            network;

    private double             health;
    private double             starvation;

    public Creature(final Gender gender, final String name, final String lastname) {
        this.gender = gender;
        this.name = name;
        this.lastname = lastname;
        this.sensors = new ArrayList<>();
        this.skills = new ArrayList<>();

        this.health = 1.0;
        this.starvation = 0;
    }

    public void initializeSensors(final List<Sensor> sensors) {
        // TODO add check, this is not possible after the network has been
        // created!
        this.sensors.clear();
        this.sensors.addAll(sensors);
    }

    public void initializeSkills(final List<Skill> skills) {
        // TODO add check, this is not possible after the network has been
        // created!
        this.skills.clear();
        this.skills.addAll(skills);
    }

    // TODO rename to brain? - so we have a better naming
    public void initializeNetwork() {
        // TODO add check, this is not possible after the network has been
        // created!
        final int[] hidden = { this.sensors.size() + 1, this.skills.size() + 1 };
        this.network = new Network(this.sensors.size(), hidden, this.skills.size());
    }

    // TODO better name
    public void tick() {
        final double[] input = new double[this.sensors.size()];

        for (int i = 0; i < this.sensors.size(); i++) {
            Sensor sensor = this.sensors.get(i);
            input[i] = sensor.getValue();
        }

        final double[] output = this.network.feed(input);

        for (int i = 0; i < this.skills.size(); i++) {
            Skill skill = this.skills.get(i);
            if (output[i] >= 0.0) {
                skill.doPerform();
            } else {
                skill.doNotPerform();
            }
        }
    }

    public void evolve() {
        this.network.evolve();
    }

    public Creature breed(final Creature parent) {

        this.network.breed(parent.network);

        // TODO
        // example keep 1 hidden layer of dad and one hidden layer of mother
        // switch randomly
        return null;
    }

    public boolean isAlive() {
        return this.health > -1.0;
    }

    public double getHealth() {
        return this.health;
    }

    public void gainHealth(final double amount) {
        final double newHealth = this.health + amount;
        this.health = getValueInRange(newHealth, -1.0, 1.0);
    }

    public double getStarvation() {
        return this.starvation;
    }

    public void gainStarvation(final double amount) {
        final double newStarvation = this.starvation + amount;
        this.starvation = getValueInRange(newStarvation, -1.0, 1.0);
    }

    private double getValueInRange(final double newValue, final double minValue, final double maxValue) {
        if (newValue > maxValue) {
            return maxValue;
        } else if (newValue < minValue) {
            return minValue;
        } else {
            return newValue;
        }
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        // TODO do not use toString only for names, to string should display
        // some information about the creature, we should provide the name via
        // getter and adjust loggign!
        return this.name + " " + this.lastname;
    }

}
