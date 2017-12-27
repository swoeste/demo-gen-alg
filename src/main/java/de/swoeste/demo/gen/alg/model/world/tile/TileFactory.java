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

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

import de.swoeste.demo.gen.alg.model.polygon.Vector;

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

        final AbstractTile tile;

        // XXX create extra method?
        if (noise <= -0.9) {
            tile = new VoidTile(pos, size, noise);
        } else if (noise <= -0.4) {
            tile = new WaterTile(pos, size, noise);
        } else if (noise <= -0.3) {
            tile = new SandTile(pos, size, noise);
        } else if (noise <= 0.1) {
            tile = new GrassTile(pos, size, noise);
        } else if (noise <= 0.35) {
            tile = new MountainTile(pos, size, noise);
        } else {
            tile = new SnowTile(pos, size, noise);
        }

        tile.setAttributes(createAttributes());
        tile.init();

        return tile;
    }

    private Map<TileAttribute, Integer> createAttributes() {
        final Map<TileAttribute, Integer> attributes = new EnumMap<>(TileAttribute.class);
        for (TileAttribute attribute : TileAttribute.values()) {
            attributes.put(attribute, attribute.getDefaultValue());
        }
        return attributes;
    }

}
