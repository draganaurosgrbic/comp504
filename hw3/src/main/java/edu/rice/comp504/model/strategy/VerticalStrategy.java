package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.BallWorldStore;
import edu.rice.comp504.model.ball.Ball;

import java.awt.*;

public class VerticalStrategy implements IUpdateStrategy {
    private static IUpdateStrategy ONLY;

    /**
     * Static method for fetching the Singleton instance.
     */
    public static IUpdateStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new VerticalStrategy();
        }
        return ONLY;
    }

    private VerticalStrategy() {}

    @Override
    public String getName() {
        return "vertical";
    }

    @Override
    public void updateState(Ball context) {
        int newY = context.getLocation().y + context.getVel().y;
        if (newY - context.getRadius() <= 0) {
            newY = context.getRadius();
        }
        if (newY + context.getRadius() >= BallWorldStore.getCanvasDims().y) {
            newY = BallWorldStore.getCanvasDims().y - context.getRadius();
        }

        context.setLocation(new Point(context.getLocation().x, newY));
    }
}
