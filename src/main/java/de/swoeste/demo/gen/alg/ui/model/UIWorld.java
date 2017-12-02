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
package de.swoeste.demo.gen.alg.ui.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.swoeste.demo.gen.alg.event.SimpleEvent;
import de.swoeste.demo.gen.alg.event.SimpleEventListener;
import de.swoeste.demo.gen.alg.event.SimpleEventType;
import de.swoeste.demo.gen.alg.model.Rectangle;
import de.swoeste.demo.gen.alg.model.Vector;
import de.swoeste.demo.gen.alg.model.creature.Creature;
import de.swoeste.demo.gen.alg.model.world.World;
import de.swoeste.demo.gen.alg.model.world.tile.Tile;
import de.swoeste.demo.gen.alg.ui.util.UICreatureAgeComparator;
import de.swoeste.demo.gen.alg.util.FPSCalculator;
import de.swoeste.demo.gen.alg.util.SortedList;
import javafx.scene.canvas.GraphicsContext;

/**
 * @author swoeste
 */
public class UIWorld implements SimpleEventListener {

    private final World                        world;

    private final List<UITile>                 tiles;

    private final HashMap<Integer, UICreature> creatures;
    private final List<UICreature>             creaturesHistory;

    private final Queue<SimpleEvent>           eventCollector;

    private final FPSCalculator                fpsCalculator;

    private int                                drawCount;

    public UIWorld(final World world) {
        this.world = world;
        this.tiles = new ArrayList<>();
        this.creatures = new HashMap<>();
        this.creaturesHistory = new SortedList<>(new ArrayList<>(), new UICreatureAgeComparator());
        this.eventCollector = new ConcurrentLinkedQueue<>();
        this.fpsCalculator = new FPSCalculator();
        this.drawCount = 0;

        init();
    }

    private void init() {
        // create tiles
        createTiles(this.world);

        // create creatures
        createCreatures(this.world);

        // finally register listeners
        this.world.getEventBus().registerListener(SimpleEventType.CREATURE_CREATED, this);
        this.world.getEventBus().registerListener(SimpleEventType.CREATURE_DIED, this);
    }

    private void createTiles(final World world) {
        this.tiles.clear();
        for (final Tile tile : world.getTiles()) {
            this.tiles.add(new UITile(tile));
        }
    }

    private void createCreatures(final World world) {
        this.creatures.clear();
        for (final Creature creature : world.getCreatures()) {
            this.creatures.put(creature.getId(), new UICreature(creature));
        }
    }

    public int getWidth() {
        return this.world.getWorldWidthPixel();
    }

    public int getHeight() {
        return this.world.getWorldHeightPixel();
    }

    public long getAge() {
        return this.world.getAge();
    }

    public double getFPS() {
        return this.fpsCalculator.getFrameRate();
    }

    public double getAPS() {
        return this.world.getAPS();
    }

    public void draw(final GraphicsContext worldGC, final GraphicsContext overlayGC) {
        this.drawCount++;
        this.fpsCalculator.pass();

        // process all events collected since the last draw
        processEvents();

        final double maxWidth = this.world.getWorldWidthPixel();
        final double maxHeight = this.world.getWorldHeightPixel();

        // draw all tiles to world
        // as a performance improvement we don't draw every 2. frame
        if ((this.drawCount % 2) != 0) {
            this.tiles.stream().forEach(entry -> entry.draw(worldGC));
        }

        // as a performance improvement we don't draw every 4. frame
        if ((this.drawCount % 4) != 0) {
            // clear the overlay
            overlayGC.clearRect(0, 0, maxWidth, maxHeight);

            // draw creatures to the overlay
            this.creatures.values().stream().forEach(entry -> entry.draw(overlayGC));
        }
    }

    // TODO write a unit test for this !
    public UITile getTile(final Vector position) {
        final int tileX = (int) position.getX() / this.world.getTileSize();
        final int tileY = (int) position.getY() / this.world.getTileSize();
        return this.tiles.get(tileY + (tileX * this.world.getWorldHeightTiles()));
    }

    public UICreature getCreature(final Vector position) {
        final Rectangle area = new Rectangle((int) position.getX(), (int) position.getY(), 0, 0);
        final List<Creature> inArea = this.world.getCreaturesInArea(area);

        if (inArea.isEmpty()) {
            return null;
        }

        if (inArea.size() == 1) {
            return this.creatures.get(inArea.get(0).getId());
        }

        // TODO add handling for multiple creatures!
        return null;
    }

    public List<UICreature> getCreatureHistory() {
        return Collections.unmodifiableList(this.creaturesHistory);
    }

    public int countCreatures() {
        return this.creatures.size();
    }

    public int countDeadCreatures() {
        return this.creaturesHistory.size();
    }

    /** {@inheritDoc} */
    @Override
    public void handle(final SimpleEvent event) {
        /*
         * The handle(..) method is called from the BL thread and could be executed in parallel to the drawing method.
         * This could lead to an inconsistent state of the stored data (e.g. event: deleted something from list, draw:
         * draw all elements from that list). Therefore all events are collected async. and only handled in the draw
         * method.
         */
        this.eventCollector.add(event);
    }

    private void processEvents() {
        while (!this.eventCollector.isEmpty()) {
            final SimpleEvent event = this.eventCollector.poll();
            //
            if (SimpleEventType.CREATURE_CREATED.equals(event.getType())) {
                // TODO move to extra method
                final Creature creature = (Creature) event.getData();
                this.creatures.put(creature.getId(), new UICreature(creature));
                continue;
            }
            //
            if (SimpleEventType.CREATURE_DIED.equals(event.getType())) {
                // TODO move to extra method
                final Creature deadCreature = (Creature) event.getData();
                final UICreature deadUICreature = this.creatures.remove(deadCreature.getId());
                this.creaturesHistory.add(deadUICreature);

                // TODO this is not sorted !!!

                continue;
            }
        }
    }

}
