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
package de.swoeste.demo.gen.alg.model.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.swoeste.demo.gen.alg.Configuration;
import de.swoeste.demo.gen.alg.event.SimpleEvent;
import de.swoeste.demo.gen.alg.event.SimpleEventBus;
import de.swoeste.demo.gen.alg.event.SimpleEventListener;
import de.swoeste.demo.gen.alg.event.SimpleEventType;
import de.swoeste.demo.gen.alg.model.Rectangle;
import de.swoeste.demo.gen.alg.model.Vector;
import de.swoeste.demo.gen.alg.model.creature.Creature;
import de.swoeste.demo.gen.alg.model.creature.CreatureAttribute;
import de.swoeste.demo.gen.alg.model.creature.CreatureFactory;
import de.swoeste.demo.gen.alg.model.world.tile.Tile;
import de.swoeste.demo.gen.alg.model.world.tile.TileFactory;
import de.swoeste.demo.gen.alg.position.PositionTracker;
import de.swoeste.demo.gen.alg.util.FPSCalculator;
import de.swoeste.demo.gen.alg.util.FastNoise;

/**
 * @author swoeste
 */
public class World implements SimpleEventListener {

    private static final Logger             LOG = LoggerFactory.getLogger(World.class);

    private final SimpleEventBus            eventBus;

    private final List<Tile>                tiles;
    private final List<Creature>            creatures;
    private final List<Creature>            creatureHistory;
    private final PositionTracker<Creature> creaturePositionTracker;

    private final int                       worldWidthTiles;
    private final int                       worldHeightTiles;
    private final int                       worldWidthPixel;
    private final int                       worldHeightPixel;
    private final int                       tileSize;
    private final int                       worldSeed;
    private final int                       creatureSeed;

    private final Random                    random;

    private final FPSCalculator             fpsCalculator;
    private long                            age;

    public World(final Configuration config) {
        this.eventBus = new SimpleEventBus();
        this.eventBus.registerListener(SimpleEventType.CREATURE_SIZE_CHANGED, this);
        this.eventBus.registerListener(SimpleEventType.CREATURE_POS_CHANGED, this);
        this.eventBus.registerListener(SimpleEventType.CREATURE_DIED, this);

        this.worldWidthTiles = config.getWorldWidth();
        this.worldHeightTiles = config.getWorldHeight();
        this.tileSize = config.getTileSize();
        this.worldWidthPixel = this.worldWidthTiles * this.tileSize;
        this.worldHeightPixel = this.worldHeightTiles * this.tileSize;

        this.worldSeed = config.getWorldSeed();
        this.random = new Random(this.worldSeed);
        this.creatureSeed = config.getCreatureSeed();

        this.tiles = createTiles();
        this.creatures = createCreatures(config.getCreatureAmount());
        this.creatureHistory = new LinkedList<>();
        this.creaturePositionTracker = createCreaturePositionTracker();

        this.fpsCalculator = new FPSCalculator();
        this.age = 0;
    }

    private List<Tile> createTiles() {
        final TileFactory factory = new TileFactory(this.worldSeed);
        final FastNoise noise = new FastNoise(this.worldSeed);

        final List<Tile> result = new ArrayList<>(this.worldWidthTiles * this.worldHeightTiles);
        for (int x = 0; x < this.worldWidthTiles; x++) {
            for (int y = 0; y < this.worldHeightTiles; y++) {
                final Vector position = new Vector(x * this.tileSize, y * this.tileSize);

                final double noiseValue = (1 * noise.getSimplex(1.0f * x, 1.0f * y))  //
                        + (0.5 * noise.getSimplex(2.0f * x, 2.0f * y)) //
                        + (0.25 * noise.getSimplex(4.0f * x, 4.0f * y));

                final Tile tile = factory.create(position, this.tileSize, noiseValue);
                result.add(tile);
            }
        }

        return result;
    }

