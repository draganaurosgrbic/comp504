package edu.rice.comp504.model.strategy.update;

import edu.rice.comp504.model.paintobject.PaintObject;

public class SquareStrategy implements IUpdateStrategy {

    private static IUpdateStrategy ONLY;

    private IUpdateStrategy horizontal;
    private IUpdateStrategy vertical;

    /**
     * Static method for fetching the Singleton instance.
     */
    public static IUpdateStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new SquareStrategy();
        }
        return ONLY;
    }

    private SquareStrategy() {
        horizontal = HorizontalStrategy.getOnly();
        vertical = VerticalStrategy.getOnly();
    }

    @Override
    public String getName() {
        return "square";
    }

    @Override
    public void updateState(PaintObject object) {
        switch (object.getState()) {
            case 0:
                horizontal.updateState(object);
                object.increaseHorizontalDistance();
                if (object.getHorizontalDistance() >= 10) {
                    object.setState(1);
                    object.resetHorizontalDistance();
                    object.invertVelY();
                }
                break;
            default:
                vertical.updateState(object);
                object.increaseVerticalDistance();
                if (object.getVerticalDistance() >= 10) {
                    object.setState(0);
                    object.resetVerticalDistance();
                    object.invertVelX();
                }
                break;
        }
    }
}
