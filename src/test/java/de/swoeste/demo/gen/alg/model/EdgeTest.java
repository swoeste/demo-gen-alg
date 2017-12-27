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

import de.swoeste.demo.gen.alg.model.polygon.Edge;
import de.swoeste.demo.gen.alg.model.polygon.Vector;

/**
 * @author swoeste
 */
@Test
public class EdgeTest {

    public void testcollidesWith() {
        final Edge a = new Edge(new Vector(0, 0), new Vector(5, 5));
        final Edge b = new Edge(new Vector(5, 0), new Vector(0, 5));
        Assert.assertTrue(a.collidesWith(b));
        Assert.assertTrue(b.collidesWith(a));
    }

    public void testcollidesWithEqualEdge() {
        final Edge a = new Edge(new Vector(0, 0), new Vector(5, 5));
        final Edge b = new Edge(new Vector(0, 0), new Vector(5, 5));
        Assert.assertTrue(a.collidesWith(b));
        Assert.assertTrue(b.collidesWith(a));
    }

    public void testcollidesWithOverlappingEdge() {
        final Edge a = new Edge(new Vector(0, 0), new Vector(2, 2));
        final Edge b = new Edge(new Vector(2, 2), new Vector(4, 4));
        Assert.assertTrue(a.collidesWith(b));
        Assert.assertTrue(b.collidesWith(a));
    }

    public void testcollidesWithNonCollidingEdge() {
        final Edge a = new Edge(new Vector(0, 0), new Vector(1, 3));
        final Edge b = new Edge(new Vector(3, 2), new Vector(5, 6));
        Assert.assertFalse(a.collidesWith(b));
        Assert.assertFalse(b.collidesWith(a));
    }

    public void testcollidesWithParallelEdge() {
        final Edge a = new Edge(new Vector(0, 0), new Vector(0, 4));
        final Edge b = new Edge(new Vector(4, 0), new Vector(4, 4));
        Assert.assertFalse(a.collidesWith(b));
        Assert.assertFalse(b.collidesWith(a));
    }

    public void testGetDirection() {
        final Edge edge = new Edge(new Vector(4, 4), new Vector(5, 5));
        final Vector direction = edge.getDirection();
        Assert.assertEquals(direction.getX(), 1.0);
        Assert.assertEquals(direction.getY(), 1.0);
    }

}
