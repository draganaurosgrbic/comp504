package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.MovingLine;

import java.awt.*;

/**
 * Horizontal strategy moves the line to the right only in the x direction.
 */
public class HorizontalStrategy implements IUpdateStrategy {
    private static IUpdateStrategy ONLY;

    /**
     * Constructor.
     */
    private HorizontalStrategy() {

    }

    /**
     * Only makes 1 vertical strategy.
     * @return The vertical strategy
     */
    public static IUpdateStrategy make() {
        if (ONLY == null ) {
            ONLY = new HorizontalStrategy();
        }
        return ONLY;
    }

    /**
     * Get the strategy name.
     * @return strategy name
     */
    public String getName() {
        return "horizontal";
    }

    /**
     * Update the ball state in the ball world.
     * @param context The ball to update
     */
    public void updateState(MovingLine context) {
        Point vel = context.getVelocity();
        context.nextLocation(new Point((int)vel.getX(), 0));
    }
}
