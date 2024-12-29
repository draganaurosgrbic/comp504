package edu.rice.comp504.model;

import java.awt.*;
import java.util.ArrayList;

public class ComplexShape extends AShape {
    private ArrayList<AShape> children;

    /**
     * Constructor call.
     */
    public ComplexShape(String name, Point loc, int size, int matrix, boolean frame) {
        super(name, loc);
        children = new ArrayList<>();

        for (int i = 0; i < matrix * matrix; ++i) {
            int column = i % matrix;
            int row = i / matrix;

            if (frame && (column > 0 && column < matrix - 1) && (row > 0 && row < matrix - 1)) {
                continue;
            }

            int x = loc.x + column * 2 * size;
            int y = loc.y + row * 2 * size;

            children.add(new SimpleShape(name, new Point(x, y), size, 1, false));
        }

    }

    @Override
    public void setAttrs() {
        for (AShape child: children) {
            child.setAttrs();
        }
    }
}
