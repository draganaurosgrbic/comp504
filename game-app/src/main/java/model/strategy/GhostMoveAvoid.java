package model.strategy;

import model.Ghost;
import model.Pacman;

/**
 * This class is the strategy for the ghost which is trying to avoid pacman. Move away from pacman
 */
public  class GhostMoveAvoid implements IGhostUpdateStrategy {

    private static IGhostUpdateStrategy ONLY;   // singleton instance

    /**
     * Private constructor.
     */
    private GhostMoveAvoid() {}

    /**
     * Returns the Singleton instance.
     */
    public static IGhostUpdateStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new GhostMoveAvoid();
        }
        return ONLY;
    }

    @Override
    public void update(Ghost context) {
        Pacman pacman = context.getBoardStore().getPacman();

        int dx = Integer.compare(context.getLoc().x, pacman.getLoc().x);
        int dy = Integer.compare(context.getLoc().y, pacman.getLoc().y);

        if (dx != 0 && context.canGhostMoveX(dx)) {
            context.move(dx, 0);
        } else if (dy != 0 && context.canGhostMoveY(dy)) {
            context.move(0, dy);
        } else {
            GhostMoveRandom.getOnly().update(context);
        }
    }

}
