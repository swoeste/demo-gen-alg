/*
 * Copyright (C) 2017 Sebastian Woeste
 *
 * Licensed to Sebastian Woeste under one or more contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership. I license this file to You under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package de.swoeste.demo.gen.alg.util;

/**
 * @author swoeste
 */
public class FPSCalculator {

    private final long[] frameTimes     = new long[100];
    private int          frameTimeIndex = 0;
    private boolean      arrayFilled    = false;
    private double       frameRate      = 0.0;

    public void pass() {
        pass(System.nanoTime());
    }

    public void pass(final long currentNanoTime) {
        long oldFrameTime = this.frameTimes[this.frameTimeIndex];
        this.frameTimes[this.frameTimeIndex] = currentNanoTime;
        this.frameTimeIndex = (this.frameTimeIndex + 1) % this.frameTimes.length;

        if (this.frameTimeIndex == 0) {
            this.arrayFilled = true;
        }

        if (this.arrayFilled) {
            long elapsedNanos = currentNanoTime - oldFrameTime;
            long elapsedNanosPerFrame = elapsedNanos / this.frameTimes.length;
            this.frameRate = 1_000_000_000.0 / elapsedNanosPerFrame;
        }
    }

    public double getFrameRate() {
        return this.frameRate;
    }

}