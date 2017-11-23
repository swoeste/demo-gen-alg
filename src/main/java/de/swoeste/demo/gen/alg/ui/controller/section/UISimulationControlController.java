/*
 * Copyright (C) 2017 Sebastian Woeste
 *
 * Licensed to Sebastian Woeste under one or more contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership. I license this file to You under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package de.swoeste.demo.gen.alg.ui.controller.section;

import de.swoeste.demo.gen.alg.ui.controller.section.model.UISimulationControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;

/**
 * @author swoeste
 */
public class UISimulationControlController extends AbstractUISectionController {

    private final UISimulationControl model;

    @FXML
    private Button                    btnStartSimulation;

    @FXML
    private Button                    btnNewSimulation;

    @FXML
    private Button                    btnStopSimulation;

    @FXML
    private Slider                    sldrSpeed;

    @FXML
    private Tooltip                   ttipSpeed;

    public UISimulationControlController() {
        this.model = getBackingBean().getSimulationControl();
    }

    @FXML
    void initialize() {
        this.btnStartSimulation.setDisable(true);
        this.btnStopSimulation.setDisable(true);
        this.sldrSpeed.setDisable(true);

        initializeToolTips();
        initializeBindings();
    }

    private void initializeToolTips() {
        bindTooltipValueToSliderValue(this.ttipSpeed, this.sldrSpeed);
    }

    private void initializeBindings() {
        this.model.getSpeed().bind(this.sldrSpeed.valueProperty());
    }

    @FXML
    void startSimulation(final ActionEvent event) {
        getBackingBean().startSimulation();
    }

    @FXML
    void newSimulation(final ActionEvent event) {
        getBackingBean().newSimulation();
        this.btnStartSimulation.setDisable(false);
        this.btnStopSimulation.setDisable(false);
        this.sldrSpeed.setDisable(false);
    }

    @FXML
    void stopSimulation(final ActionEvent event) {
        getBackingBean().stopSimulation();
    }

}
