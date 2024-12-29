package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.MovingLine;

/**
 * Strategies will implement this interface.
 */
public interface IUpdateStrategy {
    /**
     * Get the strategy name.
     * @return The strategy name
     */
    public String getName();

    /**
     * Update the line state using the behavior defined by the strategy.
     * @param context  The moving line to update
     */
    public void updateState(MovingLine context);


}
