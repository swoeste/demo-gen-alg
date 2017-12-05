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
package de.swoeste.demo.gen.alg.ui.model;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import de.swoeste.demo.gen.alg.model.Vector;
import de.swoeste.demo.gen.alg.model.world.tile.Tile;
import de.swoeste.demo.gen.alg.model.world.tile.TileAttribute;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author swoeste
 */
public class UITile implements Selectable {

    private static final Color COLOR_BODY          = Color.BLACK;
    private static final Color COLOR_BODY_SELECTED = Color.RED;

    private final Tile         tile;

    private boolean            selected;

    public UITile(final Tile tile) {
        this.tile = tile;
        this.selected = false;
    }

    public Vector getPosition() {
        return this.tile.getPosition();
    }

    public int getSize() {
        return this.tile.getSize();
    }

    public Color getColor() {
        final int r = this.tile.getAttributeValue(TileAttribute.COLOR_R);
        final int g = this.tile.getAttributeValue(TileAttribute.COLOR_G);
        final int b = this.tile.getAttributeValue(TileAttribute.COLOR_B);
        return RGBColorUtil.getColor(r, g, b);
    }

    public void draw(final GraphicsContext gc) {
        final Vector position = this.tile.getPosition();

        // tile
        gc.setFill(getColor());
        gc.fillRect(position.getX(), position.getY(), getSize(), getSize());

        // border
        gc.setStroke(COLOR_BODY);
        gc.strokeRect(position.getX(), position.getY(), getSize(), getSize());

        if (this.selected) {
            gc.setStroke(COLOR_BODY_SELECTED);
            gc.strokeRect(position.getX() + 1, position.getY() + 1, getSize() - 2, getSize() - 2);
        }
    }

    public List<UIProperty> getProperties() {
        final List<UIProperty> result = new ArrayList<>();

        for (TileAttribute creatureAttribute : TileAttribute.values()) {
            final int attributeValue = this.tile.getAttributeValue(creatureAttribute);
            // TODO check if the tile has the attribute?
            result.add(new UIProperty(creatureAttribute.name(), String.valueOf(attributeValue)));
        }

        return result;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return MessageFormat.format("UITile [tile={0}]", this.tile); //$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public void setSelected(final boolean selected) {
        this.selected = selected;
    }

}
