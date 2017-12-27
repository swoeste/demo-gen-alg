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
package de.swoeste.demo.gen.alg.model.polygon;

import java.text.MessageFormat;

/**
 * @author swoeste
 */
public class Vector {

    private final double x;
    private final double y;

    public Vector(final int x, final int y) {
        this((double) x, (double) y);
    }

    public Vector(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public Vector add(final Vector vector) {
        final double addX = this.x + vector.x;
        final double addY = this.y + vector.y;
        return new Vector(addX, addY);
    }

    public Vector substract(final Vector vector) {
        final double subX = this.x - vector.x;
        final double subY = this.y - vector.y;
        return new Vector(subX, subY);
    }

    public Vector multiply(final double factor) {
        final double multiplyX = this.x * factor;
        final double multiplyY = this.y * factor;
        return new Vector(multiplyX, multiplyY);
    }

    public Vector divide(final double divisor) {
        final double divideX = this.x / divisor;
        final double divideY = this.y / divisor;
        return new Vector(divideX, divideY);
    }

    public Vector project(final double orientationRadians, final double distance) {
        final double addX = this.x + (Math.cos(orientationRadians) * distance);
        final double addY = this.y + (Math.sin(orientationRadians) * distance);
        return new Vector(addX, addY);
    }

    public Vector mod(final Vector vector) {
        final double modX = this.x % vector.x;
        final double modY = this.y % vector.y;
        return new Vector(modX, modY);
    }

    public double distance(final Vector vector) {
        // http://mathworld.wolfram.com/Distance.html
        return Math.sqrt(Math.pow(vector.x - this.x, 2) + Math.pow(vector.y - this.y, 2));
    }

    public double dotProduct(final Vector b) {
        // http://mathworld.wolfram.com/DotProduct.html
        return (this.x * b.getX()) + (this.y * b.getY());
    }

    public Vector normalize() {
        // http://mathworld.wolfram.com/NormalizedVector.html
        return divide(Math.sqrt((this.x * this.x) + (this.y * this.y)));
    }

    public Vector perpendicularize() {
        // http://mathworld.wolfram.com/PerpendicularVector.html
        return new Vector(this.y, this.x * -1);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return MessageFormat.format("Vector [x={0}, y={1}]", this.x, this.y); //$NON-NLS-1$
    }

}
