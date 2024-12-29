package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.ball.Ball;

public class ZigZagStrategy implements IUpdateStrategy {
    private static IUpdateStrategy ONLY;
    private IUpdateStrategy diagonal;

    /**
     * Static method for fetching the Singleton instance.
     */
    public static IUpdateStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new ZigZagStrategy();
        }
        return ONLY;
    }

    private ZigZagStrategy() {
        diagonal = DiagonalStrategy.getOnly();
    }

    @Override
    public String getName() {
        return "zig_zag";
    }

    @Override
    public void updateState(Ball context) {
        diagonal.updateState(context);
        context.incrementDistanceXY();
        if (context.getDistanceXY() >= 10) {
            context.invertVelY();
            context.resetDistanceXY();
        }
    }
}
