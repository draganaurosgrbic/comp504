package model.strategy;

import model.Pacman;
import model.Ghost;
import java.awt.*;

/**
 * This class is used to update the state of the pacman.
 */
public class PacmanMove implements IPacmanUpdateStrategy {

    private static IPacmanUpdateStrategy ONLY;  // singleton instance

    /**
     * Private constructor.
     */
    private PacmanMove() {}

    /**
     * Returns the Singleton instance.
     */
    public static IPacmanUpdateStrategy getOnly() {
        if (ONLY == null) {
            ONLY = new PacmanMove();
        }
        return ONLY;
    }

    @Override
    public void update(Pacman context, String requestDirection) {
        int[][] board = context.getBoardStore().getBoard();
        Ghost[] ghosts = context.getBoardStore().getGhosts();

        detectGhosts(context, ghosts);

        changeDirection(context, board, requestDirection);

        context.getBoardStore().pacmanMoves(nextLoc(context, context.getDir()));

        detectGhosts(context, ghosts);
    }

    /**
     * change the direction of the Pacman.
     * @param context Pacman
     * @param board board
     * @param requestDirection the requested direction
     */
    public void changeDirection(Pacman context, int[][] board, String requestDirection) {
        if (requestDirection.equals("up")) {
            Point next = nextLoc(context, new Point(-1, 0));
            if (board[next.x][next.y] != 1) {
                context.setDir(new Point(-1, 0));
            }
        } else if (requestDirection.equals("down")) {
            Point next = nextLoc(context, new Point(1, 0));
            if (board[next.x][next.y] != 1) {
                context.setDir(new Point(1, 0));
            }
        } else if (requestDirection.equals("left")) {
            if (context.getLoc().y == 0) {
                context.setDir(new Point(0, -1));
            } else {
                Point next = nextLoc(context, new Point(0, -1));
                if (board[next.x][next.y] != 1) {
                    context.setDir(new Point(0, -1));
                }
            }
        } else if (requestDirection.equals("right")) {
            if (context.getLoc().y == 27) {
                context.setDir(new Point(0, 1));
            } else {
                Point next = nextLoc(context, new Point(0, 1));
                if (board[next.x][next.y] != 1) {
                    context.setDir(new Point(0, 1));
                }
            }
        }
    }

    /**
     * get the next location of the Pacman.
     * @param context Pacman
     * @param dir the direction of the Pacman
     * @return the next location of the Pacman
     */
    public Point nextLoc(Pacman context, Point dir) {
        if (context.getLoc().y == 0 && dir.y == -1) {
            return new Point(context.getLoc().x + dir.x, 27);
        } else if (context.getLoc().y == 27 && dir.y == 1) {
            return new Point(context.getLoc().x + dir.x, 0);
        } else {
            return new Point(context.getLoc().x + dir.x, context.getLoc().y + dir.y);
        }
    }

    /**
     * detect the collision between the Pacman and the ghosts.
     * @param pacman Pacman
     * @param ghosts ghosts
     */
    public void detectGhosts(Pacman pacman, Ghost[] ghosts) {
        for (Ghost ghost: ghosts) {
            if (pacman.getLoc().x == ghost.getLoc().x && pacman.getLoc().y == ghost.getLoc().y) {
                if (ghost.getColor().equals("afraid")) {
                    pacman.getBoardStore().pacmanEatsGhost(ghost);
                } else if (!ghost.getColor().equals("eyes")) {
                    pacman.getBoardStore().pacmanDies();
                }
            }
        }
    }
}
