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
package de.swoeste.demo.gen.alg.event;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author swoeste
 */
@Test(singleThreaded = true)
public class SimpleEventBusTest {

    private final SimpleEventBus eventBus = new SimpleEventBus();

    @Test
    public void testSimpleEventBus() {
        final SimpleListener listener = new SimpleListener();

        // register listeners
        for (SimpleEventType simpleEventType : SimpleEventType.values()) {
            this.eventBus.registerListener(simpleEventType, listener);
        }

        // fire events
        for (SimpleEventType simpleEventType : SimpleEventType.values()) {
            this.eventBus.fireEvent(simpleEventType, null);
        }

        // check invocations
        Assert.assertEquals(listener.getInvocations(), SimpleEventType.values().length);

        // unregister listeners
        for (SimpleEventType simpleEventType : SimpleEventType.values()) {
            this.eventBus.unregisterListener(simpleEventType, listener);
        }

        // fire events again
        for (SimpleEventType simpleEventType : SimpleEventType.values()) {
            this.eventBus.fireEvent(simpleEventType, null);
        }

        // check invocations again
        Assert.assertEquals(listener.getInvocations(), SimpleEventType.values().length);
    }

    private final class SimpleListener implements SimpleEventListener {

        private int invocations;

        /** {@inheritDoc} */
        @Override
        public void handle(final SimpleEvent event) {
            this.invocations++;
        }

        public int getInvocations() {
            return this.invocations;
        }

    }

}
