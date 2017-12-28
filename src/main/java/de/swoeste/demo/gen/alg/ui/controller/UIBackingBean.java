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
package de.swoeste.demo.gen.alg.ui.controller;

import java.text.DecimalFormat;

import de.swoeste.demo.gen.alg.Configuration;
import de.swoeste.demo.gen.alg.Simulation;
import de.swoeste.demo.gen.alg.model.polygon.Vector;
import de.swoeste.demo.gen.alg.ui.controller.model.UIWorldModel;
import de.swoeste.demo.gen.alg.ui.controller.section.model.UICreatureHistoryModel;
import de.swoeste.demo.gen.alg.ui.controller.section.model.UIRuntimeInformationModel;
import de.swoeste.demo.gen.alg.ui.controller.section.model.UISelectionInformationModel;
import de.swoeste.demo.gen.alg.ui.controller.section.model.UISimulationConfigurationModel;
import de.swoeste.demo.gen.alg.ui.controller.section.model.UISimulationControlModel;
import de.swoeste.demo.gen.alg.ui.model.Selectable;
import de.swoeste.demo.gen.alg.ui.model.UICreature;
import de.swoeste.demo.gen.alg.ui.model.UITile;
import de.swoeste.demo.gen.alg.ui.model.UIWorld;
import de.swoeste.demo.gen.alg.ui.simulation.SimulationAnimationTimer;
import javafx.scene.canvas.GraphicsContext;

/**
 * @author swoeste
 */
public class UIBackingBean {

    private static final UIBackingBean           INSTANCE       = new UIBackingBean();
    private static final DecimalFormat           DECIMAL_FORMAT = new DecimalFormat("#.##"); //$NON-NLS-1$
    private static final DecimalFormat           INTEGER_FORMAT = new DecimalFormat("#");                                                                                                    //$NON-NLS-1$

    private final UIWorldModel                   control;
    private final UICreatureHistoryModel         creatureHistory;
    private final UIRuntimeInformationModel      runtimeInformation;
    private final UISelectionInformationModel    selectionInformation;
    private final UISimulationConfigurationModel simulationConfiguration;
    private final UISimulationControlModel       simulationControl;

    private UIWorld                              world;
    private Simulation                           simulation;
    private SimulationAnimationTimer             simulationAnimationTimer;

    private Selectable                           selection;

    private GraphicsContext                      worldGC;
    private GraphicsContext                      overlayGC;

    public UIBackingBean() {
        this.control = new UIWorldModel();
        this.creatureHistory = new UICreatureHistoryModel();
        this.runtimeInformation = new UIRuntimeInformationModel();
        this.selectionInformation = new UISelectionInformationModel();
        this.simulationConfiguration = new UISimulationConfigurationModel();
        this.simulationControl = new UISimulationControlModel();
    }

    public UICreatureHistoryModel getCreatureHistory() {
        return this.creatureHistory;
    }

    public UIRuntimeInformationModel getRuntimeInformation() {
        return this.runtimeInformation;
    }

    public UISelectionInformationModel getSelectionInformation() {
        return this.selectionInformation;
    }

    public UISimulationConfigurationModel getSimulationConfiguration() {
        return this.simulationConfiguration;
    }

    public UISimulationControlModel getSimulationControl() {
        return this.simulationControl;
    }

    public UIWorldModel getControl() {
        return this.control;
    }

    public void update() {
        // update simulation speed
        this.simulation.setSpeed(this.simulationControl.getSpeedValue());

        // update selection information
        updateSelectionInformation();

        // update creature history
        // TODO would be faster if we only update, if a creature dies!
        this.creatureHistory.getCreatures().setAll(this.world.getCreatureHistory());

        // draw
        this.world.draw(this.worldGC, this.overlayGC);

        // update runtime information
        this.runtimeInformation.setAge(DECIMAL_FORMAT.format(this.world.getAge()));
        this.runtimeInformation.setAPS(DECIMAL_FORMAT.format(this.world.getAPS()));
        this.runtimeInformation.setFPS(DECIMAL_FORMAT.format(this.world.getFPS()));
        this.runtimeInformation.setCreatures(INTEGER_FORMAT.format(this.world.countCreatures()));
        this.runtimeInformation.setDeadCreatures(INTEGER_FORMAT.format(this.world.countDeadCreatures()));
    }

