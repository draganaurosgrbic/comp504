package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.BallWorldStore;
import edu.rice.comp504.model.ball.Ball;

import java.awt.*;

public class RotatingStrategy implements IUpdateStrategy {
    private static IUpdateStrategy ONLY;

    /**
     * Static method for fetching the Singleton instance.
     */
    public static IUpdateStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new RotatingStrategy();
        }
        return ONLY;
    }

    private RotatingStrategy() {}

    @Override
    public String getName() {
        return "rotating";
    }

    @Override
    public void updateState(Ball context) {
        int newX = (int) (BallWorldStore.getCanvasDims().x / 2 + BallWorldStore.getCanvasDims().y / 4 * Math.cos(context.getAngle()));
        int newY = (int) (BallWorldStore.getCanvasDims().y / 2 + BallWorldStore.getCanvasDims().y / 4 * Math.sin(context.getAngle()));
        context.setLocation(new Point(newX, newY));
        context.incrementAngle();
    }
}
