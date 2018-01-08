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
package de.swoeste.demo.gen.alg.model.creature.skill;

import de.swoeste.demo.gen.alg.event.SimpleEventType;
import de.swoeste.demo.gen.alg.model.creature.Creature;
import de.swoeste.demo.gen.alg.model.creature.CreatureAttribute;
import de.swoeste.demo.gen.alg.model.polygon.Vector;
import de.swoeste.demo.gen.alg.model.world.World;
import de.swoeste.demo.gen.alg.model.world.tile.Tile;
import de.swoeste.demo.gen.alg.model.world.tile.TileAttribute;
import de.swoeste.demo.gen.alg.util.NumberUtil;

/**
 * @author swoeste
 */
public class MoveSkill extends AbstractSkill {

    public MoveSkill(final World world, final Creature creature) {
        super(world, creature, CreatureAttribute.MOVE);
    }

    /** {@inheritDoc} */
    @Override
    protected void doPerform(final World world, final Creature creature) {
        final Vector currPosition = getPosition(creature);

        // convert orientation
        final int distance = creature.getAttributeValue(CreatureAttribute.MOVE_DISTANCE);
        final int directionDegrees = creature.getAttributeValue(CreatureAttribute.MOVE_DIRECTION);
        final double directionRadians = Math.toRadians(directionDegrees);

        // calculate velocity
        final Vector targetProjection = currPosition.project(directionRadians, distance);
        final Vector velocity = targetProjection.substract(currPosition).divide(distance);

        Vector prevPosition = currPosition;
        Vector nextPosition = currPosition;
        double distanceToMove = distance;

        while (distanceToMove > 0) {
            // make one step
            nextPosition = prevPosition.add(velocity);

            // check if the world was left with the last step
            if (!world.isPositionInWorld(nextPosition)) {
                setPosition(world, creature, prevPosition);
                return;
            }

            // check if the last step on the current tile is valid
            final Tile currTile = world.getTile(nextPosition);
            if (!currTile.isWalkable()) {
                setPosition(world, creature, prevPosition);
                return;
            }

            // XXX - do some further collision detection

            // reduce the distance to move by the step-cost for one step on the current tile
            distanceToMove = distanceToMove - currTile.getAttributeValue(TileAttribute.STEP_COST);
            prevPosition = nextPosition;
        }

        setPosition(world, creature, nextPosition);
    }

    private void setPosition(final World world, final Creature creature, final Vector position) {
        // ensure that we don't "round" out of the world at the world edges
        final int x = NumberUtil.getValueInRange(NumberUtil.round(position.getX()), 0, world.getWorldWidthPixel() - 1);
        final int y = NumberUtil.getValueInRange(NumberUtil.round(position.getY()), 0, world.getWorldHeightPixel() - 1);

        creature.setAttributeValue(CreatureAttribute.POSITION_X, x);
        creature.setAttributeValue(CreatureAttribute.POSITION_Y, y);

        world.getEventBus().fireEvent(SimpleEventType.CREATURE_POS_CHANGED, creature);
    }

    /** {@inheritDoc} */
    @Override
    protected void doNotPerform(final World world, final Creature creature) {
        // do nothing
    }

}
