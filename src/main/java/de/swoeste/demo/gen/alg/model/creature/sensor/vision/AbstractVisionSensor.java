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
package de.swoeste.demo.gen.alg.model.creature.sensor.vision;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.swoeste.demo.gen.alg.model.RGBColor;
import de.swoeste.demo.gen.alg.model.Vector;
import de.swoeste.demo.gen.alg.model.creature.Creature;
import de.swoeste.demo.gen.alg.model.creature.CreatureAttribute;
import de.swoeste.demo.gen.alg.model.creature.sensor.Sensor;
import de.swoeste.demo.gen.alg.model.world.World;

/**
 * @author swoeste
 */
public abstract class AbstractVisionSensor implements Sensor {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractVisionSensor.class);

    private final World         world;
    private final Creature      creature;

    public AbstractVisionSensor(final World world, final Creature creature) {
        this.world = world;
        this.creature = creature;
    }

    /** {@inheritDoc} */
    @Override
    public final double getSensorValue() {
        final int x = this.creature.getAttributeValue(CreatureAttribute.POSITION_X);
        final int y = this.creature.getAttributeValue(CreatureAttribute.POSITION_Y);
        final Vector position = new Vector(x, y);
        return getSensorValue(this.world, this.creature, position);
    }

    protected abstract double getSensorValue(World world, Creature creature, Vector position);

    protected final double getNumberRepresentation(final RGBColor color) {
        final int max = 16777215;
        final int rgb = (color.getRed() << 16) + (color.getGreen() << 8) + color.getBlue();
        return (rgb - (max / 2.0)) / (max / 2.0);
    }

}
