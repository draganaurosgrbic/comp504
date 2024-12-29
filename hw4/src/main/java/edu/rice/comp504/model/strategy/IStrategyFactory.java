package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.strategy.collision.ICollisionStrategy;
import edu.rice.comp504.model.strategy.update.IUpdateStrategy;

public interface IStrategyFactory {
    IUpdateStrategy makeUpdateStrategy(String type);

    ICollisionStrategy makeCollisionStrategy(String type);
}
