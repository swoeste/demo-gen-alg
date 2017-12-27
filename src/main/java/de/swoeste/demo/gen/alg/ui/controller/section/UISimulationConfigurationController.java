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
package de.swoeste.demo.gen.alg.ui.controller.section;

import java.util.Random;

import de.swoeste.demo.gen.alg.model.neural.network.activation.ActivationFunctionType;
import de.swoeste.demo.gen.alg.ui.controller.section.model.UISimulationConfigurationModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;

/**
 * @author swoeste
 */
public class UISimulationConfigurationController extends AbstractUISectionController {

    private final UISimulationConfigurationModel model;
    private final Random                         random;

    @FXML
    private ComboBox<ActivationFunctionType>     cbActivationFunction;

    @FXML
    private Spinner<Integer>                     spnrWorldSeed;

    @FXML
    private Button                               btnWorldSeedRandom;

    @FXML
    private Spinner<Integer>                     spnrWorldWidth;

    @FXML
    private Spinner<Integer>                     spnrWorldHeight;

    @FXML
    private Spinner<Integer>                     spnrTileSize;

    @FXML
    private Spinner<Integer>                     spnrCreatureSeed;

    @FXML
    private Button                               btnCreatureSeedRandom;

    @FXML
    private Spinner<Integer>                     spnrCreatureAmount;

    public UISimulationConfigurationController() {
        this.model = getBackingBean().getSimulationConfiguration();
        this.random = new Random();
    }

    @FXML
    void initialize() {
        initializeRandomValueSpinners();
        initializeComboBoxes();
        initializeBindings();
    }

    private void initializeRandomValueSpinners() {
        this.spnrCreatureSeed.getValueFactory().setValue(this.random.nextInt());
        this.spnrWorldSeed.getValueFactory().setValue(this.random.nextInt());
    }

    private void initializeComboBoxes() {
        this.cbActivationFunction.getItems().setAll(ActivationFunctionType.values());
        this.cbActivationFunction.setValue(ActivationFunctionType.TANH);
    }

    private void initializeBindings() {
        this.model.getActivationFunction().bind(this.cbActivationFunction.valueProperty());
        this.model.getWorldSeed().bind(this.spnrWorldSeed.valueProperty());
        this.model.getWorldWidth().bind(this.spnrWorldWidth.valueProperty());
        this.model.getWorldHeight().bind(this.spnrWorldHeight.valueProperty());
        this.model.getTileSize().bind(this.spnrTileSize.valueProperty());
        this.model.getCreatureSeed().bind(this.spnrCreatureSeed.valueProperty());
        this.model.getCreatureAmount().bind(this.spnrCreatureAmount.valueProperty());
    }

    @FXML
    void createRandomCreatureSeed(final ActionEvent event) {
        this.spnrCreatureSeed.getValueFactory().setValue(this.random.nextInt());
    }

    @FXML
    void createRandomWorldSeed(final ActionEvent event) {
        this.spnrWorldSeed.getValueFactory().setValue(this.random.nextInt());
    }

}
