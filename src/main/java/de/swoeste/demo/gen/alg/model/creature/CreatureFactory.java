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
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.swoeste.demo.gen.alg.model.Vector;
import de.swoeste.demo.gen.alg.model.creature.receptor.AttributeReceptor;
import de.swoeste.demo.gen.alg.model.creature.receptor.attribute.MoveDirectionReceptor;
import de.swoeste.demo.gen.alg.model.creature.receptor.attribute.MoveDistanceReceptor;
import de.swoeste.demo.gen.alg.model.creature.receptor.attribute.ViewDirectionReceptor;
import de.swoeste.demo.gen.alg.model.creature.receptor.attribute.skill.ChangeMoveDirectionReceptor;
import de.swoeste.demo.gen.alg.model.creature.receptor.attribute.skill.ChangeMoveDistanceReceptor;
import de.swoeste.demo.gen.alg.model.creature.receptor.attribute.skill.ChangeViewDirectionReceptor;
import de.swoeste.demo.gen.alg.model.creature.receptor.attribute.skill.EatReceptor;
import de.swoeste.demo.gen.alg.model.creature.receptor.attribute.skill.MoveReceptor;
import de.swoeste.demo.gen.alg.model.creature.sensor.HealthSensor;
import de.swoeste.demo.gen.alg.model.creature.sensor.HungerSensor;
import de.swoeste.demo.gen.alg.model.creature.sensor.Sensor;
import de.swoeste.demo.gen.alg.model.creature.sensor.vision.VisionCenterTileColorSensor;
import de.swoeste.demo.gen.alg.model.creature.sensor.vision.VisionCenterTileFoodSensor;
import de.swoeste.demo.gen.alg.model.creature.sensor.vision.VisionCurrentTileColorSensor;
import de.swoeste.demo.gen.alg.model.creature.sensor.vision.VisionCurrentTileFoodSensor;
import de.swoeste.demo.gen.alg.model.creature.sensor.vision.VisionLeftTileColorSensor;
import de.swoeste.demo.gen.alg.model.creature.sensor.vision.VisionLeftTileFoodSensor;
import de.swoeste.demo.gen.alg.model.creature.sensor.vision.VisionRightTileColorSensor;
import de.swoeste.demo.gen.alg.model.creature.sensor.vision.VisionRightTileFoodSensor;
import de.swoeste.demo.gen.alg.model.creature.skill.EatingSkill;
import de.swoeste.demo.gen.alg.model.creature.skill.MoveSkill;
import de.swoeste.demo.gen.alg.model.creature.skill.Skill;
import de.swoeste.demo.gen.alg.model.creature.skill.decision.ChangeMoveDirectionSkill;
import de.swoeste.demo.gen.alg.model.creature.skill.decision.ChangeMoveDistanceSkill;
import de.swoeste.demo.gen.alg.model.creature.skill.decision.ChangeViewDirectionSkill;
import de.swoeste.demo.gen.alg.model.neural.network.Network;
import de.swoeste.demo.gen.alg.model.neural.network.activation.ActivationFunction;
import de.swoeste.demo.gen.alg.model.neural.network.activation.ActivationFunctionFactory;
import de.swoeste.demo.gen.alg.model.neural.network.activation.ActivationFunctionType;
import de.swoeste.demo.gen.alg.model.world.World;
import de.swoeste.demo.gen.alg.util.NumberUtil;

/**
 * @author swoeste
 */
public class CreatureFactory {

    private static final Logger      LOG = LoggerFactory.getLogger(CreatureFactory.class);

    private int                      id;
    private final int                seed;
    private final Random             random;

    // TODO implement this a little bit more nicely
    private final ActivationFunction activationFunction;

    public CreatureFactory(final ActivationFunctionType activationFunctionType, final int seed) {
        this.seed = seed;
        this.random = new Random(seed);
        this.activationFunction = ActivationFunctionFactory.create(activationFunctionType);
    }

    public final Creature create(final World world, final Vector position) {
        final Gender gender = Gender.values()[this.random.nextInt(Gender.values().length)];
        final String name = NameFactory.getRandomName(gender);
        final String lastname = NameFactory.getRandomLastname();
        return create(world, position, gender, name, lastname);
    }

