package model.strategy;

import model.Ghost;
import model.MazeUtils;

import java.awt.*;
import java.util.LinkedList;

/**
 * This class is the strategy for the ghost which was eaten by the pacman and moves back to the base. Get closer to the base
 */
public  class GhostReturnToBase implements IGhostUpdateStrategy {

    private LinkedList<Point> path; // path on which the ghost should move in order to get to the base

    /**
     * Constructor.
     */
    public GhostReturnToBase(Ghost context) {
        path = MazeUtils.searchPath(context.getBoardStore().getBoard(), context.getLoc(), new Point(12, 13));
    }

    @Override
    public void update(Ghost context) {

        if (path.isEmpty()) {
            context.resetColor();
            context.resetStrategy();
        } else {
            context.setLoc(path.remove());
        }

    }

}