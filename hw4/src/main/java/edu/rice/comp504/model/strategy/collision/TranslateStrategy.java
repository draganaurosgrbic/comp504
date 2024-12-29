package edu.rice.comp504.model.strategy.collision;

import edu.rice.comp504.model.PaintWorldStore;
import edu.rice.comp504.model.paintobject.PaintObject;

import java.awt.*;

public class TranslateStrategy implements ICollisionStrategy {
    private static ICollisionStrategy ONLY;

    /**
     * Static method for fetching the Singleton instance.
     */
    public static ICollisionStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new TranslateStrategy();
        }
        return ONLY;
    }

    private TranslateStrategy() {}

    @Override
    public String getName() {
        return "translate";
    }

    @Override
    public void updateBalls(PaintObject ball, PaintObject otherBall) {
        if (otherBall.isRotation()) {
            otherBall.addToAngle(180);
        } else {
            Point dimensions = PaintWorldStore.getCanvasDimensions();

            int newX = dimensions.x - otherBall.getLocation().x;
            int newY = dimensions.y - otherBall.getLocation().y;

            otherBall.setLocation(new Point(newX, newY));
        }
    }
}
