package edu.rice.comp504.model.strategy.update;

import edu.rice.comp504.model.paintobject.PaintObject;

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
    public void updateState(PaintObject object) {
        switch (object.getState()) {
            case 0:
                diagonal.updateState(object);
                object.increaseDiagonalDistance();
                if (object.getDiagonalDistance() >= 10) {
                    object.resetDiagonalDistance();
                    object.setState(1);
                    object.invertVelY();
                }
                break;
            default:
                horizontal.updateState(object);
                object.increaseHorizontalDistance();
                if (object.getHorizontalDistance() >= 10) {
                    object.resetHorizontalDistance();
                    object.setState(0);
                }
                break;
        }
    }
}
