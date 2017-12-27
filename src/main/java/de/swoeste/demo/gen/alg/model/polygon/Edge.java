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
 * An edge is a particular type of line segment joining two vertices in a polygon.
 *
 * <pre>
 *            P1
 *            *
 *           /
 *          /
 *         /
 *        /
 *       /
 *      *
 *     P0
 * </pre>
 *
 * @author swoeste
 */
public class Edge extends Polygon {

    protected Edge(final List<Vector> points) {
        super(points);
    }

    public Edge(final Vector p0, final Vector p1) {
        super(p0, p1);
    }

    public Vector getDirection() {
        final Vector p0 = getPoints().get(0);
        final Vector p1 = getPoints().get(1);
        return p1.substract(p0);
    }

}
