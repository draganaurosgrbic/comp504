package model.strategy;

import model.Ghost;
import model.MazeUtils;

import java.awt.*;
import java.util.LinkedList;

/**
 * This class is the strategy for the ghost which is trying to chase pacman. Get closer to Pacman
 */
public  class GhostMoveChase implements IGhostUpdateStrategy {

    private LinkedList<Point> path; // path on which the ghost should chase the pacman
    private Point lastPacmanLocation;   // last unmodified location of the pacman

    /**
     * Set the path on which the ghost should move in order to catch the pacman.
     * @param ghost The ghost
     */
    public void setPath(Ghost ghost) {
        Point pacmanLocation = ghost.getBoardStore().getPacman().getLoc();

        if (lastPacmanLocation != null && lastPacmanLocation.x == pacmanLocation.x && lastPacmanLocation.y == pacmanLocation.y) {
            return;
        }

        path = MazeUtils.searchPath(ghost.getBoardStore().getBoard(), ghost.getLoc(), pacmanLocation);
        lastPacmanLocation = pacmanLocation;
    }

    @Override
    public void update(Ghost context) {
        if (path == null || path.isEmpty()) {
            setPath(context);
        }

        if (!path.isEmpty()) {
            context.setLoc(path.remove());
        }
    }

}