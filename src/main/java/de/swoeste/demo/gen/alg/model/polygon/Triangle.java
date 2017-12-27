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

import java.util.List;

/**
 * A triangle is a polygon with three edges and three vertices.
 *
 * <pre>
 *            P1
 *            *
 *           / \
 *          /   \
 *         /     \
 *        /       \
 *       /         \
 *      *-----------*
 *     P0           P2
 * </pre>
 *
 * @author swoeste
 */
public class Triangle extends Polygon {

    protected Triangle(final List<Vector> points) {
        super(points);
    }

    public Triangle(final Vector p0, final Vector p1, final Vector p2) {
        super(p0, p1, p2);
    }

    // TODO maybe we could also implement this in the polygon class for a more general usage!
    public boolean collidesWith(final Vector point) {
        final Vector p0 = getPoints().get(0);
        final Vector p1 = getPoints().get(1);
        final Vector p2 = getPoints().get(2);

        final boolean sideA = (((p1.getY() - p0.getY()) * (point.getX() - p0.getX())) - ((p1.getX() - p0.getX()) * (point.getY() - p0.getY()))) > 0.0;
        final boolean sideB = (((p2.getY() - p1.getY()) * (point.getX() - p1.getX())) - ((p2.getX() - p1.getX()) * (point.getY() - p1.getY()))) > 0.0;
        if (sideA != sideB) {
            // fast exit, the point is already outside of the triangle
            return false;
        }

        final boolean sideC = (((p0.getY() - p2.getY()) * (point.getX() - p2.getX())) - ((p0.getX() - p2.getX()) * (point.getY() - p2.getY()))) > 0.0;
        return (sideA == sideC);
    }

}
