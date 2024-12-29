package edu.rice.comp504.model.strategy.collision;

import edu.rice.comp504.model.paintobject.PaintObject;

/**
 * Interface which all collision strategies should implement.
 */
public interface ICollisionStrategy {

    /**
     * Method returns name of collision strategy.
     */
    String getName();

    /**
     * Method updates balls which collide.
     */
    void updateBalls(PaintObject ball, PaintObject otherBall);
}
