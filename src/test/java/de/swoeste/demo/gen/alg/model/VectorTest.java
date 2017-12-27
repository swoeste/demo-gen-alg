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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.testng.Assert;
import org.testng.annotations.Test;

import de.swoeste.demo.gen.alg.model.polygon.Vector;

/**
 * @author swoeste
 */
@Test
public class VectorTest {

    private final static DecimalFormat DF = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.ENGLISH)); //$NON-NLS-1$

    public void testAdd() {
        final Vector base = new Vector(4.5, 3.5);
        final Vector add = new Vector(0.5, -0.5);
        final Vector result = base.add(add);
        Assert.assertEquals(result.getX(), 5.0);
        Assert.assertEquals(result.getY(), 3.0);
    }

    public void testSubstract() {
        final Vector base = new Vector(4.5, 2.5);
        final Vector substract = new Vector(0.5, -0.5);
        final Vector result = base.substract(substract);
        Assert.assertEquals(result.getX(), 4.0);
        Assert.assertEquals(result.getY(), 3.0);
    }

    public void testMultiply() {
        final Vector base = new Vector(4.5, 2.5);
        final double multiplyer = 4;
        final Vector result = base.multiply(multiplyer);
        Assert.assertEquals(result.getX(), 18.0);
        Assert.assertEquals(result.getY(), 10.0);
    }

    public void testDivide() {
        final Vector base = new Vector(4.5, 2.5);
        final double divisor = 4;
        final Vector result = base.divide(divisor);
        Assert.assertEquals(result.getX(), 1.125);
        Assert.assertEquals(result.getY(), 0.625);
    }

    public void testProject() {
        final Vector base = new Vector(5, 5);

        final Vector projection0 = base.project(Math.toRadians(0), 3);
        Assert.assertEquals(projection0.getX(), 8.0);
        Assert.assertEquals(projection0.getY(), 5.0);

        final Vector projection90 = base.project(Math.toRadians(90), 3);
        Assert.assertEquals(projection90.getX(), 5.0);
        Assert.assertEquals(projection90.getY(), 8.0);

        final Vector projection180 = base.project(Math.toRadians(180), 3);
        Assert.assertEquals(projection180.getX(), 2.0);
        Assert.assertEquals(projection180.getY(), 5.0);

        final Vector projection270 = base.project(Math.toRadians(270), 3);
        Assert.assertEquals(DF.format(projection270.getX()), "5"); //$NON-NLS-1$
        Assert.assertEquals(DF.format(projection270.getY()), "2"); //$NON-NLS-1$
    }

    public void testMod() {
        final Vector base = new Vector(4.6, 2.4);
        final Vector divisor = new Vector(0.5, -0.5);
        final Vector result = base.mod(divisor);
        Assert.assertEquals(DF.format(result.getX()), "0.1"); //$NON-NLS-1$
        Assert.assertEquals(DF.format(result.getY()), "0.4"); //$NON-NLS-1$
    }

    public void testDistanceOn1Axis() {
        final Vector pointA = new Vector(4.6, 2.4);
        final Vector pointB = new Vector(0.5, 2.4);
        final double resultAB = pointA.distance(pointB);
        final double resultBA = pointB.distance(pointA);
        Assert.assertEquals(resultAB, resultBA);
        Assert.assertEquals(DF.format(resultAB), "4.1"); //$NON-NLS-1$
    }

    public void testDistanceOn2Axis() {
        final Vector pointA = new Vector(4.6, 2.4);
        final Vector pointB = new Vector(0.5, -0.5);
        final double resultAB = pointA.distance(pointB);
        final double resultBA = pointB.distance(pointA);
        Assert.assertEquals(resultAB, resultBA);
        Assert.assertEquals(DF.format(resultAB), "5.02"); //$NON-NLS-1$
    }

    public void testDotProduct() {
        final Vector pointA = new Vector(1.25, 2);
        final Vector pointB = new Vector(0.5, 4);
        final double resultAB = pointA.dotProduct(pointB);
        final double resultBA = pointB.dotProduct(pointA);
        Assert.assertEquals(resultAB, resultBA);
        Assert.assertEquals(DF.format(resultAB), "8.62"); //$NON-NLS-1$
    }

    public void testNormalize() {
        final Vector point = new Vector(20, 10);
        final Vector normalize = point.normalize();
        Assert.assertEquals(normalize.getX(), 2 / Math.sqrt(5));
        Assert.assertEquals(normalize.getY(), 1 / Math.sqrt(5));
    }

    public void testPerpendicularize() {
        final Vector point = new Vector(0.5, 4);
        final Vector perpendicularize = point.perpendicularize();
        Assert.assertEquals(perpendicularize.getX(), 4.0);
        Assert.assertEquals(perpendicularize.getY(), -0.5);
    }

}