    public final Creature create(final World world, final Vector position, final Gender gender, final String name, final String lastname) {
        final Creature creature = new Creature(this.id, gender, name, lastname);

        final List<Sensor> sensors = createSensors(world, creature);
        creature.setSensors(sensors);

        final List<AttributeReceptor> receptors = createAttributeReceptors(creature);
        creature.setReceptors(receptors);

        final List<Skill> skills = createSkills(world, creature);
        creature.setSkills(skills);

        final Map<CreatureAttribute, Integer> attributes = createAttributes();
        creature.setAttributes(attributes);

        final Network network = createNetwork(sensors.size(), receptors.size());
        creature.setNetwork(network);

        creature.setAttributeValue(CreatureAttribute.POSITION_X, NumberUtil.round(position.getX()));
        creature.setAttributeValue(CreatureAttribute.POSITION_Y, NumberUtil.round(position.getY()));

        // TODO configurable ?
        creature.setAttributeValue(CreatureAttribute.SIZE, 10);

        // TODO configurable ?
        creature.setAttributeValue(CreatureAttribute.COLOR_R, this.random.nextInt(256));
        creature.setAttributeValue(CreatureAttribute.COLOR_G, this.random.nextInt(256));
        creature.setAttributeValue(CreatureAttribute.COLOR_B, this.random.nextInt(256));

        this.id++;

        LOG.debug("Created new creature: {}", creature); //$NON-NLS-1$
        return creature;
    }

    private Network createNetwork(final int sensors, final int receptors) {
        final int[] hidden = { sensors + 1, receptors + 1 };
        return new Network(sensors, hidden, receptors, this.seed, this.activationFunction);
    }

    private List<Skill> createSkills(final World world, final Creature creature) {
        final List<Skill> skills = new ArrayList<>();

        // Real Skills
        skills.add(new EatingSkill(world, creature));
        skills.add(new MoveSkill(world, creature));

        // Decision Skills
        skills.add(new ChangeMoveDirectionSkill(creature));
        skills.add(new ChangeMoveDistanceSkill(creature));
        skills.add(new ChangeViewDirectionSkill(creature));

        return skills;
    }

    private List<AttributeReceptor> createAttributeReceptors(final Creature creature) {
        final List<AttributeReceptor> receptors = new ArrayList<>();

        // Attributs
        receptors.add(new MoveDirectionReceptor(creature));
        receptors.add(new MoveDistanceReceptor(creature));
        receptors.add(new ViewDirectionReceptor(creature));

        // Skill Activation Attributes
        receptors.add(new EatReceptor(creature));
        receptors.add(new MoveReceptor(creature));
        receptors.add(new ChangeMoveDirectionReceptor(creature));
        receptors.add(new ChangeMoveDistanceReceptor(creature));
        receptors.add(new ChangeViewDirectionReceptor(creature));

        return receptors;
    }

    private List<Sensor> createSensors(final World world, final Creature creature) {
        final List<Sensor> sensors = new ArrayList<>();

        sensors.add(new HealthSensor(creature));
        sensors.add(new HungerSensor(creature));

        sensors.add(new VisionCurrentTileColorSensor(world, creature));
        sensors.add(new VisionCenterTileColorSensor(world, creature));
        sensors.add(new VisionLeftTileColorSensor(world, creature));
        sensors.add(new VisionRightTileColorSensor(world, creature));

        sensors.add(new VisionCurrentTileFoodSensor(world, creature));
        sensors.add(new VisionCenterTileFoodSensor(world, creature));
        sensors.add(new VisionLeftTileFoodSensor(world, creature));
        sensors.add(new VisionRightTileFoodSensor(world, creature));

        return sensors;
    }

    private Map<CreatureAttribute, Integer> createAttributes() {
        final Map<CreatureAttribute, Integer> attributes = new EnumMap<>(CreatureAttribute.class);
        for (CreatureAttribute attribute : CreatureAttribute.values()) {
            attributes.put(attribute, attribute.getDefaultValue());
        }
        return attributes;
    }

}
