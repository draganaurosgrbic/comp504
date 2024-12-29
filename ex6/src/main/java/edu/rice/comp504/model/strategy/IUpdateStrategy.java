package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.paintobj.APaintObject;

/**
 * The IUpdateStrategy interface is used to determine the behavior of a paint object in the canvas over time.
 */
public interface IUpdateStrategy {

    /**
     * Update the state the paint object.
     * @param context The paint object to apply the strategy to.
     */
    void updateState(APaintObject context);

    /**
     * Get the name of the strategy.
     * @return The strategy name.
     */
    String getName();
}
