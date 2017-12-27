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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.Validate;

import de.swoeste.demo.gen.alg.model.neural.network.activation.ActivationFunction;

/**
 * @author swoeste
 */
public class Network {

    private final int                seed;
    private final Random             random;

    private final ActivationFunction activationFunction;

    private final int                inputLayerSize;
    private final int[]              hiddenLayerSize;
    private final int                outputLayerSize;

    private Layer                    inputLayer;
    private List<Layer>              hiddenLayers;
    private Layer                    outputLayer;

    private List<Connection>         connections;

    public Network(final int input, final int[] hidden, final int output, final int seed, final ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;

        this.seed = seed;
        this.random = new Random(seed);

        this.inputLayerSize = input;
        this.hiddenLayerSize = hidden;
        this.outputLayerSize = output;

        createInputLayer();
        createHiddenLayer();
        createOutputLayer();
        createConnections();
    }

    public ActivationFunction getActivationFunction() {
        return this.activationFunction;
    }

    private void createInputLayer() {
        Validate.isTrue(this.inputLayerSize >= 1, "Expected at least 1 input neuron."); //$NON-NLS-1$

        this.inputLayer = new Layer("Input"); //$NON-NLS-1$

        // create input neurons
        for (int i = 0; i < this.inputLayerSize; i++) {
            final String neuronName = MessageFormat.format("Input_{0}", i); //$NON-NLS-1$
            this.inputLayer.addNeuron(new InputNeuron(neuronName));
        }

        // create bias neuron
        this.inputLayer.addNeuron(new BiasNeuron("Bias_Input")); //$NON-NLS-1$
    }

    private void createHiddenLayer() {
        this.hiddenLayers = new ArrayList<>();

        for (int i = 0; i < this.hiddenLayerSize.length; i++) {
            final String layerName = MessageFormat.format("Hidden_{0}", i); //$NON-NLS-1$
            final Layer layer = new Layer(layerName);

            final int size = this.hiddenLayerSize[i];
            Validate.isTrue(size >= 1, MessageFormat.format("Expected at least 1 hidden neuron in hidden layer {0}.", i)); //$NON-NLS-1$
            for (int j = 0; j < size; j++) {
                final String name = MessageFormat.format("Hidden_{0}_{1}", i, j); //$NON-NLS-1$
                layer.addNeuron(new Neuron(name, this.activationFunction));
            }

            // create bias
            final String biasName = MessageFormat.format("Hidden_{0}_Bias", i); //$NON-NLS-1$
            layer.addNeuron(new BiasNeuron(biasName));

            this.hiddenLayers.add(layer);
        }
    }

    private void createOutputLayer() {
        Validate.isTrue(this.outputLayerSize >= 1, "Expected at least 1 output neuron."); //$NON-NLS-1$

        this.outputLayer = new Layer("Output"); //$NON-NLS-1$

        // create output neurons
        for (int i = 0; i < this.outputLayerSize; i++) {
            final String name = MessageFormat.format("Output_{0}", i); //$NON-NLS-1$
            this.outputLayer.addNeuron(new Neuron(name, this.activationFunction));
        }
    }

    private void createConnections() {
        this.connections = new ArrayList<>();

        final List<Layer> allLayers = new ArrayList<>();
        allLayers.addAll(this.hiddenLayers);
        allLayers.add(this.outputLayer);

        Layer prevLayer = this.inputLayer;
        for (Layer layer : allLayers) {

            final List<Neuron> inNeurons = prevLayer.getNeurons();
            for (final Neuron inNeuron : inNeurons) {

                final List<Neuron> outNeurons = layer.getNeurons();
                for (final Neuron outNeuron : outNeurons) {
                    if (outNeuron instanceof BiasNeuron) {
                        continue;
                    }
                    final double weight = this.random.nextDouble();
                    this.connections.add(inNeuron.connect(outNeuron, weight));
                }
            }

            prevLayer = layer;
        }
    }

    public double[] feed(final double[] inputValues) {
        Validate.isTrue(inputValues.length == this.inputLayerSize, "Expected input values to match input layer neurons!"); //$NON-NLS-1$

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

    public void mutate() {
        final int connectionToMutate = this.random.nextInt(this.connections.size());
        final double weight = this.random.nextDouble();
        this.connections.get(connectionToMutate).setWeight(weight);
    }

    public Network replicate() {
        return replicate(null);
    }

    public Network replicate(final Network cohabitant) {
        final Network network = new Network(this.inputLayerSize, this.hiddenLayerSize, this.outputLayerSize, this.seed, this.activationFunction);

        // create crossover segments
        final List<Integer> segmentSizes = new ArrayList<>();
        Arrays.stream(this.hiddenLayerSize).forEach(entry -> segmentSizes.add(entry));
        segmentSizes.add(this.outputLayerSize);

        // perform crossover
        Network currentCohabitant = this;
        Network prevCohabitant = cohabitant == null ? this : cohabitant;
        Network temp;

        int checkSum = 0;
        for (Integer segmentSize : segmentSizes) {
            for (int i = 0; i < segmentSize; i++) {
                network.connections.get(i).setWeight(currentCohabitant.connections.get(i).getWeight());
                checkSum++;
            }
            // swap cohabitant
            temp = prevCohabitant;
            prevCohabitant = currentCohabitant;
            currentCohabitant = temp;
        }
        Validate.isTrue(checkSum == this.connections.size());

        // TODO configurable (mutation change + mutation possible)
        // perform a mutation ?
        final double mutationChance = 0.5;
        final boolean mutate = this.random.nextDouble() <= mutationChance;
        if (mutate) {
            network.mutate();
        }

        return network;
    }

}
