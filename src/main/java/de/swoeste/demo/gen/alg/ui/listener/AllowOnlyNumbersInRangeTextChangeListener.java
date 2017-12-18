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

import de.swoeste.demo.gen.alg.util.NumberUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * @author swoeste
 */
public class AllowOnlyNumbersInRangeTextChangeListener implements ChangeListener<String> {

    private static final String EMPTY            = "";                         //$NON-NLS-1$
    private static final String REGEX_NUMBER     = "\\d*";         //$NON-NLS-1$
    private static final String REGEX_NOT_NUMBER = "[^\\d]"; //$NON-NLS-1$

    private final TextField     textField;
    private final int           min;
    private final int           max;

    public AllowOnlyNumbersInRangeTextChangeListener(final TextField textField, final int min, final int max) {
        this.textField = textField;
        this.min = min;
        this.max = max;
    }

    @Override
    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
        final String numberAsString;
        if (!newValue.matches(REGEX_NUMBER)) {
            numberAsString = newValue.replaceAll(REGEX_NOT_NUMBER, EMPTY);
        } else {
            numberAsString = newValue;
        }

        // TODO check for "" (empty string)
        // TODO eingabe von 100 bei erlaubt 20..200 ist ein problem

        final int number = Integer.parseInt(numberAsString);
        final int numberInRange = NumberUtil.getValueInRange(number, this.min, this.max);
        this.textField.setText(String.valueOf(numberInRange));
    }

}