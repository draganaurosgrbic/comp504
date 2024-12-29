package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.ball.Ball;

public class WaterfallStrategy implements IUpdateStrategy {
    private static IUpdateStrategy ONLY;
    private IUpdateStrategy diagonal;
    private IUpdateStrategy vertical;

    /**
     * Static method for fetching the Singleton instance.
     */
    public static IUpdateStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new WaterfallStrategy();
        }
        return ONLY;
    }

    private WaterfallStrategy() {
        diagonal = DiagonalStrategy.getOnly();
        vertical = VerticalStrategy.getOnly();
    }

    @Override
    public String getName() {
        return "waterfall";
    }

    @Override
    public void updateState(Ball context) {
        switch (context.getState()) {
            case 0:
                diagonal.updateState(context);
                context.incrementDistanceXY();
                if (context.getDistanceXY() >= 10) {
                    context.setState(1);
                    context.resetDistanceXY();
                }
                break;
            default:
                vertical.updateState(context);
                context.incrementDistanceY();
                if (context.getDistanceY() >= 10) {
                    context.setState(0);
                    context.resetDistanceY();
                }
                break;
        }
    }
}
