package edu.rice.comp504.model.strategy.collision;

import edu.rice.comp504.model.paintobject.PaintObject;

public class ChangeDirectionStrategy implements ICollisionStrategy {
    private static ICollisionStrategy ONLY;

    /**
     * Static method for fetching the Singleton instance.
     */
    public static ICollisionStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new ChangeDirectionStrategy();
        }
        return ONLY;
    }

    private ChangeDirectionStrategy() {}

    @Override
    public String getName() {
        return "change_direction";
    }

    @Override
    public void updateBalls(PaintObject ball, PaintObject otherBall) {
        if (otherBall.isRotation()) {
            if (!(otherBall.getCollisionStrategy() instanceof ChangeDirectionStrategy)) {
                ball.invertVelX();;
                ball.invertVelY();
            }
        } else {
            otherBall.invertVelX();
            otherBall.invertVelY();
        }
    }
}
