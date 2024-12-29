package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.ball.Ball;

public class InvertedTrapezoidsStrategy implements IUpdateStrategy {
    private static IUpdateStrategy ONLY;
    private IUpdateStrategy diagonal;
    private IUpdateStrategy horizontal;

    /**
     * Static method for fetching the Singleton instance.
     */
    public static IUpdateStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new InvertedTrapezoidsStrategy();
        }
        return ONLY;
    }

    private InvertedTrapezoidsStrategy() {
        diagonal = DiagonalStrategy.getOnly();
        horizontal = HorizontalStrategy.getOnly();
    }

    @Override
    public String getName() {
        return "inverted_trapezoids";
    }

    @Override
    public void updateState(Ball context) {
        switch (context.getState()) {
            case 0:
                diagonal.updateState(context);
                context.incrementDistanceXY();
                if (context.getDistanceXY() >= 10) {
                    context.resetDistanceXY();
                    context.setState(1);
                    context.invertVelY();
                }
                break;
            default:
                horizontal.updateState(context);
                context.incrementDistanceX();
                if (context.getDistanceX() >= 10) {
                    context.resetDistanceX();
                    context.setState(0);
                }
                break;
        }
    }
}
