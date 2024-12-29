package edu.rice.comp504.model.ball;

import edu.rice.comp504.model.BallWorldStore;
import edu.rice.comp504.util.RandUtil;

import java.awt.*;

public class BallFac implements IBallFac {
    private final String[] availColors = {"red", "blue", "yellow", "green", "black", "purple", "orange", "gray", "brown"};

    @Override
    public Ball make(String type) {
        int radius = RandUtil.getRnd(3, 5);
        int x;
        int y;

        if (type.equals("rotating")) {
            x = (int) (BallWorldStore.getCanvasDims().x / 2 + BallWorldStore.getCanvasDims().y / 4 * Math.cos(0));
            y = (int) (BallWorldStore.getCanvasDims().y / 2 + BallWorldStore.getCanvasDims().y / 4 * Math.sin(0));
        } else if (type.equals("pulse") || type.equals("zig_zag") || type.equals("inverted_squares") || type.equals("inverted_trapezoids")) {
            x = RandUtil.getRnd(radius, BallWorldStore.getCanvasDims().x - 2 * radius);
            y = RandUtil.getRnd(radius, BallWorldStore.getCanvasDims().y / 2);
        } else if (type.equals("square")) {
            x = RandUtil.getRnd(radius, BallWorldStore.getCanvasDims().x / 2);
            y = 3 * BallWorldStore.getCanvasDims().y / 4;
        } else if (type.equals("rhombus")) {
            x = RandUtil.getRnd(radius, BallWorldStore.getCanvasDims().x / 2);
            y = BallWorldStore.getCanvasDims().y / 2;
        } else {
            x = RandUtil.getRnd(radius, BallWorldStore.getCanvasDims().x - 2 * radius);
            y = RandUtil.getRnd(radius, BallWorldStore.getCanvasDims().y - 2 * radius);
        }

        Point loc = new Point(x, y);
        Point vel = new Point(RandUtil.getRnd(3, 5), RandUtil.getRnd(3, 5));
        String color = availColors[RandUtil.getRnd(0, availColors.length)];

        return new Ball(loc, radius, vel, color);
    }
}
