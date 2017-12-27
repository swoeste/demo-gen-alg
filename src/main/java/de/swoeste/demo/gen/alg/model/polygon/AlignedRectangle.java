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
import java.util.List;

/**
 * An (axis) aligned rectangle is a specific quadrangle which is aligned with the coordinate axes.
 *
 * <pre>
 *      +-----------+
 *      |           |
 *      h           |
 *      e           |
 *      i           |
 *      g           |
 *      h           |
 *      t           |
 *      |           |
 *      +---width---+
 *    origin
 * </pre>
 *
 * @author swoeste
 */
public class AlignedRectangle extends Quadrilateral {

    protected AlignedRectangle(final List<Vector> points) {
        super(points);
    }

    public AlignedRectangle(final int x, final int y, final int width, final int height) {
        super(createPoints(x, y, width, height));
    }

    public Vector getOrigin() {
        return getPoints().get(0);
    }

    public double getWidth() {
        final Vector p0 = getPoints().get(0);
        final Vector p1 = getPoints().get(1);
        return (p1.getX() - p0.getX());
    }

    public double getHeight() {
        final Vector p0 = getPoints().get(0);
        final Vector p3 = getPoints().get(3);
        return (p3.getY() - p0.getY());
    }

    public boolean collidesWith(final AlignedRectangle other) {
        return ((this.getOrigin().getX() <= (other.getOrigin().getX() + other.getWidth())) && //
                (other.getOrigin().getX() <= (this.getOrigin().getX() + this.getWidth())) && //
                (this.getOrigin().getY() <= (other.getOrigin().getY() + other.getHeight())) && //
                (other.getOrigin().getY() <= (this.getOrigin().getY() + this.getHeight())));
    }

    private static final List<Vector> createPoints(final double x, final double y, final double width, final double height) {
        final List<Vector> result = new ArrayList<>();

        final Vector origin = new Vector(x, y);
        result.add(origin);
        result.add(new Vector(origin.getX() + width, origin.getY()));
        result.add(new Vector(origin.getX() + width, origin.getY() + height));
        result.add(new Vector(origin.getX(), origin.getY() + height));

        return result;
    }

}
