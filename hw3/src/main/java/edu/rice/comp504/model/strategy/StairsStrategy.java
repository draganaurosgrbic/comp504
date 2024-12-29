package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.ball.Ball;

public class StairsStrategy implements IUpdateStrategy {
    private static IUpdateStrategy ONLY;
    private IUpdateStrategy horizontal;
    private IUpdateStrategy vertical;

    /**
     * Static method for fetching the Singleton instance.
     */
    public static IUpdateStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new StairsStrategy();
        }
        return ONLY;
    }

    private StairsStrategy() {
        horizontal = HorizontalStrategy.getOnly();
        vertical = VerticalStrategy.getOnly();
    }

    @Override
    public String getName() {
        return "stairs";
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
                }
                break;
        }
    }
}
