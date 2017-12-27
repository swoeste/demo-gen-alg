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
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * @author swoeste
 */
public class UISimulationConfigurationModel {

    private final ObjectProperty<ActivationFunctionType> activationFunction = new SimpleObjectProperty<>();
    private final IntegerProperty                        worldSeed          = new SimpleIntegerProperty();
    private final IntegerProperty                        worldWidth         = new SimpleIntegerProperty();
    private final IntegerProperty                        worldHeight        = new SimpleIntegerProperty();
    private final IntegerProperty                        tileSize           = new SimpleIntegerProperty();
    private final IntegerProperty                        creatureSeed       = new SimpleIntegerProperty();
    private final IntegerProperty                        creatureAmount     = new SimpleIntegerProperty();

    public ObjectProperty<ActivationFunctionType> getActivationFunction() {
        return this.activationFunction;
    }

    public ActivationFunctionType getActivationFunctionValue() {
        return this.activationFunction.get();
    }

    public IntegerProperty getWorldSeed() {
        return this.worldSeed;
    }

    public Integer getWorldSeedValue() {
        return this.worldSeed.get();
    }

    public IntegerProperty getWorldWidth() {
        return this.worldWidth;
    }

    public Integer getWorldWithValue() {
        return this.worldWidth.get();
    }

    public IntegerProperty getWorldHeight() {
        return this.worldHeight;
    }

    public Integer getWorldHeightValue() {
        return this.worldHeight.get();
    }

    public IntegerProperty getTileSize() {
        return this.tileSize;
    }

    public Integer getTileSizeValue() {
        return this.tileSize.get();
    }

    public IntegerProperty getCreatureSeed() {
        return this.creatureSeed;
    }

    public Integer getCreatureSeedValue() {
        return this.creatureSeed.get();
    }

    public IntegerProperty getCreatureAmount() {
        return this.creatureAmount;
    }

    public Integer getCreatureAmountValue() {
        return this.creatureAmount.get();
    }

}
