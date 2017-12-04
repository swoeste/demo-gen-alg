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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.swoeste.demo.gen.alg.model.Vector;
import de.swoeste.demo.gen.alg.model.creature.Creature;
import de.swoeste.demo.gen.alg.model.creature.CreatureAttribute;
import de.swoeste.demo.gen.alg.model.world.World;
import de.swoeste.demo.gen.alg.model.world.tile.Tile;

/**
 * @author swoeste
 */
public class EatingSkill extends AbstractSkill {

    private static final Logger LOG = LoggerFactory.getLogger(EatingSkill.class);

    // TODO we need something to eat ...

    // TODO we need to ensure, that hunger (and other attributes) stays in border 0 ... MAX !

    public EatingSkill(final World world, final Creature creature) {
        super(world, creature, CreatureAttribute.EAT);
    }

    /** {@inheritDoc} */
    @Override
    protected void doPerform(final World world, final Creature creature) {
        if (canEat(world, creature)) {
            final int hunger = creature.decreaseAttributeByValue(CreatureAttribute.HUNGER, 1);

            // the creature has eaten too much foot, so it will lose some health
            if (hunger < 0) {
                creature.decreaseAttributeByValue(CreatureAttribute.HEALTH, 1);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void doNotPerform(final World world, final Creature creature) {
        final int hunger = creature.increaseAttributeByValue(CreatureAttribute.HUNGER, 1);

        // the creature is very hungry, so it will lose some health
        if (hunger > creature.getAttributeValue(CreatureAttribute.MAX_HUNGER)) {
            creature.decreaseAttributeByValue(CreatureAttribute.HEALTH, 1);
        }
    }

    private boolean canEat(final World world, final Creature creature) {
        final Vector position = getPosition(creature);
        final Tile tile = world.getTile(position);

        // TODO implement

        return true;
    }

}
