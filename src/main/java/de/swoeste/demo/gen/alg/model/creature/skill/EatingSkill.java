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

import de.swoeste.demo.gen.alg.model.creature.Creature;
import de.swoeste.demo.gen.alg.model.creature.CreatureAttribute;
import de.swoeste.demo.gen.alg.model.polygon.Vector;
import de.swoeste.demo.gen.alg.model.world.World;
import de.swoeste.demo.gen.alg.model.world.tile.Tile;
import de.swoeste.demo.gen.alg.model.world.tile.TileAttribute;

/**
 * @author swoeste
 */
public class EatingSkill extends AbstractSkill {

    public EatingSkill(final World world, final Creature creature) {
        super(world, creature, CreatureAttribute.EAT);
    }

    /** {@inheritDoc} */
    @Override
    protected void doPerform(final World world, final Creature creature) {
        final Vector position = getPosition(creature);
        final Tile tile = world.getTile(position);

        if (tile.getAttributeValue(TileAttribute.FOOD) > 0) {
            eat(creature);

            // remove 1 food unit from tile
            tile.decreaseAttributeByValue(TileAttribute.FOOD, 1, 0);
        } else {
            starve(creature);
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void doNotPerform(final World world, final Creature creature) {
        starve(creature);
    }

    private void eat(final Creature creature) {
        final int hunger = creature.decreaseAttributeByValue(CreatureAttribute.HUNGER, 1, 0);

        // the creature has eaten too much foot, so it will lose some health
        if (hunger <= 0) {
            creature.decreaseAttributeByValue(CreatureAttribute.HEALTH, 1, 0);
        }
    }

    private void starve(final Creature creature) {
        final int maxHunger = creature.getAttributeValue(CreatureAttribute.MAX_HUNGER);
        final int hunger = creature.increaseAttributeByValue(CreatureAttribute.HUNGER, 1, maxHunger);

        // the creature is very hungry, so it will lose some health
        if (hunger >= creature.getAttributeValue(CreatureAttribute.MAX_HUNGER)) {
            creature.decreaseAttributeByValue(CreatureAttribute.HEALTH, 1, 0);
        }
    }

}
