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

/**
 * @author swoeste
 */
public abstract class AbstractSkill implements Skill {

    private static final Logger     LOG = LoggerFactory.getLogger(AbstractSkill.class);

    private final World             world;
    private final Creature          creature;
    private final CreatureAttribute activator;

    private int                     countPerform;
    private int                     countNotPerform;

    public AbstractSkill(final World world, final Creature creature, final CreatureAttribute activator) {
        this.world = world;
        this.creature = creature;
        this.activator = activator;
    }

    /** {@inheritDoc} */
    @Override
    public final CreatureAttribute getActivationAttribute() {
        return this.activator;
    }

    /** {@inheritDoc} */
    @Override
    public final void doPerform() {
        this.countPerform++;
        LOG.debug("{} will perform {} it used this skill {} times", this.creature, this, this.countPerform); //$NON-NLS-1$
        doPerform(this.world, this.creature);
    }

    protected abstract void doPerform(World world, Creature creature);

    /** {@inheritDoc} */
    @Override
    public final void doNotPerform() {
        this.countNotPerform++;
        LOG.debug("{} will not perform {} it ignored this skill {} times", this.creature, this, this.countNotPerform); //$NON-NLS-1$
        doNotPerform(this.world, this.creature);
    }

    protected abstract void doNotPerform(World world, Creature creature);

    protected Vector getPosition(final Creature creature) {
        final int x = creature.getAttributeValue(CreatureAttribute.POSITION_X);
        final int y = creature.getAttributeValue(CreatureAttribute.POSITION_Y);
        return new Vector(x, y);
    }

}
