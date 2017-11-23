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
package de.swoeste.demo.gen.alg.util;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author swoeste
 */
public class SimpleExecutor {

    private static final Logger         LOG      = LoggerFactory.getLogger(SimpleExecutor.class);

    private static final SimpleExecutor INSTANCE = new SimpleExecutor();

    private final ExecutorService       executorService;

    /**
     * Constructor for a new RunnableExecutor.
     */
    private SimpleExecutor() {
        this.executorService = Executors.newCachedThreadPool();
    }

    public void execute(final Runnable runnable) {
        this.executorService.execute(runnable);
    }

    public void shutdown() throws InterruptedException {
        this.executorService.shutdown();
        if (!this.executorService.awaitTermination(10, TimeUnit.SECONDS)) {
            LOG.warn("Executor did not terminate in the specified time."); //$NON-NLS-1$
            final List<Runnable> droppedTasks = this.executorService.shutdownNow();
            LOG.warn("Executor was abruptly shut down. {} tasks will not be executed.", droppedTasks.size()); //$NON-NLS-1$
        }
    }

    public static SimpleExecutor getInstance() {
        return INSTANCE;
    }

}
