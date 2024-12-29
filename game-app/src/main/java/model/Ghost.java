package model;

import model.strategy.GhostMoveChase;
import model.strategy.IGhostUpdateStrategy;
import java.awt.*;

/**
 * Abstract class representing a ghost character in the Pac-Man game.
 */
public class Ghost {
    private int x;  // Current x position on the board
    private int y; // Current y position on the board
    private String color; // Color of the ghost

    private IGhostUpdateStrategy strategy; // Update strategy of the ghost
    private BoardStore boardStore; // The game board

    private String startColor;  // starting color, used for resetting

    private IGhostUpdateStrategy startStrategy; // starting strategy, used for resetting

    /**
     * Constructor.
     */
    public Ghost(int x, int y, String color, IGhostUpdateStrategy strategy, BoardStore boardStore) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.strategy = strategy;
        this.boardStore = boardStore;

        this.startColor = color;
        this.startStrategy = strategy;
    }

    /**
     * Check whether the ghost can move in the requested direction.
     * @param dx - x axis movement direction
     * @param dy - y axis movement direction
     * @return flag indicating whether the ghost can move in the requested direction
     */
    public boolean canGhostMove(int dx, int dy) {
        return canGhostMoveX(dx) && canGhostMoveY(dy);
    }

    /**
     * Check whether the ghost can move in the requested direction.
     * @param dx - x axis movement direction
     * @return flag indicating whether the ghost can move in the requested direction
     */
    public boolean canGhostMoveX(int dx) {
        int[][] board = boardStore.getBoard();

        if (x + dx < 0) {
            return false;
        }
        if (x + dx >= board.length) {
            return false;
        }
        if (board[x + dx][y] == 1) {
            return false;
        }
        return true;
    }

    /**
     * Check whether the ghost can move in the requested direction.
     * @param dy - y axis movement direction
     * @return flag indicating whether the ghost can move in the requested direction
     */
    public boolean canGhostMoveY(int dy) {
        int[][] board = boardStore.getBoard();

        if (y + dy < 0) {
            return false;
        }
        if (y + dy >= board[0].length) {
            return false;
        }
        if (board[x][y + dy] == 1) {
            return false;
        }
        return true;
    }

    /**
     * get the location of the Ghost.
     * @return the location of the Ghost
     */
    public Point getLoc() {
        return new Point(x, y);
    }

    /**
     * get the color of the Ghost.
     * @return the color of the Ghost
     */
    public String getColor() {
        return color;
    }

    /**
     * get the strategy of the ghost.
     * @return the strategy of the ghost
     */
    public IGhostUpdateStrategy getStrategy() {
        return strategy;
    }

    /**
     * get the board store.
     * @return the board store
     */
    public BoardStore getBoardStore() {
        return boardStore;
    }

    /**
     * set the strategy of the ghost.
     * @param strategy the strategy of the ghost
     */
    public void setStrategy(IGhostUpdateStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * set the color of the ghost.
     * @param color the color of the ghost
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * set the location of the ghost.
     * @param loc the location of the ghost
     */
    public void setLoc(Point loc) {
        x = loc.x;
        y = loc.y;
    }

    /**
     * update the state using the stored strategy.
     */
    public void update() {
        strategy.update(this);
    }

    /**
     * Move the ghost in the requested direction.
     * @param dx - x axis movement direction
     * @param dy - y axis movement direction
     */
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    /**
     * reset the color of the ghost to its initial value.
     */
    public void resetColor() {
        color = startColor;
    }

    /**
     * reset the strategy of the ghost to its initial value.
     */
    public void resetStrategy() {
        strategy = startStrategy;
        if (strategy instanceof GhostMoveChase) {
            ((GhostMoveChase) strategy).setPath(this);
        }
    }

}
