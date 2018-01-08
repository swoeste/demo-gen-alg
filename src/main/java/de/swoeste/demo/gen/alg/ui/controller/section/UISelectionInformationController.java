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

import de.swoeste.demo.gen.alg.ui.controller.dialog.UICreatureNetworkDialogController;
import de.swoeste.demo.gen.alg.ui.controller.section.model.UISelectionInformationModel;
import de.swoeste.demo.gen.alg.ui.model.Selectable;
import de.swoeste.demo.gen.alg.ui.model.UICreature;
import de.swoeste.demo.gen.alg.ui.model.UIProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Window;

/**
 * @author swoeste
 */
public class UISelectionInformationController extends AbstractUISectionController {

    private final UISelectionInformationModel model;

    @FXML
    private Label                             lblName;

    @FXML
    private Label                             lblPositionX;

    @FXML
    private Label                             lblPositionY;

    @FXML
    private Button                            btnDetails;

    @FXML
    private TableView<UIProperty>             tblProperties;

    public UISelectionInformationController() {
        this.model = getBackingBean().getSelectionInformation();
    }

    @FXML
    void initialize() {
        this.btnDetails.setDisable(true);

        initializeListeners();
        initializeBindings();
    }

    private void initializeListeners() {
        this.model.getSelection().addListener(new ChangeListenerImplementation());
    }

    private void initializeBindings() {
        this.lblName.textProperty().bind(this.model.getName());
        this.lblPositionX.textProperty().bind(this.model.getPositionX());
        this.lblPositionY.textProperty().bind(this.model.getPositionY());
        this.tblProperties.itemsProperty().bind(this.model.getProperties());
    }

    @FXML
    void showDetails(final ActionEvent event) {
        final Window owner = this.btnDetails.getScene().getWindow();
        final Selectable selectable = this.model.getSelection().get();

        if (selectable instanceof UICreature) {
            UICreatureNetworkDialogController.show(owner, (UICreature) selectable);
        }
    }

    /**
     * @author swoeste
     */
    private final class ChangeListenerImplementation implements ChangeListener<Selectable> {
        @Override
        public void changed(final ObservableValue<? extends Selectable> observable, final Selectable oldValue, final Selectable newValue) {
            if (newValue instanceof UICreature) {
                UISelectionInformationController.this.btnDetails.setDisable(false);
            } else {
                UISelectionInformationController.this.btnDetails.setDisable(true);
            }
        }
    }

}
