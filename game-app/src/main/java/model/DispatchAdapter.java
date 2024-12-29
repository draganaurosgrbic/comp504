package model;

import java.awt.*;

/**
 * DispatchAdapter to handle the game logic by interfacing with BoardStore.
 */
public class DispatchAdapter {
    private BoardStore boardStore;

    /**
     * Constructor.
     */
    public DispatchAdapter() {
        boardStore = new BoardStore();
    }

    /**
     * get the board.
     * @return the board
     */
    public int[][] getBoard() {
        return boardStore.getBoard();
    }

    /**
     * get the pacman.
     * @return the pacman
     */
    public Pacman getPacman() {
        return boardStore.getPacman();
    }

    /**
     * get the ghosts.
     * @return the ghosts
     */
    public Ghost[] getGhosts() {
        return boardStore.getGhosts();
    }

    /**
     * get the pacman direction.
     * @return the pacman direction
     */
    public Point getPacmanDir() {
        return boardStore.getPacmanDir();
    }

    /**
     * Gets the current score.
     * @return The current score.
     */
    public int getScore() {
        return boardStore.getScore();
    }

    /**
     * Gets the current number of lives remaining.
     * @return The current number of lives remaining.
     */
    public int getLives() {
        return boardStore.getLives();
    }

    /**
     * Gets the current level.
     * @return The current level.
     */
    public int getGameLevel() {
        return boardStore.getGameLevel();
    }

    /**
     * Gets the current game status.
     * @return The current game status.
     */
    public int getGameStatus() {
        return boardStore.getGameStatus();
    }

    /**
     * Starts a new game by resetting the board and characters.
     */
    public void startGame() {
        boardStore.startGame();
    }

    /**
     * Pause the game.
     */
    public void pauseGame() {
        boardStore.pauseGame();
    }

    /**
     * Ends the game.
     */
    public void endGame() {
        boardStore.resetGame();
    }

    /**
     * Updates the game state on each tick, such as character movements and game logic.
     * @param pacmanDirection The direction in which Pac-Man should move.
     */
    public void updateGameState(String pacmanDirection) {
        boardStore.updateGameState(pacmanDirection);
    }

    /**
     * Sets the number of ghosts.
     * @param ghosts The number of ghosts.
     */
    public void setNumGhosts(int ghosts) {
        boardStore.setNumGhosts(ghosts);
    }

}
