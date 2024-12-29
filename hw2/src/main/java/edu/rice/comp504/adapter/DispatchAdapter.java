package edu.rice.comp504.adapter;

import edu.rice.comp504.model.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class DispatchAdapter {
    private static Point dims;
    private ArrayList<AShape> shapes;

    public DispatchAdapter() {
        shapes = new ArrayList<>();
    }

    public static Point getCanvasDims() {
        return dims;
    }

    public static void setCanvasDims(Point d) {
        dims = d;
    }

    public ArrayList<AShape> getShapes() {
        return shapes;
    }

    /**
     * Method creates new shape and adds it to the list of all shapes.
     */
    public AShape addShape(String name, int matrix, boolean frame, boolean randomColors) {
        int size = 12;

        int x = ThreadLocalRandom.current().nextInt(size, dims.x - size - (matrix - 1) * 2 * size);
        int y = ThreadLocalRandom.current().nextInt(size, dims.y - size - (matrix - 1) * 2 * size);
        Point loc = new Point(x, y);

        AShape shape = randomColors ? new ComplexShape(name, loc, size, matrix, frame)
                : new SimpleShape(name, loc, size, matrix, frame);
        shapes.add(shape);
        return shape;
    }

    /**
     * Method updates the color of all created shapes.
     */
    public ArrayList<AShape> updateShapes() {
        for (AShape shape: shapes) {
            shape.setAttrs();
        }
        return shapes;
    }

    public ArrayList<AShape> removeShapes() {
        shapes.clear();
        return shapes;
    }
}
