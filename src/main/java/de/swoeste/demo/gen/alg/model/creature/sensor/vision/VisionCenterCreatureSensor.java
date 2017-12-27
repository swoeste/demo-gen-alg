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

import java.util.List;

import de.swoeste.demo.gen.alg.model.creature.Creature;
import de.swoeste.demo.gen.alg.model.creature.CreatureAttribute;
import de.swoeste.demo.gen.alg.model.polygon.Edge;
import de.swoeste.demo.gen.alg.model.polygon.Vector;
import de.swoeste.demo.gen.alg.model.world.World;

/**
 * This sensor detects other creatures in front of the sensor owner.
 *
 * <pre>
 *      * * * * *
 *      * * 0 * *
 *      * * | * *
 *      * * | * *
 *      * * | * *
 *      * * | * *
 *      * * x * *
 *      * * * * *
 * </pre>
 *
 * @author swoeste
 */
public class VisionCenterCreatureSensor extends AbstractCreatureVisionSensor {

    public VisionCenterCreatureSensor(final World world, final Creature creature) {
        super(world, creature);
    }

    /** {@inheritDoc} */
    @Override
    protected boolean collidesWithCreature(final Creature creature, final Vector position, final List<Creature> creaturesInArea) {
        final int viewDirectionDegrees = creature.getAttributeValue(CreatureAttribute.VIEW_DIRECTION);
        final int viewDistance = creature.getAttributeValue(CreatureAttribute.VIEW_DISTANCE);

        final double viewDirectionRadians = Math.toRadians(viewDirectionDegrees);
        final Vector lineOfSightEndPoint = position.project(viewDirectionRadians, viewDistance);

        final Edge lineOfSight = new Edge(position, lineOfSightEndPoint);
        for (final Creature creatureInArea : creaturesInArea) {
            if (lineOfSight.collidesWith(creatureInArea.getShape())) {
                return true;
            }
        }

        return false;
    }

}
