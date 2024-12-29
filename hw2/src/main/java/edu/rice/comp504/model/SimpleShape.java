package edu.rice.comp504.model;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class SimpleShape extends AShape {
    private int size;
    private int matrix;
    private boolean frame;

    /**
     * Constructor call.
     */
    public SimpleShape(String name, Point loc, int size, int matrix, boolean frame) {
        super(name, loc);
        this.size = size;
        this.matrix = matrix;
        this.frame = frame;
    }

    @Override
    public void setAttrs() {
        int random = ThreadLocalRandom.current().nextInt(0, size * matrix + (frame ? 1 : 0)) % availColors.length;
        color = availColors[random];
    }
}
