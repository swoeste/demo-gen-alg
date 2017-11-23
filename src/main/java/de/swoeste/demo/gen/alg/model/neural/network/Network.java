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
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.Validate;

/**
 * @author swoeste
 */
public class Network {

    private final int    seed;
    private final Random random;

    private Layer        inputLayer;
    private Layer        outputLayer;
    private List<Layer>  hiddenLayers;

    public Network(final int input, final int[] hidden, final int output, final int seed) {
        Validate.isTrue(input >= 1, "Expected at least 1 input neuron."); //$NON-NLS-1$
        // TODO validate hidden
        Validate.isTrue(output >= 1, "Expected at least 1 output neuron."); //$NON-NLS-1$

        this.seed = seed;
        this.random = new Random(seed);

        createInputLayer(input);
        createHiddenLayer(hidden);
        createOutputLayer(output);
        createConnections();
    }

    private void createInputLayer(final int size) {
        this.inputLayer = new Layer("Input"); //$NON-NLS-1$

        // create input neurons
        for (int i = 0; i < size; i++) {
            this.inputLayer.addNeuron(new InputNeuron("Input_" + i)); //$NON-NLS-1$
        }

        // create bias neuron
        this.inputLayer.addNeuron(new BiasNeuron("Bias_Input")); //$NON-NLS-1$
    }

    private void createHiddenLayer(final int[] sizePerLayer) {
        this.hiddenLayers = new ArrayList<>();

        for (int i = 0; i < sizePerLayer.length; i++) {
            final Layer layer = new Layer("Hidden_" + i); //$NON-NLS-1$

            final int size = sizePerLayer[i];
            for (int j = 0; j < size; j++) {
                layer.addNeuron(new Neuron("Hidden_" + i + "_" + j)); //$NON-NLS-1$ //$NON-NLS-2$
            }

            // create bias
            layer.addNeuron(new BiasNeuron("Hidden_" + i + "_Bias")); //$NON-NLS-1$ //$NON-NLS-2$

            this.hiddenLayers.add(layer);
        }
    }

    private void createOutputLayer(final int size) {
        this.outputLayer = new Layer("Output"); //$NON-NLS-1$

        // create output neurons
        for (int i = 0; i < size; i++) {
            this.outputLayer.addNeuron(new Neuron("Output_" + i)); //$NON-NLS-1$
        }
    }

    private void createConnections() {
        final List<Layer> allLayers = new ArrayList<>();
        allLayers.addAll(this.hiddenLayers);
        allLayers.add(this.outputLayer);

        Layer prevLayer = this.inputLayer;
        for (Layer layer : allLayers) {

            final List<Neuron> neurons = prevLayer.getNeurons();
            for (Neuron neuron : neurons) {

                List<Neuron> neurons2 = layer.getNeurons();
                for (Neuron neuron2 : neurons2) {
                    if (neuron2 instanceof BiasNeuron) {
                        continue;
                    }

                    neuron.connect(neuron2, this.random.nextDouble());
                }
            }

            prevLayer = layer;
        }
    }

    public double[] feed(final double[] inputValues) {
        // TODO check inputvalues = inputlayer size - 1 (bias)

        final List<Neuron> inputNeurons = this.inputLayer.getNeurons();
        for (int i = 0; i < (inputNeurons.size() - 1); i++) {
            final Neuron inputNeuron = inputNeurons.get(i);
            ((InputNeuron) inputNeuron).setInputValue(inputValues[i]);
        }

        final List<Neuron> outputNeurons = this.outputLayer.getNeurons();
        final double[] result = new double[outputNeurons.size()];
        for (int i = 0; i < outputNeurons.size(); i++) {
            final Neuron outputNeuron = outputNeurons.get(i);
            result[i] = outputNeuron.getOutputValue();
        }

        return result;
    }

    public Network evolve() {
        // TODO implement

        // TODO add parameter for mutation chance

        // create a new network, based on this one with a chance of a mutation

        return null;
    }

    public Network evolve(final Network network) {
        // TODO implement

        // TODO add parameter for mutation chance

        // create a new network, based on this and the given one with a chance of a mutation

        return null;
    }

}
