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

import java.text.MessageFormat;

/**
 * @author swoeste
 */
public class RGBColor {

    public static final RGBColor BLACK = new RGBColor(0, 0, 0);
    public static final RGBColor RED   = new RGBColor(255, 0, 0);
    public static final RGBColor GREEN = new RGBColor(0, 255, 0);
    public static final RGBColor BLUE  = new RGBColor(0, 0, 255);
    public static final RGBColor WHITE = new RGBColor(255, 255, 255);

    private final int            r;
    private final int            g;
    private final int            b;

    public RGBColor(final int red, final int green, final int blue) {
        this.r = red;
        this.g = green;
        this.b = blue;
    }

    public int getRed() {
        return this.r;
    }

    public int getGreen() {
        return this.g;
    }

    public int getBlue() {
        return this.b;
    }

    // TODO write a test for this
    public double getDistance(final RGBColor color) {
        // ((r2 - r1)^2 + (g2 - g1)^2 + (b2 - b1)^2) ^ (1/2)
        int r = color.getRed() - this.r;
        int g = color.getGreen() - this.g;
        int b = color.getBlue() - this.b;
        return Math.pow((r * r) + (g * g) + (b * b), 0.5);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return MessageFormat.format("RGBColor [red={0}, green={1}, blue={2}]", this.r, this.g, this.b); //$NON-NLS-1$
    }

}
