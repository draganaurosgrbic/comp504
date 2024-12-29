package edu.rice.comp504.model.strategy.update;

import edu.rice.comp504.model.paintobject.PaintObject;

public class HorizontalStrategy implements IUpdateStrategy {
    private static IUpdateStrategy ONLY;

    /**
     * Static method for fetching the Singleton instance.
     */
    public static IUpdateStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new HorizontalStrategy();
        }
        return ONLY;
    }

    private HorizontalStrategy() {}

    @Override
    public String getName() {
        return "horizontal";
    }

    @Override
    public void updateState(PaintObject object) {
        object.nextLocation(object.getVelocity().x, 0);
    }
}
