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
import java.util.Iterator;
import java.util.List;

import de.swoeste.demo.gen.alg.model.Rectangle;

/**
 * A partition can store MAX_ELEMENTS before it splits itself into 4 sub-partitions. Each sub-partition is a partition
 * in its self and can also split itself recursively. The partitions are index from 0 to 3 and range from minX to maxX
 * and minY to maxY.
 *
 * <pre>
 * maxY *-----*-----*
 *      *     *     *
 *      *  2  *  3  *
 *      *     *     *
 * midY *-----*-----*
 *      *     *     *
 *      *  0  *  1  *
 *      *     *     *
 * minX *-----*-----*
 *    minX  midX  maxX
 * </pre>
 *
 * @author swoeste
 */
public final class Partition<T> {

    private static final int             MAX_LEVELS   = 4;
    private static final int             MAX_ELEMENTS = 4;

    private final List<Partitionable<T>> elements;
    private final Partition<T>[]         partitions;

    private final int                    level;
    private final int                    minX;
    private final int                    midX;
    private final int                    maxX;
    private final int                    sizeX;
    private final int                    midY;
    private final int                    sizeY;
    private final int                    minY;
    private final int                    maxY;

    private boolean                      splitted;

    public Partition(final Rectangle partitionShape) {
        this(0, partitionShape.getPosX(), partitionShape.getPosY(), partitionShape.getSizeX(), partitionShape.getSizeY());
    }

    private Partition(final int level, final int minX, final int minY, final int sizeX, final int sizeY) {
        this.level = level;
        this.minX = minX;
        this.minY = minY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.maxX = minX + sizeX;
        this.maxY = minY + sizeY;
        this.midX = minX + (sizeX / 2);
        this.midY = minY + (sizeY / 2);
        this.partitions = new Partition[4];
        this.elements = new ArrayList<>();
        this.splitted = false;
    }

    public Rectangle getPartitionShape() {
        return new Rectangle(this.minX, this.minY, this.sizeX, this.sizeY);
    }

    public boolean removeElement(final Partitionable<T> partitionable) {
        partitionable.setCurrentPartition(null);
        return this.elements.remove(partitionable);
    }

    public int countElements() {
        int size = this.elements.size();
        for (Partition<T> partition : this.partitions) {
            if (partition != null) {
                size = size + partition.countElements();
            }
        }
        return size;
    }

    public void insert(final Partitionable<T> partitionable) {
        if (this.splitted) {
            final Rectangle shape = partitionable.getElementShape();
            final int index = getIndex(shape.getPosX(), shape.getPosY(), shape.getSizeX(), shape.getSizeY());
            if (index != -1) {
                this.partitions[index].insert(partitionable);
                return;
            }
        }

        partitionable.setCurrentPartition(this);
        this.elements.add(partitionable);

        if ((this.elements.size() > Partition.MAX_ELEMENTS) && (this.level < Partition.MAX_LEVELS)) {
            if (!this.splitted) {
                split();
            }
            partitionElements();
        }
    }

    public List<Partitionable<T>> retrieve(final Rectangle shape) {
        return retrieve(shape.getPosX(), shape.getPosY(), shape.getSizeX(), shape.getSizeY());
    }

    public List<Partitionable<T>> retrieve(final int x, final int y, final int sizeX, final int sizeY) {
        final List<Partitionable<T>> result = new ArrayList<>();
        retrieve(result, x, y, sizeX, sizeY);
        return result;
    }

    private void retrieve(final List<Partitionable<T>> returnObjects, final int x, final int y, final int sizeX, final int sizeY) {
        final int index = getIndex(x, y, sizeX, sizeY);
        if (this.splitted && (index != -1)) {
            this.partitions[index].retrieve(returnObjects, x, y, sizeX, sizeY);
        }
        returnObjects.addAll(this.elements);
    }

    private int getIndex(final int x, final int y, final int sizeX, final int sizeY) {
        final boolean bottomPartition = ((y < this.midY) && ((y + sizeY) < this.midY));
        final boolean topPartition = ((y >= this.midY) && ((y + sizeY) < this.maxY));

        final boolean leftPartition = ((x < this.midX) && ((x + sizeX) < this.midX));
        final boolean rightPartition = (x >= this.midX) && ((x + sizeX) < this.maxX);

        if (leftPartition) {
            if (bottomPartition) {
                return 0;
            }
            if (topPartition) {
                return 2;
            }
        }
        if (rightPartition) {
            if (bottomPartition) {
                return 1;
            } else if (topPartition) {
                return 3;
            }
        }

        // element does not fit into any partition
        return -1;
    }

    private void partitionElements() {
        final Iterator<Partitionable<T>> iterator = this.elements.iterator();
        while (iterator.hasNext()) {
            final Partitionable<T> current = iterator.next();
            final Rectangle shape = current.getElementShape();
            final int index = getIndex(shape.getPosX(), shape.getPosY(), shape.getSizeX(), shape.getSizeY());
            if (index != -1) {
                iterator.remove();
                this.partitions[index].insert(current);
            }
        }
    }

    private void split() {
        this.partitions[0] = new Partition<>(this.level + 1, this.minX, this.midX, this.minY, this.midY);
        this.partitions[1] = new Partition<>(this.level + 1, this.midX, this.maxX, this.minY, this.midY);
        this.partitions[2] = new Partition<>(this.level + 1, this.minX, this.midX, this.midY, this.maxY);
        this.partitions[3] = new Partition<>(this.level + 1, this.midX, this.maxX, this.midY, this.maxY);
        this.splitted = true;
    }

}