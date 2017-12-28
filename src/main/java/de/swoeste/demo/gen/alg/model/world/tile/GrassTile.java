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
package de.swoeste.demo.gen.alg.model.world.tile;

import de.swoeste.demo.gen.alg.model.RGBColor;
import de.swoeste.demo.gen.alg.model.polygon.Vector;

/**
 * @author swoeste
 */
public class GrassTile extends AbstractTile {

    public GrassTile(final Vector position, final int size, final double height) {
        super(position, size, height, true, true);
    }

    /** {@inheritDoc} */
    @Override
    void init() {
        this.setAttributeValue(TileAttribute.COLOR_R, RGBColor.GREEN.getRed());
        this.setAttributeValue(TileAttribute.COLOR_G, RGBColor.GREEN.getGreen());
        this.setAttributeValue(TileAttribute.COLOR_B, RGBColor.GREEN.getBlue());

        this.setAttributeValue(TileAttribute.STEP_COST, 1);

        this.setAttributeValue(TileAttribute.MAX_FOOD, 200);
        this.setAttributeValue(TileAttribute.FOOD, 200);
    }

    /** {@inheritDoc} */
    @Override
    public void update() {
        final int maxFood = this.getAttributeValue(TileAttribute.MAX_FOOD);
        this.increaseAttributeByValue(TileAttribute.FOOD, 1, maxFood);
    }

}
