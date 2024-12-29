package model.strategy;

import model.Ghost;

import java.util.Random;

/**
 * This class is the strategy for the ghost which randomly moves. Random walk
 */
public  class GhostMoveRandom implements IGhostUpdateStrategy {

    private static IGhostUpdateStrategy ONLY;   // singleton instance
    private Random random = new Random();

    /**
     * Private constructor.
     */
    private GhostMoveRandom() {}

    /**
     * Returns the Singleton instance.
     */
    public static IGhostUpdateStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new GhostMoveRandom();
        }
        return ONLY;
    }

    @Override
    public void update(Ghost context) {
        int[] directions = new int[]{1, -1, 1, -1};
        int dx = 0;
        int dy = 0;
        int index;
        boolean canMove = false;

        while (!canMove) {
            index = random.nextInt(0, 4);
            dx = index < 2 ? directions[index] : 0;
            dy = index < 2 ? 0 : directions[index];
            canMove = context.canGhostMove(dx, dy);
        }

        context.move(dx, dy);
    }

}