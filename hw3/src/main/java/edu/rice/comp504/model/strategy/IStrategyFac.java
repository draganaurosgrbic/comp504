package edu.rice.comp504.model.strategy;

/**
 * Interface with abstract methods for creating ball movement strategies.
 */
public interface IStrategyFac {

    /**
     * Method creates new ball movement strategy.
     */
    IUpdateStrategy make(String type);
}
