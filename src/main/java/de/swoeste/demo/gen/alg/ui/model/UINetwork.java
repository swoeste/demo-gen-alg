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

import java.text.DecimalFormat;

import de.swoeste.demo.gen.alg.model.neural.network.Connection;
import de.swoeste.demo.gen.alg.model.neural.network.Layer;
import de.swoeste.demo.gen.alg.model.neural.network.Network;
import de.swoeste.demo.gen.alg.model.neural.network.Neuron;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author swoeste
 */
public class UINetwork {

    private static final DecimalFormat DECIMAL_FORMAT  = new DecimalFormat("#.##"); //$NON-NLS-1$

    private static final int           DIST_X          = 300;
    private static final int           DIST_Y          = 100;
    private static final int           NEURON_DIAMETER = 20;

    private final Network              network;

    public UINetwork(final Network network) {
        this.network = network;
    }

    public void draw(final GraphicsContext gc) {
        gc.setStroke(Color.RED);
        gc.setFill(Color.BLACK);

        int posX = 50;
        for (Layer layer : this.network.getAllLayer()) {
            int posY = 50;

            int prevConnectionX = 0;
            int prevConnectionY = 0;

            for (Neuron neuron : layer.getNeurons()) {
                int prevLayerY = 50 + (NEURON_DIAMETER / 2);

                for (Connection connection : neuron.getConnectionsIn()) {

                    final int prevLayerX = (posX - DIST_X) + NEURON_DIAMETER;
                    gc.strokeLine(prevLayerX, prevLayerY, posX, posY + (NEURON_DIAMETER / 2));

                    final double weight = connection.getWeight();
                    gc.fillText(DECIMAL_FORMAT.format(weight), prevLayerX + prevConnectionX, prevLayerY + prevConnectionY);

                    prevLayerY = prevLayerY + DIST_Y;
                }

                prevConnectionX = prevConnectionX + 8;
                prevConnectionY = prevConnectionY + 8;

                gc.fillOval(posX, posY, NEURON_DIAMETER, NEURON_DIAMETER);

                posY = posY + DIST_Y;
            }

            posX = posX + DIST_X;
        }

    }

    // TODO gewichte noch einmal zum schluss, aktuell sind die unter den connections!

}
