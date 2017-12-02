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
package de.swoeste.demo.gen.alg.model;

import java.text.MessageFormat;

/**
 * @author swoeste
 */
public class Rectangle {

    private final int posX;
    private final int posY;
    private final int sizeX;
    private final int sizeY;

    public Rectangle(final Rectangle rectangle) {
        this(rectangle.posX, rectangle.posY, rectangle.sizeX, rectangle.sizeY);
    }

    public Rectangle(final int posX, final int posY, final int sizeX, final int sizeY) {
        this.posX = posX;
        this.posY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public int getSizeX() {
        return this.sizeX;
    }

    public int getSizeY() {
        return this.sizeY;
    }

    // TODO write a test !!!
    public boolean intersect(final Rectangle other) {
        return ((this.posX <= (other.posX + other.sizeX)) && //
                (other.posX <= (this.posX + this.sizeX)) && //
                (this.posY <= (other.posY + other.sizeY)) && //
                (other.posY <= (this.posY + this.sizeY)));
    }

    @Override
    public String toString() {
        return MessageFormat.format("Rectangle [posX={0}, posY={1}, sizeX={2}, sizeY={3}]", this.posX, this.posY, this.sizeX, this.sizeY); //$NON-NLS-1$
    }

}
