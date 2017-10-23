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
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.swoeste.demo.gen.alg.model.creature.sensor.HealthSensor;
import de.swoeste.demo.gen.alg.model.creature.sensor.Sensor;
import de.swoeste.demo.gen.alg.model.creature.sensor.StarvationSensor;
import de.swoeste.demo.gen.alg.model.creature.skill.EatingSkill;
import de.swoeste.demo.gen.alg.model.creature.skill.Skill;

/**
 * @author swoeste
 */
public class CreatureFactory {

    private static final Logger LOG    = LoggerFactory.getLogger(CreatureFactory.class);

    private static final Random RANDOM = new Random();

    private CreatureFactory() {
        // hidden
    }

    public static final Creature createCreature() {
        final Gender gender = Gender.valueOf(RANDOM.nextInt(2));
        final String name = NameFactory.getRandomName(gender);
        final String lastname = NameFactory.getRandomLastname();
        return createCreature(gender, name, lastname);
    }

    public static final Creature createCreature(final Gender gender, final String name, final String lastname) {
        final Creature creature = new Creature(gender, name, lastname);

        final List<Sensor> sensors = new ArrayList<>();
        sensors.add(new HealthSensor(creature));
        sensors.add(new StarvationSensor(creature));
        creature.initializeSensors(sensors);

        final List<Skill> skills = new ArrayList<>();
        skills.add(new EatingSkill(creature));
        creature.initializeSkills(skills);

        creature.initializeNetwork();

        LOG.info("{} {} has been created out of dust and love", name, lastname); //$NON-NLS-1$

        return creature;
    }

}
