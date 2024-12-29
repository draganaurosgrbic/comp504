package model.strategy;

import model.Pacman;

/**
 * This class is used to update the state of the pacman.
 */
public interface IPacmanUpdateStrategy {

    /**
     * This method is used to update the state of the pacman.
     * @param context Pacman
     * @param requestDirection the direction of the pacman
     */
    void update(Pacman context, String requestDirection);
}
