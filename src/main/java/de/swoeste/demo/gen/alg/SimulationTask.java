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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.swoeste.demo.gen.alg.model.world.World;

public class SimulationTask implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(SimulationTask.class);

    private final World         world;

    private long                delay;
    private boolean             paused;
    private boolean             canceled;

    public SimulationTask(final World world) {
        this.world = world;
        this.delay = 100L;
        this.paused = false;
        this.canceled = false;
    }

    public void setDelay(final long delay) {
        this.delay = delay;
    }

    public void setPaused(final boolean paused) {
        this.paused = paused;
    }

    public void cancel() {
        this.canceled = true;
    }

    /** {@inheritDoc} */
    @Override
    public void run() {
        while (!Thread.interrupted() && !this.canceled) {
            if (!this.paused) {
                this.world.update();
                sleep(this.delay);
            } else {
                sleep(500L);
            }
        }
        LOG.info("The SimulationTask on thread {} has been completed!", Thread.currentThread()); //$NON-NLS-1$
    }

    private void sleep(final long wait) {
        try {
            if (wait >= 0L) {
                Thread.sleep(wait);
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            LOG.error("Thread {} was interupted while sleeping!", Thread.currentThread(), ex); //$NON-NLS-1$
        }
    }

}