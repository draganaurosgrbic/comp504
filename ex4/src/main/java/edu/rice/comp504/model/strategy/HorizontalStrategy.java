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
     * Get the strategy name.
     * @return strategy name
     */
    public String getName() {
        return "horizontal";
    }

    /**
     * Make a horizontal strategy.
     * @return A horizontal strategy
     */
    public static IUpdateStrategy make() {
        if (ONLY == null) {
            ONLY = new HorizontalStrategy();
        }
        return ONLY;
    }

    /**
     * Update the ball state in the ball world.
     * @param context The ball to update
     */
    public void updateState(MovingLine context) {
        Point newStartLine = new Point(context.getStartLine().x + context.getVelocity().x, context.getStartLine().y);
        context.setStartLine(newStartLine);
        Point newEndLine = new Point(context.getEndLine().x + context.getVelocity().x, context.getEndLine().y);
        context.setEndLine(newEndLine);
    }
}
