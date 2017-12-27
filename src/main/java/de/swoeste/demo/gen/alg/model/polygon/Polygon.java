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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.Validate;

/**
 * A polygon is a plane figure that is bounded by a finite chain of straight line segments (edges) closing in a loop to
 * form a closed polygonal chain or circuit.
 *
 * @author swoeste
 */
public class Polygon {

    private final List<Vector> points;

    private List<Edge>         edges;

    public Polygon(final Vector... points) {
        this(Arrays.asList(points));
    }

    public Polygon(final List<Vector> points) {
        Validate.isTrue(points.size() >= 2, "A polygon must consist of at least two points"); //$NON-NLS-1$
        this.points = new ArrayList<>(points);
    }

    public List<Vector> getPoints() {
        return this.points;
    }

    protected List<Edge> getEdges() {
        // TODO sync?
        if (this.edges == null) {
            // lazy initialize the edges, because they wont be used in most cases!
            this.edges = createEdges(this.points);
        }
        return this.edges;
    }

    public boolean collidesWith(final Polygon other) {
        // check edges of polygon A
        if (!collidesWith(this.getEdges(), other)) {
            // fast exit, we found an axis where both polygons do not collide!
            return false;
        }

        // check edges of polygon B
        return collidesWith(other.getEdges(), other);
    }

    private boolean collidesWith(final List<Edge> edges, final Polygon other) {
        for (int i = 0; i < edges.size(); i++) {
            // get the direction vector
            Vector axis = edges.get(i).getDirection();
            // get the normal of the vector (90 degrees)
            axis = axis.perpendicularize();
            // find the projection of a and b onto axis
            final double[] a = project(this, axis);
            final double[] b = project(other, axis);
            if (!overlaps(a, b)) {
                // fast exit, they do not overlap, so no collision
                return false;
            }
        }
        return true;
    }

    private double[] project(final Polygon a, final Vector axis) {
        final Vector normalizedAxis = axis.normalize();

        // min and max are the start and finish points
        double min = a.getPoints().get(0).dotProduct(normalizedAxis);
        double max = min;

        // find the projection of every point on the polygon onto the line
        for (int i = 0; i < a.getPoints().size(); i++) {
            double proj = a.getPoints().get(i).dotProduct(normalizedAxis);
            if (proj < min) {
                min = proj;
            }
            if (proj > max) {
                max = proj;
            }
        }

        return new double[] { min, max };
    }

    private boolean isValueInRange(final double n, final double[] range) {
        double a = range[0];
        double b = range[1];
        if (b < a) {
            // swap
            a = b;
            b = range[0];
        }
        return ((n >= a) && (n <= b));
    }

    private boolean overlaps(final double[] a, final double[] b) {
        return isValueInRange(a[0], b) || isValueInRange(a[1], b) || isValueInRange(b[0], a) || isValueInRange(b[1], a);
    }

    private List<Edge> createEdges(final List<Vector> points) {
        final List<Edge> result = new ArrayList<>(points.size());

        Vector prevPoint = null;
        for (Vector point : points) {
            if (prevPoint != null) {
                result.add(new Edge(prevPoint, point));
            }
            prevPoint = point;
        }

        return result;
    }

}