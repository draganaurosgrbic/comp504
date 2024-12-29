package edu.rice.comp504.model.strategy.collision;

import edu.rice.comp504.model.paintobject.PaintObject;

public class FishConvertStrategy implements ICollisionStrategy {
    private static ICollisionStrategy ONLY;

    /**
     * Static method for fetching the Singleton instance.
     */
    public static ICollisionStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new FishConvertStrategy();
        }
        return ONLY;
    }

    private FishConvertStrategy() {}

    @Override
    public String getName() {
        return "fish_convert";
    }

    @Override
    public void updateBalls(PaintObject ball, PaintObject otherBall) {
        otherBall.convertToFish();
    }
}
