package model;

import model.strategy.IPacmanUpdateStrategy;
import model.strategy.PacmanMove;

import java.awt.*;

/**
 * Abstract class representing the Pac-Man character in the game.
 */
public  class Pacman {
    private int x; // Current x position on the board
    private int y; // Current y position on the board
    private int dx; // Current direction vector x-component
    private int dy; // Current direction vector y-component

    private IPacmanUpdateStrategy strategy; // Update strategy of PacMan
    private BoardStore boardStore; // The game board store

    private int startX; // starting x, used to reset location
    private int startY; // starting y, used to reset location

    /**
     * Constructor.
     */
    public Pacman(int x, int y, BoardStore boardStore) {
        this.x = x;
        this.y = y;
        this.boardStore = boardStore;

        this.startX = x;
        this.startY = y;

        this.dx = 0;
        this.dy = 0;

        this.strategy = PacmanMove.getOnly();
    }


    /**
     * get the location of PacMan.
     * @return the location of PacMan
     */
    public Point getLoc() {
        return new Point(x, y);
    }

    /**
     * set the location of PacMan.
     * @param loc the location of PacMan
     */
    public void setLoc(Point loc) {
        this.x = loc.x;
        this.y = loc.y;
    }

    /**
     * get the direction of PacMan.
     * @return the direction of PacMan
     */
    public Point getDir()  {
        return new Point(dx, dy);
    }

    /**
     * set the direction of PacMan.
     * @param dir the direction of PacMan
     */
    public void setDir(Point dir) {
        this.dx = dir.x;
        this.dy = dir.y;
    }

    /**
     * get the strategy of PacMan.
     * @return the strategy of PacMan
     */
    public  IPacmanUpdateStrategy getStrategy() {
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
     * reset the pacman location to its initial value.
     */
    public void reset() {
        x = startX;
        y = startY;
    }

}

