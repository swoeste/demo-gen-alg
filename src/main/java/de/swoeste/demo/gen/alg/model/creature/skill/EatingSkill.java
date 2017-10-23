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

import de.swoeste.demo.gen.alg.model.creature.Creature;

/**
 * @author swoeste
 */
public class EatingSkill implements Skill {

    private static final Logger LOG = LoggerFactory.getLogger(EatingSkill.class);

    // TODO we need something to eat ...

    private final Creature      creature;

    public EatingSkill(final Creature creature) {
        this.creature = creature;
    }

    /** {@inheritDoc} */
    @Override
    public void doPerform() {
        LOG.info("{} is eating", this.creature);
        this.creature.gainStarvation(-0.02);

        if (this.creature.getStarvation() <= 0.0) {
            this.creature.gainHealth(-0.025);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void doNotPerform() {
        LOG.info("{} is not eating", this.creature);
        this.creature.gainStarvation(0.02);

        if (this.creature.getStarvation() >= 1.0) {
            this.creature.gainHealth(-0.05);
        }
    }

}
