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
package de.swoeste.demo.gen.alg.position;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.swoeste.demo.gen.alg.model.Rectangle;

/**
 * @author swoeste
 */
@Test(singleThreaded = true)
public class PositionTrackerTest {

    private PositionTracker<IdentifiableStub> positionTracker;
    private List<IdentifiableStub>            elements;

    @BeforeClass
    public void init() {
        this.positionTracker = new PositionTracker<>(new Rectangle(0, 0, 100, 100));
        this.elements = new ArrayList<>();

        for (int i = 0; i <= 10; i++) {
            for (int j = 0; j <= 10; j++) {
                final Rectangle shape = new Rectangle(i * 10, j * 10, 5, 5);
                final int id = (i * 100) + j;
                final IdentifiableStub element = new IdentifiableStub(id, shape);
                this.positionTracker.addElement(element);
                this.elements.add(element);
            }
        }

        Assert.assertEquals(this.positionTracker.countElements(), this.elements.size());
        Assert.assertEquals(this.positionTracker.getAllElements().size(), this.elements.size());
    }

    public void testRemoveElement() {
        for (IdentifiableStub identifiableStub : this.elements) {
            this.positionTracker.removeElement(identifiableStub);
            this.positionTracker.isValid();
        }
        Assert.assertEquals(this.positionTracker.countElements(), 0);
        Assert.assertEquals(this.positionTracker.getAllElements().size(), 0);
    }

    @Test(dependsOnMethods = { "testRemoveElement" })
    public void testAddElement() {
        for (IdentifiableStub identifiableStub : this.elements) {
            this.positionTracker.addElement(identifiableStub);
            this.positionTracker.isValid();
        }
        Assert.assertEquals(this.positionTracker.countElements(), this.elements.size());
        Assert.assertEquals(this.positionTracker.getAllElements().size(), this.elements.size());
    }

    @Test(dependsOnMethods = { "testAddElement" })
    public void testRetrieveElements() {
        final Rectangle shape = new Rectangle(25, 25, 25, 25);

        final List<IdentifiableStub> retrievedElements = this.positionTracker.getElementsInArea(shape);
        for (IdentifiableStub retrievedElement : retrievedElements) {
            final Rectangle retrievedShape = retrievedElement.getShape();
            if (!shape.intersect(retrievedShape)) {
                Assert.fail("Received an element which does not intersect with the requested area!"); //$NON-NLS-1$
            }
        }

        this.positionTracker.isValid();
    }

    private final class IdentifiableStub implements Identifiable, Shapeaware {

        private final int       id;
        private final Rectangle shape;

        public IdentifiableStub(final int id, final Rectangle shape) {
            this.id = id;
            this.shape = shape;
        }

        public int getId() {
            return this.id;
        }

        public Rectangle getShape() {
            return this.shape;
        }

    }

}
