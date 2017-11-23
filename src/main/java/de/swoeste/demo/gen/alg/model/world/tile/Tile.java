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

import de.swoeste.demo.gen.alg.model.RGBColor;
import de.swoeste.demo.gen.alg.model.Vector;

/**
 * @author swoeste
 */
public class Tile {

    // TODO attribute, wie bei den creaturen?

    private final Vector   position;
    private final int      size;
    private final double   heigth;

    private final boolean  placeable;
    private final boolean  walkable;
    private final double   stepCost;

    private final RGBColor color;

    public Tile(final Vector position, final int size, final double height, final boolean placeable, final double stepCost, final RGBColor color) {
        this(position, size, height, placeable, true, stepCost, color);
    }

    public Tile(final Vector position, final int size, final double height, final boolean placeable, final boolean walkable, final RGBColor color) {
        this(position, size, height, placeable, walkable, 1.0, color);
    }

    public Tile(final Vector position, final int size, final double height, final boolean placeable, final boolean walkable, final double stepCost, final RGBColor color) {
        this.position = position;
        this.size = size;
        this.heigth = height;
        this.placeable = placeable;
        this.walkable = walkable;
        this.stepCost = stepCost;
        this.color = color;
    }

    public RGBColor getColor() {
        return this.color;
    }

    public void tick() {
        // default implementation, do nothing
    }

    public Vector getPosition() {
        return this.position;
    }

    public int getSize() {
        return this.size;
    }

    public double getStepCost() {
        return this.stepCost;
    }

    public boolean isWalkable() {
        return this.walkable;
    }

    public boolean isPlaceable() {
        return this.placeable;
    }

    public double getHeigth() {
        return this.heigth;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return MessageFormat.format("Tile [position={0}, size={1}, heigth={2}, placeable={3}, walkable={4}, stepCost={5}, color={6}]", //$NON-NLS-1$
                this.position, this.size, this.heigth, this.placeable, this.walkable, this.stepCost, this.color);
    }

}
