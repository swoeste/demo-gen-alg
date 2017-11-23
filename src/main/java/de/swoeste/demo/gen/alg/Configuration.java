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
package de.swoeste.demo.gen.alg;

import java.util.Random;

/**
 * @author swoeste
 */
public class Configuration {

    // world
    private int worldWidth;
    private int worldHeight;
    private int worldSeed;

    // tile
    private int tileSize;

    // creature
    private int creatureAmount;
    private int creatureSeed;

    public Configuration() {
        this.worldWidth = 50;
        this.worldHeight = 50;
        this.worldSeed = new Random().nextInt();
        this.tileSize = 20;
        this.creatureAmount = 25;
        this.creatureSeed = new Random().nextInt();
    }

    public int getWorldWidth() {
        return this.worldWidth;
    }

    public int getWorldHeight() {
        return this.worldHeight;
    }

    public int getWorldSeed() {
        return this.worldSeed;
    }

    public int getTileSize() {
        return this.tileSize;
    }

    public int getCreatureAmount() {
        return this.creatureAmount;
    }

    public int getCreatureSeed() {
        return this.creatureSeed;
    }

    public void setWorldWidth(final int worldWidth) {
        this.worldWidth = worldWidth;
    }

    public void setWorldHeight(final int worldHeight) {
        this.worldHeight = worldHeight;
    }

    public void setWorldSeed(final int worldSeed) {
        this.worldSeed = worldSeed;
    }

    public void setTileSize(final int tileSize) {
        this.tileSize = tileSize;
    }

    public void setCreatureAmount(final int creatureAmount) {
        this.creatureAmount = creatureAmount;
    }

    public void setCreatureSeed(final int creatureSeed) {
        this.creatureSeed = creatureSeed;
    }

}
