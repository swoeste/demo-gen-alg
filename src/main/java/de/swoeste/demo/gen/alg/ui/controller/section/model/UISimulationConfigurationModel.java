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
package de.swoeste.demo.gen.alg.ui.controller.section.model;

import de.swoeste.demo.gen.alg.model.neural.network.activation.ActivationFunctionType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author swoeste
 */
public class UISimulationConfigurationModel {

    private final ObjectProperty<ActivationFunctionType> activationFunction = new SimpleObjectProperty<>();
    private final StringProperty                         worldSeed          = new SimpleStringProperty();
    private final StringProperty                         worldWidth         = new SimpleStringProperty();
    private final StringProperty                         worldHeight        = new SimpleStringProperty();
    private final StringProperty                         tileSize           = new SimpleStringProperty();
    private final StringProperty                         creatureSeed       = new SimpleStringProperty();
    private final StringProperty                         creatureAmount     = new SimpleStringProperty();

    public ObjectProperty<ActivationFunctionType> getActivationFunction() {
        return this.activationFunction;
    }

    public ActivationFunctionType getActivationFunctionValue() {
        return this.activationFunction.get();
    }

    public StringProperty getWorldSeed() {
        return this.worldSeed;
    }

    public String getWorldSeedValue() {
        return this.worldSeed.get();
    }

    public StringProperty getWorldWidth() {
        return this.worldWidth;
    }

    public String getWorldWithValue() {
        return this.worldWidth.get();
    }

    public StringProperty getWorldHeight() {
        return this.worldHeight;
    }

    public String getWorldHeightValue() {
        return this.worldHeight.get();
    }

    public StringProperty getTileSize() {
        return this.tileSize;
    }

    public String getTileSizeValue() {
        return this.tileSize.get();
    }

    public StringProperty getCreatureSeed() {
        return this.creatureSeed;
    }

    public String getCreatureSeedValue() {
        return this.creatureSeed.get();
    }

    public StringProperty getCreatureAmount() {
        return this.creatureAmount;
    }

    public String getCreatureAmountValue() {
        return this.creatureAmount.get();
    }

}
