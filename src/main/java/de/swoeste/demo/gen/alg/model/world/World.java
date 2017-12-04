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
import de.swoeste.demo.gen.alg.util.SortedList;
import de.swoeste.demo.gen.alg.util.comparator.CreatureAttributeComparator;

/**
 * @author swoeste
 */
public class World implements SimpleEventListener {

    private static final Logger             LOG = LoggerFactory.getLogger(World.class);

    private final Configuration             config;

    private final SimpleEventBus            eventBus;

    private final Random                    worldRandom;

    private final FPSCalculator             apsCalculator;

    private final TileFactory               tileFactory;
    private final List<Tile>                tiles;

    private final CreatureFactory           creatureFactory;
    private final List<Creature>            creatures;
    private final List<Creature>            creaturesHistory;
    private final PositionTracker<Creature> creaturePositionTracker;

    private long                            age;

    public World(final Configuration config) {
        this.config = config;

        this.eventBus = new SimpleEventBus();

        this.worldRandom = new Random(config.getWorldSeed());

        this.apsCalculator = new FPSCalculator();

        this.tileFactory = new TileFactory(config.getWorldSeed());
        this.tiles = new ArrayList<>(config.getWorldWidthTiles() * config.getWorldHeightTiles());

        this.creatureFactory = new CreatureFactory(config.getCreatureSeed());
        this.creatures = new ArrayList<>();
        this.creaturesHistory = new SortedList<>(new ArrayList<>(), new CreatureAttributeComparator(CreatureAttribute.AGE));
        this.creaturePositionTracker = createCreaturePositionTracker();

        this.age = 0;

        init();
    }

    private void init() {
        // create tiles
        createTiles();

        // create creatures
        createCreatures(this.config.getCreatureAmount());

        // finally register listeners
        this.eventBus.registerListener(SimpleEventType.CREATURE_SIZE_CHANGED, this);
        this.eventBus.registerListener(SimpleEventType.CREATURE_POS_CHANGED, this);
        this.eventBus.registerListener(SimpleEventType.CREATURE_DIED, this);
    }

    private PositionTracker<Creature> createCreaturePositionTracker() {
        final Rectangle worldShape = new Rectangle(0, 0, this.config.getWorldWidthPixel(), this.config.getWorldHeightPixel());
        return new PositionTracker<>(worldShape);
    }

    private void createTiles() {
        final FastNoise noise = new FastNoise(this.config.getWorldSeed());

        for (int x = 0; x < this.config.getWorldWidthTiles(); x++) {
            for (int y = 0; y < this.config.getWorldHeightTiles(); y++) {
                final Vector position = new Vector(x * this.config.getTileSize(), y * this.config.getTileSize());

                final double n1 = 1.0 * noise.getSimplex(1.0f * x, 1.0f * y);
                final double n2 = 0.5 * noise.getSimplex(2.0f * x, 2.0f * y);
                final double n3 = 0.25 * noise.getSimplex(4.0f * x, 4.0f * y);
                final double noiseValue = n1 + n2 + n3;

                final Tile tile = this.tileFactory.create(position, this.config.getTileSize(), noiseValue);
                registerTile(tile);
            }
        }
    }

    private void registerTile(final Tile tile) {
        this.tiles.add(tile);
    }

    private void createCreatures(final int amount) {
        int count = 0;
        while (count < amount) {
            final Vector position = getRandomPositionForCreature();
            final Creature creature = this.creatureFactory.create(this, position);
            registerCreature(creature);
            count++;
        }
    }

    private Vector getRandomPositionForCreature() {
        while (true) {
            final Vector position = getRandomPositionInWorld();
            final Tile tile = getTile(position);
            if (tile.isPlaceable()) {
                return position;
            }
        }
    }

    private Vector getRandomPositionInWorld() {
        final int x = this.worldRandom.nextInt(this.config.getWorldWidthPixel());
        final int y = this.worldRandom.nextInt(this.config.getWorldHeightPixel());
        return new Vector(x, y);
    }

    public void registerCreature(final Creature creature) {
        this.creatures.add(creature);
        this.creaturePositionTracker.addElement(creature);
        this.creaturePositionTracker.isValid();
        this.eventBus.fireEvent(new SimpleEvent(SimpleEventType.CREATURE_CREATED, creature));
    }

    public void update() {
        this.age++;
        this.apsCalculator.pass();

        LOG.debug("starting of iteration {} with {} living creatures", this.age, this.creatures.size()); //$NON-NLS-1$

        // tiles
        this.tiles.parallelStream().forEach(Tile::update);

        // creatures
        for (Iterator<Creature> iterator = this.creatures.iterator(); iterator.hasNext();) {
            final Creature creature = iterator.next(); // CME if a creature is added while iterating :D
            creature.update();
            if (creature.getAttributeValue(CreatureAttribute.HEALTH) <= 0) {
                iterator.remove();
                LOG.warn("{} died after {} steps", creature, this.age); //$NON-NLS-1$
                this.eventBus.fireEvent(SimpleEventType.CREATURE_DIED, creature);
            }
        }

        // TODO just a first test
        // re-create creatures
        while (this.creatures.size() < this.config.getCreatureAmount()) {
            final Vector position = getRandomPositionForCreature();
            final Creature newCreature = this.creatureFactory.create(this, position);
            registerCreature(newCreature);
        }

        // any further living things ...
    }

    // TODO creature replication

    // IDEE:
    // - creatures replicate themself with a partner and die after some time
    // - if all creatures are dead, we restore an specific amount based on the creature history

    public int getWorldHeightTiles() {
        return this.config.getWorldHeightTiles();
    }

    public int getWorldHeightPixel() {
        return this.config.getWorldHeightPixel();
    }

    public int getWorldWidthTiles() {
        return this.config.getWorldWidthTiles();
    }

    public int getWorldWidthPixel() {
        return this.config.getWorldWidthPixel();
    }

    public int getTileSize() {
        return this.config.getTileSize();
    }

    public List<Tile> getTiles() {
        return Collections.unmodifiableList(this.tiles);
    }

    public List<Creature> getCreatures() {
        return Collections.unmodifiableList(this.creatures);
    }

    public long getAge() {
        return this.age;
    }

    public double getAPS() {
        return this.apsCalculator.getFrameRate();
    }

    // TODO write a unit test for this !
    public Tile getTile(final Vector position) {
        final int tileX = (int) position.getX() / this.config.getTileSize();
        final int tileY = (int) position.getY() / this.config.getTileSize();
        return this.tiles.get(tileY + (tileX * this.getWorldHeightTiles()));
    }

    public boolean isPositionInWorld(final Vector position) {
        return (position.getX() >= 0) && (position.getX() < this.config.getWorldWidthPixel()) && //
                (position.getY() >= 0) && (position.getY() < this.config.getWorldHeightPixel());
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
            this.creaturesHistory.add(creature);
            this.creaturePositionTracker.removeElement(creature);
            this.creaturePositionTracker.isValid();
            return;
        }
    }

}
