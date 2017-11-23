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

import de.swoeste.demo.gen.alg.ui.controller.section.model.UISimulationConfiguration;
import de.swoeste.demo.gen.alg.ui.listener.AllowOnlyNumbersTextChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

/**
 * @author swoeste
 */
public class UISimulationConfigurationController extends AbstractUISectionController {

    private final UISimulationConfiguration model;
    private final Random                    random;

    @FXML
    private Button                          btnCreatureSeedRandom;

    @FXML
    private Button                          btnWorldSeedRandom;

    @FXML
    private Slider                          sldrWorldWidth;

    @FXML
    private Tooltip                         ttipWorldWidth;

    @FXML
    private Slider                          sldrWorldHeight;

    @FXML
    private Tooltip                         ttipWorldHeight;

    @FXML
    private Slider                          sldrTileSize;

    @FXML
    private Tooltip                         ttipTileSize;

    @FXML
    private Slider                          sldrCreatureAmount;

    @FXML
    private Tooltip                         ttipCreatureAmount;

    @FXML
    private TextField                       txtWorldSeed;

    @FXML
    private TextField                       txtCreatureSeed;

    public UISimulationConfigurationController() {
        this.model = getBackingBean().getSimulationConfiguration();
        this.random = new Random(); // TODO move to backing bean?
    }

    @FXML
    void initialize() {
        initializeToolTips();
        initializeNumericTextFields();
        initializeBindings();
    }

    private void initializeToolTips() {
        bindTooltipValueToSliderValue(this.ttipWorldWidth, this.sldrWorldWidth);
        bindTooltipValueToSliderValue(this.ttipWorldHeight, this.sldrWorldHeight);
        bindTooltipValueToSliderValue(this.ttipTileSize, this.sldrTileSize);
        bindTooltipValueToSliderValue(this.ttipCreatureAmount, this.sldrCreatureAmount);
    }

    private void initializeNumericTextFields() {
        this.txtWorldSeed.textProperty().addListener(new AllowOnlyNumbersTextChangeListener(this.txtWorldSeed));
        this.txtWorldSeed.setText(String.valueOf(this.random.nextInt()));

        this.txtCreatureSeed.textProperty().addListener(new AllowOnlyNumbersTextChangeListener(this.txtCreatureSeed));
        this.txtCreatureSeed.setText(String.valueOf(this.random.nextInt()));
    }

    private void initializeBindings() {
        this.model.getWorldSeed().bind(this.txtWorldSeed.textProperty());
        this.model.getWorldWidth().bind(this.sldrWorldWidth.valueProperty());
        this.model.getWorldHeight().bind(this.sldrWorldHeight.valueProperty());
        this.model.getTileSize().bind(this.sldrTileSize.valueProperty());
        this.model.getCreatureSeed().bind(this.txtCreatureSeed.textProperty());
        this.model.getCreatureAmount().bind(this.sldrCreatureAmount.valueProperty());
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
