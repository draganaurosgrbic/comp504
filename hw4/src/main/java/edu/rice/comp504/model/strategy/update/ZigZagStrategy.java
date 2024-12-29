package edu.rice.comp504.model.strategy.update;

import edu.rice.comp504.model.paintobject.PaintObject;

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
    public void updateState(PaintObject object) {
        diagonal.updateState(object);
        object.increaseDiagonalDistance();
        if (object.getDiagonalDistance() >= 10) {
            object.invertVelY();
            object.resetDiagonalDistance();
        }
    }
}