    public void startSimulation() {
        if (this.simulation == null) {
            throw new IllegalStateException("The simulation is not initialized right now!"); //$NON-NLS-1$
        }
        this.simulation.start();
    }

    public void stopSimulation() {
        if (this.simulation == null) {
            throw new IllegalStateException("The simulation is not initialized right now!"); //$NON-NLS-1$
        }
        this.simulation.pause();
    }

    public void newSimulation() {
        shutdownSimulation();

        final Configuration configuration = new Configuration();
        configuration.setActivationFunction(this.simulationConfiguration.getActivationFunctionValue());
        configuration.setWorldSeed(this.simulationConfiguration.getWorldSeedValue());
        configuration.setWorldWidthTiles(this.simulationConfiguration.getWorldWithValue());
        configuration.setWorldHeightTiles(this.simulationConfiguration.getWorldHeightValue());
        configuration.setTileSize(this.simulationConfiguration.getTileSizeValue());
        configuration.setCreatureSeed(this.simulationConfiguration.getCreatureSeedValue());
        configuration.setCreatureAmount(this.simulationConfiguration.getCreatureAmountValue());

        this.simulation = new Simulation(configuration);
        this.world = new UIWorld(this.simulation.getWorld());
        this.control.setWorldHeight(this.world.getHeight());
        this.control.setWorldWidth(this.world.getWidth());

        this.simulationAnimationTimer = new SimulationAnimationTimer(this);
        this.simulationAnimationTimer.start();
    }

    private void shutdownSimulation() {
        if (this.simulation != null) {
            this.simulation.shutdown();
        }
        if (this.simulationAnimationTimer != null) {
            this.simulationAnimationTimer.stop();
        }
    }

    public void setWorldGC(final GraphicsContext worldGC) {
        this.worldGC = worldGC;
    }

    public void setOverlayGC(final GraphicsContext overlayGC) {
        this.overlayGC = overlayGC;
    }

    public static UIBackingBean getInstance() {
        return INSTANCE;
    }

    public void setSelection(final Vector position) {
        if (this.selection != null) {
            this.selection.setSelected(false);
        }

        final UICreature creature = this.world.getCreature(position);
        if (creature != null) {
            this.selection = creature;
            this.selection.setSelected(true);
            this.selectionInformation.setSelection(String.valueOf(creature));
            this.selectionInformation.setPositionX(String.valueOf(creature.getPosition().getX()));
            this.selectionInformation.setPositionY(String.valueOf(creature.getPosition().getY()));
            this.selectionInformation.getProperties().setAll(creature.getProperties());
            return;
        }
        final UITile tile = this.world.getTile(position);
        if (tile != null) {
            this.selection = tile;
            this.selection.setSelected(true);
            this.selectionInformation.setSelection(String.valueOf(tile));
            this.selectionInformation.setPositionX(String.valueOf(tile.getPosition().getX()));
            this.selectionInformation.setPositionY(String.valueOf(tile.getPosition().getY()));
            return;
        }
    }

    private void updateSelectionInformation() {
        if (this.selection == null) {
            return;
        }

        if (this.selection instanceof UICreature) {
            final UICreature creature = (UICreature) this.selection;
            this.selectionInformation.setSelection(creature.getName());
            this.selectionInformation.setPositionX(String.valueOf(creature.getPosition().getX()));
            this.selectionInformation.setPositionY(String.valueOf(creature.getPosition().getY()));
            this.selectionInformation.getProperties().setAll(creature.getProperties());
            return;
        }

        if (this.selection instanceof UITile) {
            final UITile tile = (UITile) this.selection;
            this.selectionInformation.setSelection(tile.getName());
            this.selectionInformation.setPositionX(String.valueOf(tile.getPosition().getX()));
            this.selectionInformation.setPositionY(String.valueOf(tile.getPosition().getY()));
            this.selectionInformation.getProperties().setAll(tile.getProperties());
            return;
        }
    }

}
