package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.MovingLine;


/**
 * Composite strategy is composed of the Horizontal and Vertical strategies.
 */
public class CompositeStrategy implements IUpdateStrategy {
    private static IUpdateStrategy ONLY;
    private final IUpdateStrategy[] children;

    /**
     * Constructor.
     * @param children The composite strategy children
     */
    private CompositeStrategy(IUpdateStrategy[] children) {
        this.children = children;
    }


    /**
     * Make a composite strategy.
     * @return A composite strategy
     */
    public static IUpdateStrategy make() {
        if (ONLY == null) {
            IUpdateStrategy[] children = new IUpdateStrategy[2];
            children[0] = HorizontalStrategy.make();
            children[1] = VerticalStrategy.make();
            ONLY = new CompositeStrategy(children);
        }
        return ONLY;
    }

    /**
     * Get the strategy name.
     * @return strategy name
     */
    public String getName() {
        return "composite";
    }

    /**
     * Update the ball state in the ball world.
     * @param context The ball to update
     */
    public void updateState(MovingLine context) {
        for (IUpdateStrategy child: children) {
            child.updateState(context);
        }
    }
}
