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
package de.swoeste.demo.gen.alg.ui.model;

import java.util.HashMap;
import java.util.Map;

import de.swoeste.demo.gen.alg.model.RGBColor;
import javafx.scene.paint.Color;

/**
 * @author swoeste
 */
public class RGBColorUtil {

    private static final Map<RGBColor, Color> CACHE = new HashMap<>();

    private RGBColorUtil() {
        // hidden
    }

    public static Color getColor(final RGBColor color) {
        if (!CACHE.containsKey(color)) {
            final Color uiColor = Color.rgb(color.getRed(), color.getGreen(), color.getBlue());
            CACHE.put(color, uiColor);
            return uiColor;
        } else {
            return CACHE.get(color);
        }
    }

    // TODO write a test for this
    public static double getDistance(final RGBColor colorA, final RGBColor colorB) {
        // ((r2 - r1)^2 + (g2 - g1)^2 + (b2 - b1)^2) ^ (1/2)
        int r = colorB.getRed() - colorA.getRed();
        int g = colorB.getGreen() - colorA.getGreen();
        int b = colorB.getBlue() - colorA.getBlue();
        return Math.pow((r * r) + (g * g) + (b * b), 0.5);
    }

}
