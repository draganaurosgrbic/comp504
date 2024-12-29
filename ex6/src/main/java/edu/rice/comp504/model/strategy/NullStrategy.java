package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.paintobj.APaintObject;

/**
 * The null strategy changing the color of an paint object to black.
 */
public class NullStrategy implements IUpdateStrategy {
    private static IUpdateStrategy ONLY;

    /**
     * Constructor.
     */
    private NullStrategy() {

    }

    /**
     * Make a straight strategy.  There is only one (singleton).
     * @return The singleton straight strategy
     */
    public static IUpdateStrategy makeStrategy() {
        if (ONLY == null) {
            ONLY = new NullStrategy();
        }
        return ONLY;
    }

    /**
     * Get the strategy name.
     * @return strategy name
     */
    public String getName() {
        return "null";
    }

    /**
     * Update the paintobj state in the paintobj world.
     * @param context The paintobj to update
     */
    public void updateState(APaintObject context) {
        context.setColor("black");
    }
}
