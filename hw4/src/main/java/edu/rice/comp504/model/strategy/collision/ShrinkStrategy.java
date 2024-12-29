package edu.rice.comp504.model.strategy.collision;

import edu.rice.comp504.model.paintobject.PaintObject;

public class ShrinkStrategy implements ICollisionStrategy {
    private static ICollisionStrategy ONLY;

    /**
     * Static method for fetching the Singleton instance.
     */
    public static ICollisionStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new ShrinkStrategy();
        }
        return ONLY;
    }

    private ShrinkStrategy() {}

    @Override
    public String getName() {
        return "shrink";
    }

    @Override
    public void updateBalls(PaintObject ball, PaintObject otherBall) {
        otherBall.decreaseSize();
    }
}
