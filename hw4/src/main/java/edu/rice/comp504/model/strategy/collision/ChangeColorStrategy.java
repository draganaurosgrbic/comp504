package edu.rice.comp504.model.strategy.collision;

import edu.rice.comp504.model.paintobject.PaintObject;

public class ChangeColorStrategy implements ICollisionStrategy {
    private static ICollisionStrategy ONLY;

    /**
     * Static method for fetching the Singleton instance.
     */
    public static ICollisionStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new ChangeColorStrategy();
        }
        return ONLY;
    }

    private ChangeColorStrategy() {}

    @Override
    public String getName() {
        return "change_color";
    }

    @Override
    public void updateBalls(PaintObject ball, PaintObject otherBall) {
        otherBall.changeColor();
    }
}
