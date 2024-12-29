package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.BallWorldStore;
import edu.rice.comp504.model.ball.Ball;

import java.awt.*;

public class HorizontalStrategy implements IUpdateStrategy {
    private static IUpdateStrategy ONLY;

    /**
     * Static method for fetching the Singleton instance.
     */
    public static IUpdateStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new HorizontalStrategy();
        }
        return ONLY;
    }

    private HorizontalStrategy() {}

    @Override
    public String getName() {
        return "horizontal";
    }

    @Override
    public void updateState(Ball context) {
        int newX = context.getLocation().x + context.getVel().x;
        if (newX - context.getRadius() <= 0) {
            newX = context.getRadius();
        }
        if (newX + context.getRadius() >= BallWorldStore.getCanvasDims().x) {
            newX = BallWorldStore.getCanvasDims().x - context.getRadius();
        }

        context.setLocation(new Point(newX, context.getLocation().y));
    }
}
