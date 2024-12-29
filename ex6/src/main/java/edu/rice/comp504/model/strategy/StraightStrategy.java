package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.paintobj.APaintObject;

import java.awt.*;

/**
 * The straight strategy causes an object to travel horizontally straight.
 */
public class StraightStrategy implements IUpdateStrategy {
    private static IUpdateStrategy ONLY;

    /**
     * Constructor.
     */
    private StraightStrategy() {

    }

    /**
     * Make a straight strategy.  There is only one (singleton).
     * @return A straight strategy
     */
    public static IUpdateStrategy makeStrategy() {
        if (ONLY == null) {
            ONLY = new StraightStrategy();
        }
        return ONLY;
    }

    /**
     * Get the strategy name.
     * @return strategy name
     */
    public String getName() {
        return "straight";
    }

    /**
     * Update the paintobj state in the paintobj world.
     * @param context The paintobj to update
     */
    public void updateState(APaintObject context) {
        Point vel = context.getVelocity();
        context.nextLocation(vel.x, 0);
        context.setColor("blue");
    }
}
