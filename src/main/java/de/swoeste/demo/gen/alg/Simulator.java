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
package de.swoeste.demo.gen.alg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.swoeste.demo.gen.alg.model.creature.Creature;
import de.swoeste.demo.gen.alg.model.creature.CreatureFactory;

/**
 * @author swoeste
 */
public class Simulator {

    private static final Logger LOG = LoggerFactory.getLogger(Simulator.class);

    public static void main(final String[] args) {

        final List<Creature> creatures = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            Creature creature = CreatureFactory.createCreature();
            creatures.add(creature);
        }

        int step = 0;
        int alive = creatures.size();

        while (creatures.size() > 1) {
            LOG.info("============================{} - {}============================", step, alive); //$NON-NLS-1$
            for (Iterator<Creature> iterator = creatures.iterator(); iterator.hasNext();) {
                Creature creature = iterator.next();
                creature.tick();
                if (!creature.isAlive()) {
                    iterator.remove();
                    LOG.warn("{} died alone after {} steps", creature, step); //$NON-NLS-1$
                    alive--;
                }
            }
            step++;
        }

    }

}
