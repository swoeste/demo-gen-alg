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
import de.swoeste.demo.gen.alg.model.polygon.AlignedRectangle;
import de.swoeste.demo.gen.alg.model.polygon.Vector;
import de.swoeste.demo.gen.alg.model.world.World;

/**
 * @author swoeste
 */
public abstract class AbstractCreatureVisionSensor extends AbstractVisionSensor {

    public AbstractCreatureVisionSensor(final World world, final Creature creature) {
        super(world, creature);
    }

    /** {@inheritDoc} */
    @Override
    protected double getSensorValue(final World world, final Creature creature, final Vector position) {
        final AlignedRectangle detectionShape = getDetectionShape(creature, position);
        final List<Creature> creaturesInArea = world.getCreaturesInArea(detectionShape);

        if (collidesWithCreature(creature, position, creaturesInArea)) {
            return 1.0;
        } else {
            return -1.0;
        }
    }

    protected AlignedRectangle getDetectionShape(final Creature creature, final Vector position) {
        final int viewDirectionDegrees = creature.getAttributeValue(CreatureAttribute.VIEW_DIRECTION);
        final int viewArcDegrees = creature.getAttributeValue(CreatureAttribute.VIEW_ARC);
        final int viewDistance = creature.getAttributeValue(CreatureAttribute.VIEW_DISTANCE);

        final Vector p1 = position;
        final Vector p2 = position.project(Math.toRadians(viewDirectionDegrees + (viewArcDegrees / 2.0)), viewDistance);
        final Vector p3 = position.project(Math.toRadians(viewDirectionDegrees), viewDistance);
        final Vector p4 = position.project(Math.toRadians(viewDirectionDegrees - (viewArcDegrees / 2.0)), viewDistance);

        final int minX = getMinX(p1, p2, p3, p4);
        final int maxX = getMaxX(p1, p2, p3, p4);
        final int minY = getMinY(p1, p2, p3, p4);
        final int maxY = getMaxY(p1, p2, p3, p4);

        return new AlignedRectangle(minX, minY, maxX - minX, maxY - minY);
    }

    private int getMaxX(final Vector... points) {
        int max = Integer.MIN_VALUE;
        for (Vector vector : points) {
            max = Math.max(max, (int) vector.getX());
        }
        return max;
    }

    private int getMinX(final Vector... points) {
        int min = Integer.MAX_VALUE;
        for (Vector vector : points) {
            min = Math.min(min, (int) vector.getX());
        }
        return min;
    }

    private int getMaxY(final Vector... points) {
        int max = Integer.MIN_VALUE;
        for (Vector vector : points) {
            max = Math.max(max, (int) vector.getY());
        }
        return max;
    }

    private int getMinY(final Vector... points) {
        int min = Integer.MAX_VALUE;
        for (Vector vector : points) {
            min = Math.min(min, (int) vector.getY());
        }
        return min;
    }

    protected abstract boolean collidesWithCreature(final Creature creature, final Vector position, final List<Creature> creaturesInArea);

}
