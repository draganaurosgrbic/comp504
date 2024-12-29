package edu.rice.comp504.model.strategy.collision;

import edu.rice.comp504.model.paintobject.PaintObject;

public class NullCollisionStrategy implements ICollisionStrategy {
    private static ICollisionStrategy ONLY;

    /**
     * Static method for fetching the Singleton instance.
     */
    public static ICollisionStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new NullCollisionStrategy();
        }
        return ONLY;
    }

    private NullCollisionStrategy() {}

    @Override
    public String getName() {
        return "null";
    }

    @Override
    public void updateBalls(PaintObject ball, PaintObject otherBall) {}
}
