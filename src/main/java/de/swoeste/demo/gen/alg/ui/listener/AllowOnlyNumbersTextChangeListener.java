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
package de.swoeste.demo.gen.alg.ui.listener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * @author swoeste
 */
public final class AllowOnlyNumbersTextChangeListener implements ChangeListener<String> {

    private static final String EMPTY            = "";       //$NON-NLS-1$
    private static final String REGEX_NUMBER     = "\\d*";   //$NON-NLS-1$
    private static final String REGEX_NOT_NUMBER = "[^\\d]"; //$NON-NLS-1$

    private final TextField     textField;

    public AllowOnlyNumbersTextChangeListener(final TextField textField) {
        this.textField = textField;
    }

    @Override
    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
        if (!newValue.matches(REGEX_NUMBER)) {
            this.textField.setText(newValue.replaceAll(REGEX_NOT_NUMBER, EMPTY));
        }
    }
}