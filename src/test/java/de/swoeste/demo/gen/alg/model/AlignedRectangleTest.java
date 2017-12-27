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

import de.swoeste.demo.gen.alg.model.polygon.AlignedRectangle;
import de.swoeste.demo.gen.alg.model.polygon.Vector;

/**
 * @author swoeste
 */
@Test
public class AlignedRectangleTest {

    // TODO create separated test cases!

    public void collidesWith() {
        final AlignedRectangle base = new AlignedRectangle(0, 0, 4, 6);

        // normal intersection
        Assert.assertTrue(base.collidesWith(new AlignedRectangle(2, 2, 4, 4)));

        // equal objects
        Assert.assertTrue(base.collidesWith(new AlignedRectangle(0, 0, 4, 6)));

        // object within
        Assert.assertTrue(base.collidesWith(new AlignedRectangle(1, 1, 2, 2)));

        // no intersection
        Assert.assertFalse(base.collidesWith(new AlignedRectangle(10, 10, 2, 2)));
    }

    public void testGetOrigin() {
        final AlignedRectangle rectangle = new AlignedRectangle(5, 5, 5, 5);
        final Vector origin = rectangle.getOrigin();
        Assert.assertEquals(origin.getX(), 5);
        Assert.assertEquals(origin.getY(), 5);
    }

    public void testGetWidth() {
        final AlignedRectangle rectangle = new AlignedRectangle(5, 5, 5, 5);
        Assert.assertEquals(rectangle.getWidth(), 5);
    }

    public void testGetHeight() {
        final AlignedRectangle rectangle = new AlignedRectangle(5, 5, 5, 5);
        Assert.assertEquals(rectangle.getHeight(), 5);
    }

}
