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

import java.util.Random;

import de.swoeste.demo.gen.alg.model.Vector;

/**
 * @author swoeste
 */
public class TileFactory {

    private final int    seed;
    private final Random random;

    public TileFactory(final int seed) {
        this.seed = seed;
        this.random = new Random(seed);
    }

    public Tile create(final Vector pos, final int size, final double noise) {
        if (noise <= -0.9) {
            return new VoidTile(pos, size, noise);
        } else if (noise <= -0.4) {
            return new WaterTile(pos, size, noise);
        } else if (noise <= -0.3) {
            return new SandTile(pos, size, noise);
        } else if (noise <= 0.1) {
            return new GrassTile(pos, size, noise);
        } else if (noise <= 0.35) {
            return new MountainTile(pos, size, noise);
        } else {
            return new SnowTile(pos, size, noise);
        }
    }

}
