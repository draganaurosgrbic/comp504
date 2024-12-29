package edu.rice.comp504.model;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class Rectangle extends AShape {

    private int sizeX;
    private int sizeY;

    /**
     * Constructor call.
     */
    public Rectangle(Point loc, int sizeX, int sizeY) {
        super(loc, "Rectangle");
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    @Override
    public void setAttrs() {
        int random = ThreadLocalRandom.current().nextInt(0, sizeX * sizeY) % availColors.length;
        color = availColors[random];
    }

}
