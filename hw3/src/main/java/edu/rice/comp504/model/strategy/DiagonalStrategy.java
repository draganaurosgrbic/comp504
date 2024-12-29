package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.ball.Ball;

public class DiagonalStrategy implements IUpdateStrategy {
    private static IUpdateStrategy ONLY;
    private IUpdateStrategy[] children;

    /**
     * Static method for fetching the Singleton instance.
     */
    public static IUpdateStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new DiagonalStrategy();
        }
        return ONLY;
    }

    private DiagonalStrategy() {
        children = new IUpdateStrategy[2];
        children[0] = HorizontalStrategy.getOnly();
        children[1] = VerticalStrategy.getOnly();
    }

    @Override
    public String getName() {
        return "diagonal";
    }

    @Override
    public void updateState(Ball context) {
        for (IUpdateStrategy child: children) {
            child.updateState(context);
        }
    }
}
