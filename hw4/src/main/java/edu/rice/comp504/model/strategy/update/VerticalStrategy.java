package edu.rice.comp504.model.strategy.update;

import edu.rice.comp504.model.paintobject.PaintObject;

public class VerticalStrategy implements IUpdateStrategy {
    private static IUpdateStrategy ONLY;

    /**
     * Static method for fetching the Singleton instance.
     */
    public static IUpdateStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new VerticalStrategy();
        }
        return ONLY;
    }

    private VerticalStrategy() {}

    @Override
    public String getName() {
        return "vertical";
    }

    @Override
    public void updateState(PaintObject object) {
        object.nextLocation(0, object.getVelocity().y);
    }
}
