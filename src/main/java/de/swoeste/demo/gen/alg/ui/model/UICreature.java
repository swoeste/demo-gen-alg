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
import java.util.List;

import de.swoeste.demo.gen.alg.model.creature.Creature;
import de.swoeste.demo.gen.alg.model.creature.CreatureAttribute;
import de.swoeste.demo.gen.alg.model.polygon.Vector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

/**
 * @author swoeste
 */
public class UICreature implements Selectable {

    private static final Color COLOR_SHAPE          = Color.BLACK;
    private static final Color COLOR_SHAPE_SELECTED = Color.RED;
    private static final Color COLOR_VISION         = Color.rgb(0, 0, 0, 0.25);

    private final Creature     creature;
    private boolean            selected;

    public UICreature(final Creature creature) {
        this.creature = creature;
        this.selected = false;
    }

    public void draw(final GraphicsContext gc) {
        final int bodySize = this.creature.getAttributeValue(CreatureAttribute.SIZE);
        final double x = this.creature.getAttributeValue(CreatureAttribute.POSITION_X);
        final double y = this.creature.getAttributeValue(CreatureAttribute.POSITION_Y);
        final int viewDirectionDegrees = this.creature.getAttributeValue(CreatureAttribute.VIEW_DIRECTION);
        final int visionArcSizeDegrees = this.creature.getAttributeValue(CreatureAttribute.VIEW_ARC);

        // body
        final int startAngle = viewDirectionDegrees - (visionArcSizeDegrees / 2);
        gc.setFill(getColor());
        gc.fillArc(x, y, bodySize, bodySize, startAngle, 360 - visionArcSizeDegrees, ArcType.ROUND);

        // body shape
        if (this.selected) {
            gc.setStroke(COLOR_SHAPE_SELECTED);
            gc.strokeArc(x, y, bodySize, bodySize, startAngle, 360 - visionArcSizeDegrees, ArcType.ROUND);
        } else {
            gc.setStroke(COLOR_SHAPE);
            gc.strokeArc(x, y, bodySize, bodySize, startAngle, 360 - visionArcSizeDegrees, ArcType.ROUND);
        }

        // vision indicator
        final double visionDistance = this.creature.getAttributeValue(CreatureAttribute.VIEW_DISTANCE);
        final double visionCenterX = (x - (visionDistance)) + (bodySize / 2.0);
        final double visionCenterY = (y - (visionDistance)) + (bodySize / 2.0);
        gc.setFill(COLOR_VISION);
        gc.fillArc(visionCenterX, visionCenterY, visionDistance * 2, visionDistance * 2, startAngle, visionArcSizeDegrees * -1.0, ArcType.ROUND);
    }

    public Color getColor() {
        final int r = this.creature.getAttributeValue(CreatureAttribute.COLOR_R);
        final int g = this.creature.getAttributeValue(CreatureAttribute.COLOR_G);
        final int b = this.creature.getAttributeValue(CreatureAttribute.COLOR_B);
        return RGBColorUtil.getColor(r, g, b);
    }

    public Vector getPosition() {
        final int x = this.creature.getAttributeValue(CreatureAttribute.POSITION_X);
        final int y = this.creature.getAttributeValue(CreatureAttribute.POSITION_Y);
        return new Vector(x, y);
    }

    public int getId() {
        return this.creature.getId();
    }

    public int getAge() {
        return this.creature.getAttributeValue(CreatureAttribute.AGE);
    }

    public String getName() {
        return this.creature.getLastname() + ", " + this.creature.getName(); //$NON-NLS-1$
    }

    public UINetwork getNetwork() {
        return new UINetwork(this.creature.getNetwork());
    }

    public List<UIProperty> getProperties() {
        final List<UIProperty> result = new ArrayList<>();

        for (CreatureAttribute creatureAttribute : CreatureAttribute.values()) {
            final String attributeValue = String.valueOf(this.creature.getAttributeValue(creatureAttribute));
            result.add(new UIProperty(creatureAttribute.name(), attributeValue));
        }

        return result;
    }

    /** {@inheritDoc} */
    @Override
    public void setSelected(final boolean selected) {
        this.selected = selected;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.creature == null) ? 0 : this.creature.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        UICreature other = (UICreature) obj;
        if (this.creature == null) {
            if (other.creature != null) {
                return false;
            }
        } else if (!this.creature.equals(other.creature)) {
            return false;
        }
        return true;
    }

}
