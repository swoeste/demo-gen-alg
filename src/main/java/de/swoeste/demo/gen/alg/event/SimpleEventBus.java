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

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 * @author swoeste
 */
public class SimpleEventBus {

    // TODO write a small test for this

    private final EnumMap<SimpleEventType, List<SimpleEventListener>> listeners;

    public SimpleEventBus() {
        this.listeners = new EnumMap<>(SimpleEventType.class);
        initialize();
    }

    private void initialize() {
        for (SimpleEventType eventType : SimpleEventType.values()) {
            this.listeners.put(eventType, new ArrayList<SimpleEventListener>(2));
        }
    }

    public void registerListener(final SimpleEventType eventType, final SimpleEventListener listener) {
        this.listeners.get(eventType).add(listener);
    }

    public void unregisterListener(final SimpleEventType eventType, final SimpleEventListener listener) {
        this.listeners.get(eventType).remove(listener);
    }

    public void fireEvent(final SimpleEventType eventType, final Object eventData) {
        fireEvent(new SimpleEvent(eventType, eventData));
    }

    public void fireEvent(final SimpleEvent event) {
        final List<SimpleEventListener> eventListener = this.listeners.get(event.getType());
        eventListener.stream().forEach(entry -> entry.handle(event));
    }

}
