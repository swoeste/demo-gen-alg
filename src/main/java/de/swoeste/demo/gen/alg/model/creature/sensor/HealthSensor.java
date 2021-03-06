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
package de.swoeste.demo.gen.alg.model.creature.sensor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.swoeste.demo.gen.alg.model.creature.Creature;
import de.swoeste.demo.gen.alg.model.creature.CreatureAttribute;

/**
 * @author swoeste
 */
public class HealthSensor extends AbstractSensor {

    private static final Logger LOG = LoggerFactory.getLogger(HealthSensor.class);

    private final Creature      creature;

    public HealthSensor(final Creature creature) {
        this.creature = creature;
    }

    /** {@inheritDoc} */
    @Override
    public double getSensorValue() {
        final int health = this.creature.getAttributeValue(CreatureAttribute.HEALTH);
        final int maxHealth = this.creature.getAttributeValue(CreatureAttribute.MAX_HEALTH);
        final double value = normalizeInputValue(health, maxHealth);

        LOG.debug("{} has {}({}) health", this.creature, health, value); //$NON-NLS-1$
        return value;
    }

}
