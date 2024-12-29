package edu.rice.comp504.model.strategy.update;

import edu.rice.comp504.model.paintobject.PaintObject;

public class NullUpdateStrategy implements IUpdateStrategy {
    private static IUpdateStrategy ONLY;

    /**
     * Static method for fetching the Singleton instance.
     */
    public static IUpdateStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new NullUpdateStrategy();
        }
        return ONLY;
    }

    private NullUpdateStrategy() {}

    @Override
    public String getName() {
        return "null";
    }

    @Override
    public void updateState(PaintObject object) {}
}
