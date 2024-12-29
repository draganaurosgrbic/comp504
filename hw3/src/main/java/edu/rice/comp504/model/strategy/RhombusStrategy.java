package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.ball.Ball;

public class RhombusStrategy implements IUpdateStrategy {

    private static IUpdateStrategy ONLY;

    private IUpdateStrategy diagonal;

    /**
     * Static method for fetching the Singleton instance.
     */
    public static IUpdateStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new RhombusStrategy();
        }
        return ONLY;
    }

    private RhombusStrategy() {
        diagonal = DiagonalStrategy.getOnly();
    }

    @Override
    public String getName() {
        return "rhombus";
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
                    context.invertVelY();
                }
                break;
            case 1:
                diagonal.updateState(context);
                context.incrementDistanceXY();
                if (context.getDistanceXY() >= 10) {
                    context.setState(2);
                    context.resetDistanceXY();
                    context.invertVelX();
                }
                break;
            case 2:
                diagonal.updateState(context);
                context.incrementDistanceXY();
                if (context.getDistanceXY() >= 10) {
                    context.setState(3);
                    context.resetDistanceXY();
                    context.invertVelY();
                }
                break;
            default:
                diagonal.updateState(context);
                context.incrementDistanceXY();
                if (context.getDistanceXY() >= 10) {
                    context.setState(0);
                    context.resetDistanceXY();
                    context.invertVelX();
                }
                break;
        }
    }
}
