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

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import de.swoeste.demo.gen.alg.model.polygon.Triangle;
import de.swoeste.demo.gen.alg.model.polygon.Vector;

/**
 * @author swoeste
 */
@Test
public class TriangleTest {

    // TODO create separated test cases!

    public void testCollidesWithPoint() {
        final Vector p0 = new Vector(-1, -1);
        final Vector p2 = new Vector(-1, 5);
        final Vector p1 = new Vector(5, -1);

        final Triangle triangle = new Triangle(p0, p1, p2);

        // collide with own points
        assertTrue(triangle.collidesWith(p0));
        assertTrue(triangle.collidesWith(p1));
        assertTrue(triangle.collidesWith(p2));

        // collide with point within triangle
        assertTrue(triangle.collidesWith(new Vector(0, 0)));
        assertTrue(triangle.collidesWith(new Vector(0, 1)));
        assertTrue(triangle.collidesWith(new Vector(1, 0)));
        assertTrue(triangle.collidesWith(new Vector(1, 1)));
        assertTrue(triangle.collidesWith(new Vector(0.5, 0.5)));

        // do not collide with point outside the triangle
        assertFalse(triangle.collidesWith(new Vector(6, 6)));
        assertFalse(triangle.collidesWith(new Vector(-2, -2)));
    }

}
