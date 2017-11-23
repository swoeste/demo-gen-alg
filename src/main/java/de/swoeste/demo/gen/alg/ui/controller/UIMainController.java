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
package de.swoeste.demo.gen.alg.ui.controller;

import de.swoeste.demo.gen.alg.model.Vector;
import de.swoeste.demo.gen.alg.ui.controller.model.UIMain;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;

public class UIMainController {

    private final UIBackingBean backingBean;
    private final UIMain     model;

    @FXML
    private Canvas              worldCanvas;

    @FXML
    private Canvas              overlayCanvas;

    public UIMainController() {
        this.backingBean = UIBackingBean.getInstance();
        this.model = this.backingBean.getControl();
    }

    @FXML
    void initialize() {
        this.backingBean.setWorldGC(this.worldCanvas.getGraphicsContext2D());
        this.backingBean.setOverlayGC(this.overlayCanvas.getGraphicsContext2D());

        initializeBindings();
        initializeHandler();
    }

    private void initializeBindings() {
        this.worldCanvas.widthProperty().bind(this.model.getWorldWidth());
        this.worldCanvas.heightProperty().bind(this.model.getWorldHeight());
        this.overlayCanvas.widthProperty().bind(this.model.getWorldWidth());
        this.overlayCanvas.heightProperty().bind(this.model.getWorldHeight());
    }

    private void initializeHandler() {
        this.overlayCanvas.setOnMouseClicked(event -> UIMainController.this.backingBean.setSelection(new Vector(event.getX(), event.getY())));
    }

}
