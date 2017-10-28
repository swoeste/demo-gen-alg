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

import de.swoeste.demo.gen.alg.model.world.Tile;

/**
 * @author swoeste
 */
public class UITile {

    private final Tile tile;
    private final int  x;
    private final int  y;
    private final int  size;

    public UITile(final Tile tile, final int x, final int y, final int size) {
        this.tile = tile;
        this.x = x;
        this.y = y;
        this.size = size;
    }

    /**
     * @return the tile
     */
    public Tile getTile() {
        return this.tile;
    }

    /**
     * @return the x
     */
    public int getX() {
        return this.x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return this.y;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return this.size;
    }

}
