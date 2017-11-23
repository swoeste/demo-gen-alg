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
package de.swoeste.demo.gen.alg.util;

/**
 * @author swoeste
 */
public class NumberUtil {

    private NumberUtil() {
        // hidden
    }

    public static double getValueInRange(final double newValue, final double minValue, final double maxValue) {
        if (newValue > maxValue) {
            return maxValue;
        } else if (newValue < minValue) {
            return minValue;
        } else {
            return newValue;
        }
    }

    public static int getValueInRange(final int newValue, final int minValue, final int maxValue) {
        if (newValue > maxValue) {
            return maxValue;
        } else if (newValue < minValue) {
            return minValue;
        } else {
            return newValue;
        }
    }

    public static double getClosestToZero(final double a, final double b) {
        if (Math.abs(a) <= Math.abs(b)) {
            return a;
        } else {
            return b;
        }
    }

    public static int round(final double value) {
        return (int) Math.round(value);
    }

}
