package edu.rice.comp504.model.strategy.collision;

import edu.rice.comp504.model.paintobject.PaintObject;
import edu.rice.comp504.model.strategy.update.NullUpdateStrategy;

public class StuckStrategy implements ICollisionStrategy {
    private static ICollisionStrategy ONLY;

    /**
     * Static method for fetching the Singleton instance.
     */
    public static ICollisionStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new StuckStrategy();
        }
        return ONLY;
    }

    private StuckStrategy() {}

    @Override
    public String getName() {
        return "stuck";
    }

    @Override
    public void updateBalls(PaintObject ball, PaintObject otherBall) {
        otherBall.setUpdateStrategy(NullUpdateStrategy.getOnly());
    }
}
