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
import de.swoeste.demo.gen.alg.ui.listener.AllowOnlyNumbersInRangeTextChangeListener;
import de.swoeste.demo.gen.alg.ui.listener.AllowOnlyNumbersTextChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * @author swoeste
 */
public class UISimulationConfigurationController extends AbstractUISectionController {

    private final UISimulationConfigurationModel model;
    private final Random                         random;

    @FXML
    private ComboBox<ActivationFunctionType>     cbActivationFunction;

    @FXML
    private TextField                            txtWorldSeed;

    @FXML
    private Button                               btnWorldSeedRandom;

    @FXML
    private TextField                            txtWorldWidth;

    @FXML
    private TextField                            txtWorldHeight;

    @FXML
    private TextField                            txtTileSize;

    @FXML
    private TextField                            txtCreatureSeed;

    @FXML
    private Button                               btnCreatureSeedRandom;

    @FXML
    private TextField                            txtCreatureAmount;

    public UISimulationConfigurationController() {
        this.model = getBackingBean().getSimulationConfiguration();
        this.random = new Random(); // TODO move to backing bean?
    }

    @FXML
    void initialize() {
        initializeNumericTextFields();
        initializeComboBox();
        initializeBindings();
    }

    private void initializeNumericTextFields() {
        this.txtWorldSeed.textProperty().addListener(new AllowOnlyNumbersTextChangeListener(this.txtWorldSeed));
        this.txtWorldSeed.setText(String.valueOf(this.random.nextInt()));

        this.txtWorldWidth.textProperty().addListener(new AllowOnlyNumbersInRangeTextChangeListener(this.txtWorldWidth, 2, 100));
        this.txtWorldWidth.setText("20"); //$NON-NLS-1$

        this.txtWorldHeight.textProperty().addListener(new AllowOnlyNumbersInRangeTextChangeListener(this.txtWorldHeight, 2, 100));
        this.txtWorldHeight.setText("20"); //$NON-NLS-1$

        this.txtTileSize.textProperty().addListener(new AllowOnlyNumbersInRangeTextChangeListener(this.txtTileSize, 20, 200));
        this.txtTileSize.setText("20"); //$NON-NLS-1$

        this.txtCreatureSeed.textProperty().addListener(new AllowOnlyNumbersTextChangeListener(this.txtCreatureSeed));
        this.txtCreatureSeed.setText(String.valueOf(this.random.nextInt()));

        this.txtCreatureAmount.textProperty().addListener(new AllowOnlyNumbersInRangeTextChangeListener(this.txtCreatureAmount, 0, 100));
        this.txtCreatureAmount.setText("25"); //$NON-NLS-1$
    }

    private void initializeComboBox() {
        this.cbActivationFunction.getItems().setAll(ActivationFunctionType.values());
        this.cbActivationFunction.setValue(ActivationFunctionType.TANH);
    }

    private void initializeBindings() {
        this.model.getActivationFunction().bind(this.cbActivationFunction.valueProperty());
        this.model.getWorldSeed().bind(this.txtWorldSeed.textProperty());
        this.model.getWorldWidth().bind(this.txtWorldWidth.textProperty());
        this.model.getWorldHeight().bind(this.txtWorldHeight.textProperty());
        this.model.getTileSize().bind(this.txtTileSize.textProperty());
        this.model.getCreatureSeed().bind(this.txtCreatureSeed.textProperty());
        this.model.getCreatureAmount().bind(this.txtCreatureAmount.textProperty());
    }

    @FXML
    void createRandomCreatureSeed(final ActionEvent event) {
        this.txtCreatureSeed.setText(String.valueOf(this.random.nextInt()));
    }

    @FXML
    void createRandomWorldSeed(final ActionEvent event) {
        this.txtWorldSeed.setText(String.valueOf(this.random.nextInt()));
    }

}
