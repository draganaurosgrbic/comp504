package edu.rice.comp504.model.strategy.collision;

import edu.rice.comp504.model.paintobject.PaintObject;

public class DestroyStrategy implements ICollisionStrategy {
    private static ICollisionStrategy ONLY;

    /**
     * Static method for fetching the Singleton instance.
     */
    public static ICollisionStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new DestroyStrategy();
        }
        return ONLY;
    }

    private DestroyStrategy() {}

    @Override
    public String getName() {
        return "destroy";
    }

    @Override
    public void updateBalls(PaintObject ball, PaintObject otherBall) {
        otherBall.remove();
    }
}
