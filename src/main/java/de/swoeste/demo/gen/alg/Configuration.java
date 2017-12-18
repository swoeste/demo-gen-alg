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

import de.swoeste.demo.gen.alg.model.neural.network.activation.ActivationFunctionType;

/**
 * @author swoeste
 */
public class Configuration {

    // general
    private ActivationFunctionType activationFunction;

    // world
    private int                    worldWidthTiles;
    private int                    worldHeightTiles;
    private int                    worldWidthPixel;
    private int                    worldHeightPixel;
    private int                    worldSeed;

    // tile
    private int                    tileSize;

    // creature
    private int                    creatureAmount;
    private int                    creatureSeed;

    public Configuration() {
        this.activationFunction = ActivationFunctionType.TANH;
        this.tileSize = 20;
        this.worldWidthTiles = 50;
        this.worldHeightTiles = 50;
        this.worldWidthPixel = this.worldWidthTiles * this.tileSize;
        this.worldHeightPixel = this.worldHeightTiles * this.tileSize;
        this.worldSeed = new Random().nextInt();
        this.creatureAmount = 25;
        this.creatureSeed = new Random().nextInt();
    }

    public ActivationFunctionType getActivationFunction() {
        return this.activationFunction;
    }

    public void setActivationFunction(final ActivationFunctionType activationFunction) {
        this.activationFunction = activationFunction;
    }

    public int getWorldWidthTiles() {
        return this.worldWidthTiles;
    }

    public void setWorldWidthTiles(final int worldWidthTiles) {
        this.worldWidthTiles = worldWidthTiles;
        this.worldWidthPixel = this.worldWidthTiles * this.tileSize;
    }

    public int getWorldHeightTiles() {
        return this.worldHeightTiles;
    }

    public void setWorldHeightTiles(final int worldHeightTiles) {
        this.worldHeightTiles = worldHeightTiles;
        this.worldHeightPixel = this.worldHeightTiles * this.tileSize;
    }

    public int getWorldWidthPixel() {
        return this.worldWidthPixel;
    }

    public int getWorldHeightPixel() {
        return this.worldHeightPixel;
    }

    public int getWorldSeed() {
        return this.worldSeed;
    }

    public void setWorldSeed(final int worldSeed) {
        this.worldSeed = worldSeed;
    }

    public int getTileSize() {
        return this.tileSize;
    }

    public void setTileSize(final int tileSize) {
        this.tileSize = tileSize;
        this.worldWidthPixel = this.worldWidthTiles * this.tileSize;
        this.worldHeightPixel = this.worldHeightTiles * this.tileSize;
    }

    public int getCreatureAmount() {
        return this.creatureAmount;
    }

    public void setCreatureAmount(final int creatureAmount) {
        this.creatureAmount = creatureAmount;
    }

    public int getCreatureSeed() {
        return this.creatureSeed;
    }

    public void setCreatureSeed(final int creatureSeed) {
        this.creatureSeed = creatureSeed;
    }

}
