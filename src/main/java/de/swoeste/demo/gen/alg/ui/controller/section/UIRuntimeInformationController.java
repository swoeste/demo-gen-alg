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

import de.swoeste.demo.gen.alg.ui.controller.section.model.UIRuntimeInformationModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * @author swoeste
 */
public class UIRuntimeInformationController extends AbstractUISectionController {

    private final UIRuntimeInformationModel model;

    @FXML
    private Label                      lblAge;

    @FXML
    private Label                      lblAPS;

    @FXML
    private Label                      lblFPS;

    @FXML
    private Label                      lblCreatures;

    @FXML
    private Label                      lblDeadCreatures;

    public UIRuntimeInformationController() {
        this.model = getBackingBean().getRuntimeInformation();
    }

    @FXML
    void initialize() {
        initializeBindings();
    }

    private void initializeBindings() {
        this.lblAge.textProperty().bind(this.model.getAge());
        this.lblAPS.textProperty().bind(this.model.getAPS());
        this.lblFPS.textProperty().bind(this.model.getFPS());
        this.lblCreatures.textProperty().bind(this.model.getCreatures());
        this.lblDeadCreatures.textProperty().bind(this.model.getDeadCreatures());
    }

}