    private List<Creature> createCreatures(final int amount) {
        final CreatureFactory factory = new CreatureFactory(this.creatureSeed);
        final List<Creature> result = new ArrayList<>();

        int count = 0;
        while (count < amount) {
            final int x = this.random.nextInt(this.worldWidthPixel);
            final int y = this.random.nextInt(this.worldHeightPixel);
            final Vector position = new Vector(x, y);

            final Tile tile = getTile(position);

            if (tile.isPlaceable()) {
                result.add(factory.create(this, position));
                count++;
            }
        }

        return result;
    }

    private PositionTracker<Creature> createCreaturePositionTracker() {
        final Rectangle worldShape = new Rectangle(0, 0, this.worldWidthPixel, this.worldHeightPixel);
        final PositionTracker<Creature> tracker = new PositionTracker<>(worldShape);

        for (Creature creature : this.creatures) {
            tracker.addElement(creature);
        }

        return tracker;
    }

    public List<Tile> getTiles() {
        return this.tiles;
    }

    public void update() {
        this.age++;
        this.fpsCalculator.pass();

        LOG.debug("starting of iteration {} with {} living creatures", this.age, this.creatures.size()); //$NON-NLS-1$

        // tiles
        this.tiles.parallelStream().forEach(Tile::tick);

        // creatures
        for (Iterator<Creature> iterator = this.creatures.iterator(); iterator.hasNext();) {
            Creature creature = iterator.next();
            creature.update();
            if (creature.getAttributeValue(CreatureAttribute.HEALTH) <= 0) {
                iterator.remove();
                LOG.warn("{} died alone after {} steps", creature, this.age); //$NON-NLS-1$
                this.creatureHistory.add(0, creature);
                this.eventBus.fireEvent(SimpleEventType.CREATURE_DIED, creature);
            }
        }

        // any further living things ...
    }

    public List<Creature> getCreatures() {
        return this.creatures;
    }

    public int getWorldHeightTiles() {
        return this.worldHeightTiles;
    }

    public int getWorldHeightPixel() {
        return this.worldHeightPixel;
    }

    public int getWorldWidthTiles() {
        return this.worldWidthTiles;
    }

    public int getWorldWidthPixel() {
        return this.worldWidthPixel;
    }

    public int getTileSize() {
        return this.tileSize;
    }

    public long getAge() {
        return this.age;
    }

    public double getAPS() {
        return this.fpsCalculator.getFrameRate();
    }

    public List<Creature> getCreatureHistory() {
        return Collections.unmodifiableList(this.creatureHistory);
    }

    // TODO write a unit test for this !
    public Tile getTile(final Vector position) {
        final int tileX = (int) position.getX() / this.tileSize;
        final int tileY = (int) position.getY() / this.tileSize;
        return this.tiles.get(tileY + (tileX * this.worldHeightTiles));
    }

    public boolean isPositionInWorld(final Vector position) {
        return (position.getX() >= 0) && (position.getX() < this.worldWidthPixel) && //
                (position.getY() >= 0) && (position.getY() < this.worldHeightPixel);
    }

    public SimpleEventBus getEventBus() {
        return this.eventBus;
    }

    public List<Creature> getCreaturesInArea(final Rectangle area) {
        return this.creaturePositionTracker.getElementsInArea(area);
    }

    /** {@inheritDoc} */
    @Override
    public void handle(final SimpleEvent event) {
        //
        if (SimpleEventType.CREATURE_POS_CHANGED.equals(event.getType()) || SimpleEventType.CREATURE_SIZE_CHANGED.equals(event.getType())) {
            final Creature creature = (Creature) event.getData();
            this.creaturePositionTracker.removeElement(creature);
            this.creaturePositionTracker.addElement(creature);
            this.creaturePositionTracker.isValid();
            return;
        }
        //
        if (SimpleEventType.CREATURE_DIED.equals(event.getType())) {
            final Creature creature = (Creature) event.getData();
            this.creaturePositionTracker.removeElement(creature);
            this.creaturePositionTracker.isValid();
            return;
        }
    }

}
