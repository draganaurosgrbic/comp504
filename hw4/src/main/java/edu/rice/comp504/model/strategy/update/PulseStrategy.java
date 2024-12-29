package edu.rice.comp504.model.strategy.update;

import edu.rice.comp504.model.paintobject.PaintObject;

public class PulseStrategy implements IUpdateStrategy {
    private static IUpdateStrategy ONLY;
    private IUpdateStrategy diagonal;
    private IUpdateStrategy vertical;

    /**
     * Static method for fetching the Singleton instance.
     */
    public static IUpdateStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new PulseStrategy();
        }
        return ONLY;
    }

    private PulseStrategy() {
        diagonal = DiagonalStrategy.getOnly();
        vertical = VerticalStrategy.getOnly();
    }

    @Override
    public String getName() {
        return "pulse";
    }

    @Override
    public void updateState(PaintObject object) {
        switch (object.getState()) {
            case 0:
                diagonal.updateState(object);
                object.increaseDiagonalDistance();
                if (object.getDiagonalDistance() >= 10) {
                    object.setState(1);
                    object.resetDiagonalDistance();
                    object.invertVelY();
                }
                break;
            default:
                vertical.updateState(object);
                object.increaseVerticalDistance();
                if (object.getVerticalDistance() >= 10) {
                    object.setState(0);
                    object.resetVerticalDistance();
                    object.invertVelY();
                }
                break;
        }
    }
}
