package edu.rice.comp504.model;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class Square extends AShape {

    private int size;

    /**
     * Constructor call.
     */
    public Square(Point loc, int size) {
        super(loc, "Square");
        this.loc = loc;
        this.size = size;
    }

    @Override
    public void setAttrs() {
        int random = ThreadLocalRandom.current().nextInt(0, size * size) % availColors.length;
        color = availColors[random];
    }

}
