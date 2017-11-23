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
package de.swoeste.demo.gen.alg.position;

import java.text.MessageFormat;

import de.swoeste.demo.gen.alg.model.Rectangle;

/**
 * @author swoeste
 */
public final class Partitionable<T> {

    private final T         element;
    private final Rectangle elementShape;

    private Partition<T>    currentPartition;

    public Partitionable(final T element, final Rectangle shape) {
        this.element = element;
        this.elementShape = new Rectangle(shape);
    }

    public T getElement() {
        return this.element;
    }

    public Rectangle getElementShape() {
        return this.elementShape;
    }

    protected Partition<T> getCurrentPartition() {
        return this.currentPartition;
    }

    protected void setCurrentPartition(final Partition<T> currentPartition) {
        this.currentPartition = currentPartition;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Partitionable [element={0}, elementShape={1}, currentPartition={2}]",  //$NON-NLS-1$
                this.element, this.elementShape, this.currentPartition);
    }

}