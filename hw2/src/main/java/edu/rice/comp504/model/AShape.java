package edu.rice.comp504.model;

import java.awt.*;

public abstract class AShape {
    protected String name;
    protected Point loc;
    protected String color;
    protected transient String[] availColors = {"red", "blue", "yellow", "green", "black", "purple", "orange", "gray", "brown"};

    /**
     * Constructor call.
     */
    public AShape(String name, Point loc) {
        this.name = name;
        this.loc = loc;
    }

    /**
     * Method updates the color of shape.
     */
    public abstract void setAttrs();

}

