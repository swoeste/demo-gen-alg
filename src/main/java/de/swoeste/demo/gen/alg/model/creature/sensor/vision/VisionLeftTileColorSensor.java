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

import de.swoeste.demo.gen.alg.model.Vector;
import de.swoeste.demo.gen.alg.model.creature.Creature;
import de.swoeste.demo.gen.alg.model.creature.CreatureAttribute;
import de.swoeste.demo.gen.alg.model.world.World;
import de.swoeste.demo.gen.alg.model.world.tile.Tile;
import de.swoeste.demo.gen.alg.model.world.tile.TileAttribute;

/**
 * @author swoeste
 */
public class VisionLeftTileColorSensor extends AbstractVisionSensor {

    private static final Logger LOG = LoggerFactory.getLogger(VisionLeftTileColorSensor.class);

    public VisionLeftTileColorSensor(final World world, final Creature creature) {
        super(world, creature);
    }

    /** {@inheritDoc} */
    @Override
    protected double getSensorValue(final World world, final Creature creature, final Vector position) {
        final int viewDirectionDegrees = creature.getAttributeValue(CreatureAttribute.VIEW_DIRECTION);
        final double viewDirectionRadians = Math.toRadians(viewDirectionDegrees + 45.0);

        final int visionSize = creature.getAttributeValue(CreatureAttribute.VISION_SIZE);
        final Vector visionPosition = position.project(viewDirectionRadians, visionSize);

        if (world.isPositionInWorld(visionPosition)) {
            final Tile tile = world.getTile(visionPosition);
            final int r = tile.getAttributeValue(TileAttribute.COLOR_R);
            final int g = tile.getAttributeValue(TileAttribute.COLOR_G);
            final int b = tile.getAttributeValue(TileAttribute.COLOR_B);
            return getNumberRepresentation(r, g, b);
        } else {
            return 0.0;
        }
    }

}
