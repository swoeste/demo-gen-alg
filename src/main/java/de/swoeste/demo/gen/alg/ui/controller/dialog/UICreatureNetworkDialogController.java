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
package de.swoeste.demo.gen.alg.ui.controller.dialog;

import de.swoeste.demo.gen.alg.ui.model.UICreature;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.stage.Window;

/**
 * @author swoeste
 */
public class UICreatureNetworkDialogController extends AbstractUIDialogController {

    private UICreature creature;

    @FXML
    private ScrollPane networkPane;

    @FXML
    private Canvas     networkCanvas;

    private void setCreature(final UICreature creature) {
        this.creature = creature;
    }

    // TODO
    // This implementation is different from the implementation in der UIWorldController // UIBackingBean.
    private void update() {
        final GraphicsContext graphicsContext = this.networkCanvas.getGraphicsContext2D();

        if (this.creature != null) {
            // TODO "calculate" correct size at the right place
            this.networkCanvas.setWidth(1000);
            this.networkCanvas.setHeight(2000);

            this.creature.getNetwork().draw(graphicsContext);
        }
    }

    public static void show(final Window owner, final UICreature creature) {
        final String fxml = "view/dialog_creatureNetwork.fxml";
        final String title = creature.getName();

        final UICreatureNetworkDialogController controller = showDialog(owner, fxml, title);
        controller.setCreature(creature);
        controller.update();
    }

}
