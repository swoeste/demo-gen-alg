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

import de.swoeste.demo.gen.alg.ui.model.Selectable;
import de.swoeste.demo.gen.alg.ui.model.UIProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 * @author swoeste
 */
public class UISelectionInformationModel {

    private final ObjectProperty<Selectable>     selection  = new SimpleObjectProperty<>();

    private final StringProperty                 name       = new SimpleStringProperty();
    private final StringProperty                 positionX  = new SimpleStringProperty();
    private final StringProperty                 positionY  = new SimpleStringProperty();

    private final SimpleListProperty<UIProperty> properties = new SimpleListProperty<>();

    public UISelectionInformationModel() {
        this.properties.set(FXCollections.observableArrayList());
    }

    public ObjectProperty<Selectable> getSelection() {
        return this.selection;
    }

    public void setSelection(final Selectable value) {
        this.selection.set(value);
    }

    public StringProperty getName() {
        return this.name;
    }

    public void setName(final String value) {
        this.name.set(value);
    }

    public StringProperty getPositionX() {
        return this.positionX;
    }

    public void setPositionX(final String value) {
        this.positionX.set(value);
    }

    public StringProperty getPositionY() {
        return this.positionY;
    }

    public void setPositionY(final String value) {
        this.positionY.setValue(value);
    }

    public SimpleListProperty<UIProperty> getProperties() {
        return this.properties;
    }

}
