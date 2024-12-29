package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.MovingLine;

/**
 * A factory that makes strategies.
 */
public interface IStrategyFac {

    /**
     * Makes a strategy.
     * @return A strategy
     */
    public IUpdateStrategy make();

    /**
     * Switch line strategies.
     * @param context A line.
     * @return The new line strategy for the line
     */
    public IUpdateStrategy switchStrategy(MovingLine context);
}
