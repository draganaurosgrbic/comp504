package edu.rice.comp504.model.strategy.collision;

import edu.rice.comp504.model.paintobject.PaintObject;

public class EatStrategy implements ICollisionStrategy {
    private static ICollisionStrategy ONLY;

    /**
     * Static method for fetching the Singleton instance.
     */
    public static ICollisionStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new EatStrategy();
        }
        return ONLY;
    }

    private EatStrategy() {}

    @Override
    public String getName() {
        return "eat";
    }

    @Override
    public void updateBalls(PaintObject ball, PaintObject otherBall) {
        ball.eat(otherBall);
        otherBall.remove();
    }
}
