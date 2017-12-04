/*
 * Copyright (C) 2017 Sebastian Woeste
 *
 * Licensed to Sebastian Woeste under one or more contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership. I license this file to You under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package de.swoeste.demo.gen.alg.model.world.tile;

import java.text.MessageFormat;
import java.util.Map;

import de.swoeste.demo.gen.alg.model.Vector;

/**
 * @author swoeste
 */
public abstract class AbstractTile implements Tile {

    private final Vector                position;
    private final int                   size;
    private final double                heigth;

    private final boolean               placeable;
    private final boolean               walkable;

    private Map<TileAttribute, Integer> attributes;

    public AbstractTile(final Vector position, final int size, final double height, final boolean placeable, final boolean walkable) {
        this.position = position;
        this.size = size;
        this.heigth = height;
        this.placeable = placeable;
        this.walkable = walkable;
    }

    void init() {
        // default implementation, do nothing
    }

    void setAttributes(final Map<TileAttribute, Integer> attributes) {
        this.attributes = attributes;
    }

    /** {@inheritDoc} */
    @Override
    public Vector getPosition() {
        return this.position;
    }

    /** {@inheritDoc} */
    @Override
    public int getSize() {
        return this.size;
    }

    /** {@inheritDoc} */
    @Override
    public double getHeigth() {
        return this.heigth;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPlaceable() {
        return this.placeable;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isWalkable() {
        return this.walkable;
    }

    /** {@inheritDoc} */
    @Override
    public void update() {
        // default implementation, do nothing
    }

    /** {@inheritDoc} */
    @Override
    public int getAttributeValue(final TileAttribute attribute) {
        return this.attributes.get(attribute);
    }

    /** {@inheritDoc} */
    @Override
    public int setAttributeValue(final TileAttribute attribute, final int value) {
        return this.attributes.put(attribute, value);
    }

    /** {@inheritDoc} */
    @Override
    public int increaseAttributeByValue(final TileAttribute attribute, final int value) {
        final Integer currentValue = this.attributes.get(attribute);
        final Integer newValue = currentValue + value;
        this.attributes.put(attribute, newValue);
        return newValue;  // this is the new value!
    }

    /** {@inheritDoc} */
    @Override
    public int decreaseAttributeByValue(final TileAttribute attribute, final int value) {
        final Integer currentValue = this.attributes.get(attribute);
        final Integer newValue = currentValue - value;
        this.attributes.put(attribute, newValue);
        return newValue; // this is the new value!
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return MessageFormat.format("Tile [position={0}, size={1}, heigth={2}, placeable={3}, walkable={4}]", //$NON-NLS-1$
                this.position, this.size, this.heigth, this.placeable, this.walkable);
    }

}
