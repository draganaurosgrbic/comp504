package edu.rice.comp504.model;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class Circle extends AShape {

    private int radius;

    /**
     * Constructor call.
     */
    public Circle(Point loc, int radius) {
        super(loc, "Circle");
        this.radius = radius;
    }

    @Override
    public void setAttrs() {
        int random = ThreadLocalRandom.current().nextInt(0, radius * radius) % availColors.length;
        color = availColors[random];
    }

}
