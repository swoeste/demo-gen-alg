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
package de.swoeste.demo.gen.alg.model.creature.receptor.attribute;

import de.swoeste.demo.gen.alg.model.creature.Creature;
import de.swoeste.demo.gen.alg.model.creature.receptor.AttributeReceptor;

/**
 * @author swoeste
 */
public abstract class AbstractAttributeReceptor implements AttributeReceptor {

    private final Creature creature;

    private double         receivedValue;

    public AbstractAttributeReceptor(final Creature creature) {
        this.creature = creature;
    }

    /** {@inheritDoc} */
    @Override
    public final void receive(final double value) {
        this.receivedValue = value;
        storeValue(this.creature, value);
    }

    /** {@inheritDoc} */
    @Override
    public final double getReceivedValue() {
        return this.receivedValue;
    }

    protected abstract void storeValue(final Creature creature, final double value);
}
