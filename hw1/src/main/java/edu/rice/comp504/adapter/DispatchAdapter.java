package edu.rice.comp504.adapter;

import edu.rice.comp504.model.Rectangle;
import edu.rice.comp504.model.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The dispatch adapter holds all the shapes. It is responsible for communicating with the model
 * when there is a state change requiring an update to all the shapes.  The controller will
 * pass the JSON representation of the dispatch adapter to the view.
 */
public class DispatchAdapter {
    private static Point dims;
    private ArrayList<AShape> shapes;

    /**
     * Constructor call.
     */
    public DispatchAdapter() {
        shapes = new ArrayList<>();
    }

    /**
     * Get the canvas dimensions.
     * @return The canvas dimensions
     */
    public static Point getCanvasDims() {
        return dims;
    }

    /**
     * Set the canvas dimensions.
     * @param d The canvas width (x) and height (y).
     */
    public static void setCanvasDims(Point d) {
        dims = d;
    }

    /**
     * Get the shapes.
     * @return The shapes
     */
    public ArrayList<AShape> getShapes() {
        return shapes;
    }

    /**
     * Call the update method on all the shapes.
     */
    public ArrayList<AShape> updateShapes() {
        for (AShape shape: shapes) {
            shape.setAttrs();
        }
        return shapes;
    }

    /**
     *  Add a shape.
     * @param type  The type of shape to add
     * @return A ball
     */
    public AShape addShape(String type) {
        AShape shape;
        Point loc = new Point();
        int size = 24;

        if (!type.equals("rectangle") && !type.equals("complex")) {
            loc.x = ThreadLocalRandom.current().nextInt(size, dims.x - size);
            loc.y = ThreadLocalRandom.current().nextInt(size, dims.y - size);
        } else {
            loc.x = ThreadLocalRandom.current().nextInt(2 * size, dims.x - 2 * size);
            loc.y = ThreadLocalRandom.current().nextInt(2 * size, dims.y - 2 * size);
        }

        switch (type) {
            case "circle":
                shape = new Circle(loc, size);
                break;
            case "triangle":
                shape = new Triangle(loc, size);
                break;
            case "square":
                shape = new Square(loc, size);
                break;
            case "rectangle":
                boolean doubledHeight = Math.random() > 0.5;
                shape = new Rectangle(loc, doubledHeight ? size : size * 2, doubledHeight ? size * 2 : size);
                break;
            case "complex":
                shape = new ComplexShape(loc, size);
                break;
            default:
                shape = null;
        };

        if (shape != null) {
            shapes.add(shape);
        }

        return shape;
    }

    /**
     * Remove all shapes.
     */
    public ArrayList<AShape> removeShapes() {
        shapes.clear();
        return shapes;
    }
}
