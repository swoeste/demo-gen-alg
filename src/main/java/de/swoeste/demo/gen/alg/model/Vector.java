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
package de.swoeste.demo.gen.alg.model;

import java.text.MessageFormat;

/**
 * @author swoeste
 */
public class Vector {

    // TODO check if we should move some methods to a util class

    private final double x;
    private final double y;

    public Vector(final Vector vector) {
        this(vector.x, vector.y);
    }

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
        final double addX = this.x - vector.x;
        final double addY = this.y - vector.y;
        return new Vector(addX, addY);
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
        return Math.sqrt(Math.pow(vector.x - this.x, 2) + Math.pow(vector.y - this.y, 2));
    }

    // TODO delete?
    public Vector round() {
        final double roundX = Math.round(this.x);
        final double roundY = Math.round(this.y);
        return new Vector(roundX, roundY);
    }

    // TODO move to extra util?
    public boolean isInTriangle(final Vector p1, final Vector p2, final Vector p3) {
        final boolean sideA = (((p2.y - p1.y) * (this.x - p1.x)) - ((p2.x - p1.x) * (this.y - p1.y))) > 0.0;
        final boolean sideB = (((p3.y - p2.y) * (this.x - p2.x)) - ((p3.x - p2.x) * (this.y - p2.y))) > 0.0;
        if (sideA != sideB) {
            // fast exit, the point is already outside of the triangle
            return false;
        }
        final boolean sideC = (((p1.y - p3.y) * (this.x - p3.x)) - ((p1.x - p3.x) * (this.y - p3.y))) > 0.0;
        return (sideA == sideC);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return MessageFormat.format("Vector [x={0}, y={1}]", this.x, this.y); //$NON-NLS-1$
    }

}
