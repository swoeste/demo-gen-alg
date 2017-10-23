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
package de.swoeste.demo.gen.alg.model.neuron;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.swoeste.demo.gen.alg.model.Connection;

/**
 * @author swoeste
 */
public class Neuron {

    private static final Logger    LOG            = LoggerFactory.getLogger(Neuron.class);

    private final List<Connection> connectionsIn  = new ArrayList<>();
    private final List<Connection> connectionsOut = new ArrayList<>();

    private final String           name;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             // ?

    public Neuron(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void connect(final Neuron target) {
        LOG.debug("[{}] connecting with {}", this, target); //$NON-NLS-1$
        Connection connection = new Connection(this, target);
        this.connectionsOut.add(connection);
        target.connectionsIn.add(connection);
    }

    public double getOutputValue() {
        double sum = 0.0;

        // Sum the previous layer's outputs (which are out inputs) including the
        // bias neuron from the previous layer.

        for (Connection connection : this.connectionsIn) {
            sum = sum + connection.getNeuronValue();
        }

        return activate(sum);
    }

    public double activate(final double value) {
        // alternatives:
        // - sigmoid
        // - binary step
        // - ...
        return Math.tanh(value);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return this.name;
    }
}
