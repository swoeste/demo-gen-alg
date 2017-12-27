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

import org.testng.Assert;
import org.testng.annotations.Test;

import de.swoeste.demo.gen.alg.model.polygon.Polygon;
import de.swoeste.demo.gen.alg.model.polygon.Vector;

/**
 * @author swoeste
 */
@Test
public class PolygonTest {

    // TODO create separated test cases!

    public void testCollidesWith() {
        // colliding - rectangle - rectangle
        final Polygon a = new Polygon(new Vector(0, 0), new Vector(5, 0), new Vector(5, 5), new Vector(0, 5));
        final Polygon b = new Polygon(new Vector(0, 0), new Vector(5, 0), new Vector(5, 5), new Vector(0, 5));
        assertColliding(a, b);

        // colliding - rectangle - triangle
        final Polygon c = new Polygon(new Vector(0, 0), new Vector(5, 0), new Vector(5, 5), new Vector(0, 5));
        final Polygon d = new Polygon(new Vector(0, 0), new Vector(5, 0), new Vector(0, 5));
        assertColliding(c, d);

        // colliding - rectangle - line
        final Polygon e = new Polygon(new Vector(0, 0), new Vector(5, 0), new Vector(5, 5), new Vector(0, 5));
        final Polygon f = new Polygon(new Vector(5, 0), new Vector(0, 5));
        assertColliding(e, f);
    }

    private void assertColliding(final Polygon a, final Polygon b) {
        Assert.assertTrue(a.collidesWith(b));
        Assert.assertTrue(b.collidesWith(a));
    }

    private void assertNotColliding(final Polygon a, final Polygon b) {
        Assert.assertFalse(a.collidesWith(b));
        Assert.assertFalse(b.collidesWith(a));
    }
}
