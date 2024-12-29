package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.MovingLine;

import java.awt.*;


/**
 * Vertical strategy moves the line to the right only in the y direction.
 */
public class VerticalStrategy implements IUpdateStrategy {
    private static IUpdateStrategy ONLY;

    /**
     * Constructor.
     */
    private VerticalStrategy() {
    }

    /**
     * Get the strategy name.
     * @return strategy name
     */
    public String getName() {
        return "vertical";
    }

    /**
     * Make a vertical strategy.
     * @return A vertical strategy
     */
    public static IUpdateStrategy make() {
        if (ONLY == null) {
            ONLY = new VerticalStrategy();
        }
        return ONLY;
    }

    /**
     * Update the ball state in the ball world.
     * @param context The ball to update
     */
    public void updateState(MovingLine context) {
        Point newStartLine = new Point(context.getStartLine().x, context.getStartLine().y + context.getVelocity().y);
        context.setStartLine(newStartLine);
        Point newEndLine = new Point(context.getEndLine().x, context.getEndLine().y + context.getVelocity().y);
        context.setEndLine(newEndLine);
    }
}
