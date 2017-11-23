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
package de.swoeste.demo.gen.alg.model.creature;

/**
 * @author swoeste
 */
public enum CreatureAttribute {

    // Attributes
    AGE(0), //
    SIZE(10), MAX_SIZE(20), //
    HEALTH(100), MAX_HEALTH(100), //
    HUNGER(0), MAX_HUNGER(200), //
    THIRST(0), MAX_THIRST(100), //
    POSITION_X(0), POSITION_Y(0),//
    COLOR_R(0), COLOR_G(0), COLOR_B(0),//
    VISION_SIZE(40), //

    // SKill Attributes
    EAT(-1), //
    MOVE(-1),  //
    CHANGE_MOVE_DISTANCE(-1), MOVE_DISTANCE(4), CONSIDERED_MOVE_DISTANCE(4), MAX_MOVE_DISTANCE(10), //
    CHANGE_MOVE_DIRECTION(-1), MOVE_DIRECTION(0), CONSIDERED_MOVE_DIRECTION(0), MAX_MOVE_DIRECTION(180), //
    CHANGE_VIEW_DIRECTION(-1), VIEW_DIRECTION(0), CONSIDERED_VIEW_DIRECTION(0), MAX_VIEW_DIRECTION(180); //

    private final int defaultValue;

    private CreatureAttribute(final int defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int getDefaultValue() {
        return this.defaultValue;
    }
}
