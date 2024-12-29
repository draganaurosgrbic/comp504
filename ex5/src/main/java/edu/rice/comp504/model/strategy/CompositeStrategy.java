package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.MovingLine;


/**
 * The composite strategy potentially has multiple strategies as children.
 */
public class CompositeStrategy implements IUpdateStrategy {
    private static IUpdateStrategy ONLY;
    private IUpdateStrategy[] children;

    /**
     * Constructor.
     */
    private CompositeStrategy(IUpdateStrategy[] children) {
        this.children = children;
    }

    /**
     * Get the singleton composite strategy.
     * @return The singleton composite strategy
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
