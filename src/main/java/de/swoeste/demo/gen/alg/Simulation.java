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

import java.text.MessageFormat;

import de.swoeste.demo.gen.alg.model.world.World;
import de.swoeste.demo.gen.alg.util.SimpleExecutor;

/**
 * @author swoeste
 */
public class Simulation {

    private static final long    MAX_DELAY = 1000;

    private final World          world;
    private final SimulationTask task;

    public Simulation(final Configuration config) {
        this.world = new World(config);

        this.task = new SimulationTask(this.world);
        SimpleExecutor.getInstance().execute(this.task);
    }

    public World getWorld() {
        return this.world;
    }

    public void start() {
        this.task.setPaused(false);
    }

    public void pause() {
        this.task.setPaused(true);
    }

    public void shutdown() {
        this.task.cancel();
    }

    public void setSpeed(final double value) {
        final double percentage = value / 100;
        if (((percentage) <= 1.0) && ((percentage) >= 0.0)) {
            final long delay = (long) (MAX_DELAY - (MAX_DELAY * percentage));
            this.task.setDelay(delay);
        } else {
            final String msg = MessageFormat.format("''{0}'' is not a valid speed value!", percentage); //$NON-NLS-1$
            throw new IllegalArgumentException(msg);
        }
    }

}
