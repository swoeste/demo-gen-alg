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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author swoeste
 */
public class Layer {

    private final String       name;
    private final List<Neuron> neurons;

    public Layer(final String name) {
        this.name = name;
        this.neurons = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    protected void addNeuron(final Neuron neuron) {
        this.neurons.add(neuron);
    }

    public List<Neuron> getNeurons() {
        return Collections.unmodifiableList(this.neurons);
    }

}
