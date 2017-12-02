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
package de.swoeste.demo.gen.alg.util.comparator;

import java.util.Comparator;

import de.swoeste.demo.gen.alg.model.creature.Creature;
import de.swoeste.demo.gen.alg.model.creature.CreatureAttribute;

/**
 * @author swoeste
 */
public class CreatureAttributeComparator implements Comparator<Creature> {

    private final CreatureAttribute attribute;

    public CreatureAttributeComparator(final CreatureAttribute attribute) {
        this.attribute = attribute;
    }

    /** {@inheritDoc} */
    @Override
    public int compare(final Creature o1, final Creature o2) {
        return Integer.compare(o1.getAttributeValue(this.attribute), o2.getAttributeValue(this.attribute)) * -1;
    }
}