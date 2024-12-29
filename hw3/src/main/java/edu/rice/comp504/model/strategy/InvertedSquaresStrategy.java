package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.ball.Ball;

public class InvertedSquaresStrategy implements IUpdateStrategy {
    private static IUpdateStrategy ONLY;
    private IUpdateStrategy horizontal;
    private IUpdateStrategy vertical;

    /**
     * Static method for fetching the Singleton instance.
     */
    public static IUpdateStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new InvertedSquaresStrategy();
        }
        return ONLY;
    }

    private InvertedSquaresStrategy() {
        horizontal = HorizontalStrategy.getOnly();
        vertical = VerticalStrategy.getOnly();
    }

    @Override
    public String getName() {
        return "inverted_squares";
    }

    @Override
    public void updateState(Ball context) {
        switch (context.getState()) {
            case 0:
                horizontal.updateState(context);
                context.incrementDistanceX();
                if (context.getDistanceX() >= 10) {
                    context.setState(1);
                    context.resetDistanceX();
                }
                break;
            default:
                vertical.updateState(context);
                context.incrementDistanceY();
                if (context.getDistanceY() >= 10) {
                    context.setState(0);
                    context.resetDistanceY();
                    context.invertVelY();
                }
                break;
        }
    }
}
