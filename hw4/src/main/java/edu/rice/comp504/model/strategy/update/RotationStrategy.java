package edu.rice.comp504.model.strategy.update;

import edu.rice.comp504.model.PaintWorldStore;
import edu.rice.comp504.model.paintobject.PaintObject;

import java.awt.*;

public class RotationStrategy implements IUpdateStrategy {
    private static IUpdateStrategy ONLY;

    /**
     * Static method for fetching the Singleton instance.
     */
    public static IUpdateStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new RotationStrategy();
        }
        return ONLY;
    }

    private RotationStrategy() {}

    @Override
    public String getName() {
        return "rotation";
    }

    @Override
    public void updateState(PaintObject object) {
        Point dimensions = PaintWorldStore.getCanvasDimensions();

        int newX = (int) (dimensions.x / 2 + dimensions.y / 4 * Math.cos(object.getAngle()));
        int newY = (int) (dimensions.y / 2 + dimensions.y / 4 * Math.sin(object.getAngle()));

        object.setLocation(new Point(newX, newY));
        object.addToAngle();
    }
}
