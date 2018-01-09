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
import de.swoeste.demo.gen.alg.ui.controller.section.model.UICreatureHistoryModel;
import de.swoeste.demo.gen.alg.ui.model.UICreature;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.stage.Window;

/**
 * @author swoeste
 */
public class UICreatureHistoryController extends AbstractUISectionController {

    private final UICreatureHistoryModel model;

    @FXML
    private TableView<UICreature>        tblCreatureHistory;

    @FXML
    private MenuItem                     ctxItemDetails;

    public UICreatureHistoryController() {
        this.model = getBackingBean().getCreatureHistory();
    }

    @FXML
    void initialize() {
        this.ctxItemDetails.setDisable(true);

        initializeListeners();
        initializeBindings();
    }

    private void initializeListeners() {
        this.tblCreatureHistory.getSelectionModel().selectedItemProperty().addListener(new ChangeListenerImplementation());
    }

    private void initializeBindings() {
        this.tblCreatureHistory.itemsProperty().bind(this.model.getCreatures());
    }

    @FXML
    void showDetails(final ActionEvent event) {
        final Window owner = this.tblCreatureHistory.getScene().getWindow();
        final UICreature selectedItem = this.tblCreatureHistory.getSelectionModel().getSelectedItem();
        if (selectedItem instanceof UICreature) {
            UICreatureNetworkDialogController.show(owner, selectedItem);
        }
    }

    /**
     * @author swoeste
     */
    private final class ChangeListenerImplementation implements ChangeListener<UICreature> {
        @Override
        public void changed(final ObservableValue<? extends UICreature> observable, final UICreature oldValue, final UICreature newValue) {
            final boolean disabled = newValue == null;
            UICreatureHistoryController.this.ctxItemDetails.setDisable(disabled);
        }
    }

}
