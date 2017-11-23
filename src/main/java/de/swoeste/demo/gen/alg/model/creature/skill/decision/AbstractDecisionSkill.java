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
package de.swoeste.demo.gen.alg.model.creature.skill.decision;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.swoeste.demo.gen.alg.model.creature.Creature;
import de.swoeste.demo.gen.alg.model.creature.CreatureAttribute;
import de.swoeste.demo.gen.alg.model.creature.skill.Skill;

/**
 * @author swoeste
 */
public class AbstractDecisionSkill implements Skill {

    private static final Logger     LOG = LoggerFactory.getLogger(AbstractDecisionSkill.class);

    private final Creature          creature;
    private final CreatureAttribute activationAttribute;
    private final CreatureAttribute targetAttribute;
    private final CreatureAttribute consideredAttribute;

    public AbstractDecisionSkill(final Creature creature, final CreatureAttribute activationAttribute, final CreatureAttribute targetAttribute,
            final CreatureAttribute consideredAttribute) {
        this.creature = creature;
        this.activationAttribute = activationAttribute;
        this.targetAttribute = targetAttribute;
        this.consideredAttribute = consideredAttribute;
    }

    /** {@inheritDoc} */
    @Override
    public CreatureAttribute getActivationAttribute() {
        return this.activationAttribute;
    }

    /** {@inheritDoc} */
    @Override
    public void doPerform() {
        final int consideredValue = this.creature.getAttributeValue(this.consideredAttribute);
        final int previousValue = this.creature.setAttributeValue(this.targetAttribute, consideredValue);
        LOG.debug("{} decides to change its {} from {} to {}", this.creature, this.targetAttribute, previousValue, consideredValue); //$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public void doNotPerform() {
        // do nothing
    }

}
