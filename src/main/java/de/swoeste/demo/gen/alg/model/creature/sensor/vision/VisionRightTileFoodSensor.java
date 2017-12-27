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

import de.swoeste.demo.gen.alg.model.creature.Creature;
import de.swoeste.demo.gen.alg.model.creature.CreatureAttribute;
import de.swoeste.demo.gen.alg.model.polygon.Vector;
import de.swoeste.demo.gen.alg.model.world.World;

/**
 * This sensor detects the food amount of the tile which intersects the right outer point of the view area.
 *
 * <pre>
 *      * * * * *
 *      * * * * *
 *      * * | * *
 *      * * | * *
 *      * * | * 0
 *      * * | / *
 *      * * x * *
 *      * * * * *
 * </pre>
 *
 * @author swoeste
 */
public class VisionRightTileFoodSensor extends AbstractTileFoodVisionSensor {

    public VisionRightTileFoodSensor(final World world, final Creature creature) {
        super(world, creature);
    }

    /** {@inheritDoc} */
    @Override
    protected Vector getPosition(final World world, final Creature creature, final Vector position) {
        final int viewDirectionDegrees = creature.getAttributeValue(CreatureAttribute.VIEW_DIRECTION);
        final int viewArcDegrees = creature.getAttributeValue(CreatureAttribute.VIEW_ARC);
        final int viewDistance = creature.getAttributeValue(CreatureAttribute.VIEW_DISTANCE);

        final double rightViewAreaBorderRadians = Math.toRadians(viewDirectionDegrees + (viewArcDegrees / 2.0));
        return position.project(rightViewAreaBorderRadians, viewDistance);
    }

}
