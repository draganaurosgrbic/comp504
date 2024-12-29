package edu.rice.comp504.model.strategy.update;

import edu.rice.comp504.model.paintobject.PaintObject;

/**
 * Interface which all update strategies should implement.
 */
public interface IUpdateStrategy {

    /**
     * Method returns name of update strategy.
     */
    String getName();

    /**
     * Method updates state of paint object.
     */
    void updateState(PaintObject object);
}
