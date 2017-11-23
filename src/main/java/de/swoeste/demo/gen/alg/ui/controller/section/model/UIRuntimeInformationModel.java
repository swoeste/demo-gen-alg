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

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UIRuntimeInformationModel {

    private final StringProperty age           = new SimpleStringProperty();
    private final StringProperty aps           = new SimpleStringProperty();
    private final StringProperty fps           = new SimpleStringProperty();

    private final StringProperty creatures     = new SimpleStringProperty();
    private final StringProperty deadCreatures = new SimpleStringProperty();

    public StringProperty getAge() {
        return this.age;
    }

    public void setAge(final String value) {
        this.age.set(value);
    }

    public StringProperty getAPS() {
        return this.aps;
    }

    public void setAPS(final String value) {
        this.aps.set(value);
    }

    public StringProperty getFPS() {
        return this.fps;
    }

    public void setFPS(final String value) {
        this.fps.set(value);
    }

    public StringProperty getCreatures() {
        return this.creatures;
    }

    public void setCreatures(final String value) {
        this.creatures.set(value);
    }

    public StringProperty getDeadCreatures() {
        return this.deadCreatures;
    }

    public void setDeadCreatures(final String value) {
        this.deadCreatures.set(value);
    }

}