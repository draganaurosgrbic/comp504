package edu.rice.comp504.model.strategy.update;

import edu.rice.comp504.model.paintobject.PaintObject;

public class DiagonalStrategy implements IUpdateStrategy {
    private static IUpdateStrategy ONLY;

    /**
     * Static method for fetching the Singleton instance.
     */
    public static IUpdateStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new DiagonalStrategy();
        }
        return ONLY;
    }

    private DiagonalStrategy() {}

    @Override
    public String getName() {
        return "diagonal";
    }

    @Override
    public void updateState(PaintObject object) {
        object.nextLocation(object.getVelocity().x, object.getVelocity().y);
    }
}
