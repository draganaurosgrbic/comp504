package edu.rice.comp504.model.strategy.update;

import edu.rice.comp504.model.paintobject.PaintObject;

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
            case 1:
                diagonal.updateState(object);
                object.increaseDiagonalDistance();
                if (object.getDiagonalDistance() >= 10) {
                    object.setState(2);
                    object.resetDiagonalDistance();
                    object.invertVelX();
                }
                break;
            case 2:
                diagonal.updateState(object);
                object.increaseDiagonalDistance();
                if (object.getDiagonalDistance() >= 10) {
                    object.setState(3);
                    object.resetDiagonalDistance();
                    object.invertVelY();
                }
                break;
            default:
                diagonal.updateState(object);
                object.increaseDiagonalDistance();
                if (object.getDiagonalDistance() >= 10) {
                    object.setState(0);
                    object.resetDiagonalDistance();
                    object.invertVelX();
                }
                break;
        }
    }
}
