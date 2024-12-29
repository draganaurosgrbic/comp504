package edu.rice.comp504.model;

import java.awt.*;
import java.util.ArrayList;

public class ComplexShape extends AShape {

    private ArrayList<AShape> children;

    /**
     * Constructor call.
     */
    public ComplexShape(Point loc, int size) {
        super(loc, "Complex");

        Point loc1 = new Point(loc.x - size, loc.y + size);
        Point loc2 = new Point(loc.x + size, loc.y + size);
        Point loc3 = new Point(loc.x - size, loc.y - size);
        Point loc4 = new Point(loc.x + size, loc.y - size);

        Point loc5 = new Point(loc.x - size, loc.y + size - size / 4);
        Point loc6 = new Point(loc.x - size, loc.y - 3 * size / 2 + size / 4);
        Point loc7 = new Point(loc.x - size - size / 4, loc.y - 3 * size / 2 + size / 4);
        Point loc8 = new Point(loc.x + size - size / 4, loc.y - 3 * size / 2 + size / 4);

        children = new ArrayList<>();

        children.add(new Rectangle(loc5, size * 2, size / 2));
        children.add(new Rectangle(loc6, size * 2, size / 2));
        children.add(new Rectangle(loc7, size / 2, size * 2));
        children.add(new Rectangle(loc8, size / 2, size * 2));

        children.add(new Circle(loc1, size / 2));
        children.add(new Circle(loc2, size / 2));
        children.add(new Circle(loc3, size / 2));
        children.add(new Circle(loc4, size / 2));
    }

    @Override
    public void setAttrs() {
        for (AShape child: children) {
            child.setAttrs();
        }
    }
}
