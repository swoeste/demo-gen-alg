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
package de.swoeste.demo.gen.alg.model.neural.network.activation;

import org.apache.commons.math3.util.FastMath;

/**
 * @author swoeste
 */
public class Sigmoid implements ActivationFunction {

    /** {@inheritDoc} */
    @Override
    public double apply(final double rawValue) {
        // Range: 0..1
        return (1.0 / (1.0 + FastMath.exp(-1.0 * rawValue)));
    }

    /** {@inheritDoc} */
    @Override
    public boolean isActive(final double activatedValue) {
        return activatedValue >= 0.5;
    }

    /** {@inheritDoc} */
    @Override
    public double normalize(final double activatedValue) {
        // change the range from 0..1 to -1..1
        return (activatedValue - 0.5) * 2;
    }

}
