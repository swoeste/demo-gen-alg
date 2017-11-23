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
package de.swoeste.demo.gen.alg.ui.controller.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * @author swoeste
 */
public class UIWorldModel {

    // TODO currently some values are ui -> model and some are model -> ui we need to find a naming pattern to sep both.

    private final IntegerProperty worldHeight = new SimpleIntegerProperty();
    private final IntegerProperty worldWidth  = new SimpleIntegerProperty();

    public IntegerProperty getWorldHeight() {
        return this.worldHeight;
    }

    public void setWorldHeight(final int value) {
        this.worldHeight.set(value);
    }

    public IntegerProperty getWorldWidth() {
        return this.worldWidth;
    }

    public void setWorldWidth(final int value) {
        this.worldWidth.set(value);
    }

}
