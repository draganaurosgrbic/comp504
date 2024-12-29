package edu.rice.comp504.model.paintobject;

import edu.rice.comp504.model.PaintWorldStore;
import edu.rice.comp504.model.RandUtil;

import java.awt.*;

public class PaintObjectFactory implements IPaintObjectFactory {
    private static final String[] availColors = {"red", "blue", "yellow", "green", "purple", "orange", "gray", "brown"};

    public static String getColor() {
        return availColors[RandUtil.getRnd(0, availColors.length)];
    }

    public static int FISH_SIZE = 30;

    @Override
    public PaintObject make(String type, boolean rotation) {
        Point dimensions = PaintWorldStore.getCanvasDimensions();

        int size = type.equals("ball") ? RandUtil.getRnd(15, 5) : FISH_SIZE;
        int x;
        int y;

        if (rotation) {
            x = (int) (dimensions.x / 2 + dimensions.y / 4 * Math.cos(0));
            y = (int) (dimensions.y / 2 + dimensions.y / 4 * Math.sin(0));
        } else {
            x = RandUtil.getRnd(size, dimensions.x - 2 * size);
            y = RandUtil.getRnd(size, dimensions.y - 2 * size);
        }

        Point location = new Point(x, y);
        Point velocity = new Point(RandUtil.getRnd(15, 5), RandUtil.getRnd(15, 5));
        return new PaintObject(type, location, velocity, size, getColor());
    }
}
