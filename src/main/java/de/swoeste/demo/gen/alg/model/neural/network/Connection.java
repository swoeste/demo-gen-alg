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
package de.swoeste.demo.gen.alg.model.neural.network;

/**
 * @author swoeste
 */
public class Connection {

    private final Neuron source;
    private final Neuron target;

    private double       weigth;

    public Connection(final Neuron source, final Neuron target, final double weight) {
        this.source = source;
        this.target = target;
        this.weigth = weight;
    }

    public double getNeuronValue() {
        return this.weigth * this.source.getOutputValue();
    }

    public double getWeigth() {
        return this.weigth;
    }

    public void setWeigth(final double weigth) {
        this.weigth = weigth;
    }

    public Neuron getSource() {
        return this.source;
    }

    public Neuron getTarget() {
        return this.target;
    }

}
