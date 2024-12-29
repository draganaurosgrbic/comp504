package model.strategy;

import model.Ghost;

/**
 * This class is used to update the state of the ghost.
 */
public interface IGhostUpdateStrategy {

    /**
     * This method is used to update the state of the ghost.
     * @param context Ghost
     */
    void update(Ghost context);
}
