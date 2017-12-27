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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.swoeste.demo.gen.alg.model.polygon.AlignedRectangle;

/**
 * @author swoeste
 */
public class PositionTracker<T extends Identifiable & Shapeaware> {

    private final Partition<T>                       root;
    private final HashMap<Integer, Partitionable<T>> elements;

    public PositionTracker(final AlignedRectangle rootShape) {
        this.root = new Partition<>(rootShape);
        this.elements = new HashMap<>();
    }

    public void addElement(final T element) {
        final Partitionable<T> partitionable = new Partitionable<>(element, element.getShape());

        final Partitionable<T> prevElement = this.elements.put(element.getId(), partitionable);
        if (prevElement != null) {
            // TODO maybe we can remove this check in the future
            throw new IllegalArgumentException("The element has been added already!"); //$NON-NLS-1$
        }

        this.root.insert(partitionable);
    }

    public void removeElement(final T element) {
        final Partitionable<T> removedElement = this.elements.remove(element.getId());
        if (removedElement != null) {
            final boolean removed = removedElement.getCurrentPartition().removeElement(removedElement);
            if (!removed) {
                // TODO maybe we can remove this check in the future
                throw new IllegalArgumentException("An element was removed which had no representation in its partition!"); //$NON-NLS-1$
            }
        }
    }

    public List<T> getElementsInArea(final AlignedRectangle area) {
        final List<T> result = new ArrayList<>();
        final List<Partitionable<T>> retrievedPartitionables = this.root.retrieve(area);

        retrievedPartitionables.stream() //
                .filter(entry -> entry.getElementShape().collidesWith(area)) //
                .forEach(filteredEntry -> result.add(filteredEntry.getElement()));

        return result;
    }

    public List<T> getAllElements() {
        final List<T> result = new ArrayList<>();
        for (final Partitionable<T> partitionable : this.elements.values()) {
            result.add(partitionable.getElement());
        }
        return result;
    }

    public int countElements() {
        return this.root.countElements();
    }

    public AlignedRectangle getRootPartitionShape() {
        return this.root.getPartitionShape();
    }

    public boolean isValid() {
        return this.root.countElements() == this.elements.size();
    }

}
