package model;

import java.awt.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import model.strategy.*;

/**
 * Abstract class representing the game board in a Pac-Man game.
 */
public class BoardStore {
    private int[][] board; // The layout of the maze represented as a 2D array. 1 means wall, 2 means small dot, 3 means passage, 4 means fruit and 5 means blinding dots
    private Pacman pacman;  // Pacman character
    private Ghost[] ghosts; // Ghost characters


    private int lives; // Number of lives remaining
    private int score; // Current score of the game
    private int currentLevel; // Current level of the game

    private int gameStatus; // Current status of the game. 0 means playing, 1, means paused, 2 means game won and 3 means game over


    private Instant flashingTimestamp;  // Timestamp when the ghosts started flashing
    private static final long INVINCIBLE_DURATION = 10000; // Duration in milliseconds (10 seconds)
    private int ghostEatenCount; // Number of ghosts eaten during invincible state
    private Random random = new Random();


    /**
     * Constructor.
     */
    public BoardStore() {
        resetGame();
        scheduleFruitsCreation();
    }

    /**
     * Reset the board status to its initial value.
     */
    public void resetBoard() {
        pacman = new Pacman(23, 13, this);
        ghosts = new Ghost[4];
        ghosts[0] = new Ghost(12, 13, "red", GhostMoveRandom.getOnly(), this);
        ghosts[1] = new Ghost(12, 14, "blue", new GhostMoveChase(), this);
        ghosts[2] = new Ghost(13, 13, "yellow", GhostMoveAvoid.getOnly(), this);
        ghosts[3] = new Ghost(13, 14, "green", GhostMoveRandom.getOnly(), this);


        board = new int[][] {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1},
                {1, 5, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 5, 1},
                {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1},
                {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                {1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1},
                {1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1},
                {1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 3, 1, 1, 3, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 3, 1, 1, 3, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 3, 1, 1, 1, 3, 3, 1, 1, 1, 3, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 3, 1, 1, 1, 3, 3, 1, 1, 1, 3, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {3, 3, 3, 3, 3, 3, 2, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 2, 3, 3, 3, 3, 3, 3},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                {1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
                {1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
                {1, 5, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 3, 3, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 5, 1},
                {1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1},
                {1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1},
                {1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1},
                {1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1},
                {1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1},
                {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };

    }

    /**
     * get the board.
     * @return the board
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * get the pacman.
     * @return the pacman
     */
    public Pacman getPacman() {
        return pacman;
    }

    /**
     * get the ghosts.
     * @return the ghosts
     */
    public Ghost [] getGhosts() {
        return ghosts;
    }

    /**
     * get the pacman direction.
     * @return the pacman direction
     */
    public Point getPacmanDir() {
        return pacman.getDir();
    }

    /**
     * Gets the current score.
     * @return The current score.
     */
    public int getScore() {
        return score;
    }


    /**
     * Gets the current number of lives remaining.
     * @return The current number of lives remaining.
     */
    public int getLives() {
        return lives;
    }


    /**
     * Gets the current level.
     * @return The current level.
     */
    public int getGameLevel() {
        return currentLevel;
    }

    /**
     * Gets the current game status.
     * @return The current game status.
     */
    public int getGameStatus() {
        return gameStatus;
    }

    /**
     * Starts a new game by resetting the board and characters.
     */
    public void startGame() {
        gameStatus = 0;
    }

    /**
     * Pause the game.
     */
    public void pauseGame() {
        gameStatus = 1;
    }

    /**
     * Resets the current game.
     */
    public void resetGame() {
        gameStatus = 1;
        lives = 3;
        score = 0;
        currentLevel = 1;
        flashingTimestamp = null;
        ghostEatenCount = 0;

        resetBoard();
    }

    /**
     * Updates the game state on each tick, such as character movements and game logic.
     * @param pacmanDirection The direction in which Pac-Man should move.
     */
    public void updateGameState(String pacmanDirection) {
        if (flashingTimestamp != null && Duration.between(flashingTimestamp, Instant.now()).toMillis() >= INVINCIBLE_DURATION) {
            flashingTimestamp = null;
            for (Ghost ghost: ghosts) {
                if (ghost.getColor().equals("afraid")) {
                    ghost.resetColor();
                }
            }
        }

        if (gameStatus != 0) {
            return;
        }

        pacman.getStrategy().update(pacman, pacmanDirection);

        for (Ghost ghost: ghosts) {
            ghost.getStrategy().update(ghost);
            if (ghost.getColor().equals("eyes")) {
                ghost.getStrategy().update(ghost);
            }
        }

    }

    /**
     * Sets the number of ghosts.
     * @param count The number of ghosts.
     */
    public void setNumGhosts(int count) {
        ghosts = new Ghost[count];
        ghosts[0] = new Ghost(12, 13, "red", GhostMoveRandom.getOnly(), this);
        ghosts[1] = new Ghost(12, 14, "blue", new GhostMoveChase(), this);
        ghosts[2] = new Ghost(13, 13, "yellow", GhostMoveAvoid.getOnly(), this);
        ghosts[3] = new Ghost(13, 14, "green", GhostMoveRandom.getOnly(), this);

        if (count > 4) {
            ghosts[4] = new Ghost(11, 13, "red", GhostMoveRandom.getOnly(), this);
            if (count > 5) {
                ghosts[5] = new Ghost(11, 14, "green", GhostMoveRandom.getOnly(), this);
            }
        }
    }

    /**
     * Manages case when the pacman moves to another location.
     * Checks the collision with calls, updates the score if pacman ate a dot and checks whether a level has been completed
     * @param location New location of the pacman.
     */
    public void pacmanMoves(Point location) {
        int x = location.x;
        int y = location.y;
        int cell = board[x][y];

        if (cell == 1) {
            return;
        }

        pacman.setLoc(location);

        if (cell == 2 || cell == 4 || cell == 5) {
            board[x][y] = 3;

            if (cell == 2) {
                score += 10;
            } else if (cell == 4) {
                score += 100;
            } else {
                flashingTimestamp = Instant.now();
                score += 50;

                for (Ghost ghost : ghosts) {
                    if (!ghost.getColor().equals("eyes")) {
                        ghost.setColor("afraid");
                    }
                }

                ghostEatenCount = 0;
            }
        }


        board[x][y] = 3;

        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board[i].length; ++j) {
                if (board[i][j] == 2) {
                    return;
                }
            }
        }

        ++currentLevel;
        if (currentLevel >= 3) {
            resetGame();
        } else {
            resetBoard();
            for (Ghost ghost: ghosts) {
                if (!(ghost.getStrategy() instanceof GhostMoveChase)) {
                    ghost.setStrategy(new GhostMoveChase());
                    break;
                }
            }
        }
    }

    /**
     * Manages case when the pacman dies - decreases its lives, resets its position and checks whether the game is over.
     */
    public void pacmanDies() {
        lives -= 1;
        pacman.reset();

        if (lives <= 0) {
            resetGame();
            gameStatus = 3;
        }
    }

    /**
     * Manages case when the pacman eats a ghost - updated the score and sends ghost back to the base.
     * @param ghost The ghost.
     */
    public void pacmanEatsGhost(Ghost ghost) {
        ghost.setColor("eyes");
        ghost.setStrategy(new GhostReturnToBase(ghost));

        ++ghostEatenCount;
        score += 200 * (int) Math.pow(2, ghostEatenCount - 1);
    }

    /**
     * Schedules timer that periodically creates fruits on the board.
     */
    public void scheduleFruitsCreation() {
        BoardStore store = this;

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (store.gameStatus != 0) {
                    return;
                }

                int counter = 0;

                while (true) {
                    int x = random.nextInt(0, board.length);
                    int y = random.nextInt(0, board[0].length);

                    if (board[x][y] == 2 || board[x][y] == 3) {
                        board[x][y] = 4;
                        break;
                    }

                    if (++counter >= 30) {
                        break;
                    }
                }
            }
        }, 500, 10000);
    }

}

