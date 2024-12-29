package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.ball.Ball;

/**
 * Interface with abstract methods common for all ball movement strategies.
 */
public interface IUpdateStrategy {

    /**
     * Method returns name of a ball movement strategy.
     */
    String getName();

    /**
     * Method updates position of a ball.
     */
    void updateState(Ball context);
}
