package model;


import junit.framework.TestCase;
import java.awt.*;
import model.strategy.*;

public class PacmanTest extends TestCase {
    private Pacman pacman;
    private BoardStore boardStore;
    private int[][] board;
    private Ghost[] ghosts;
    private IPacmanUpdateStrategy moveStrategy  = PacmanMove.getOnly();

    public void setUp() {
        boardStore = new BoardStore();
        pacman = boardStore.getPacman();
        board = boardStore.getBoard();
        ghosts = boardStore.getGhosts();
        moveStrategy  = PacmanMove.getOnly();
        boardStore.startGame();
    }

    public void testPacmanMove() {
        pacman.setLoc(new Point(1, 1));
        moveStrategy.update(pacman, "right");
        assertEquals(new Point(1, 2), pacman.getLoc());
        moveStrategy.update(pacman, "left");
        assertEquals(new Point(1, 1), pacman.getLoc());
        moveStrategy.update(pacman,  "down");
        assertEquals(new Point(2, 1), pacman.getLoc());
        moveStrategy.update(pacman, "up");
        assertEquals(new Point(1, 1), pacman.getLoc());
    }

    public void testEatBlindingDot() {
        pacman.setLoc(new Point(2, 1));
        moveStrategy.update(pacman, "down");
        assertEquals(3, board[pacman.getLoc().x][pacman.getLoc().y]);
    }

    public void testEatSmallDot() {
        pacman.setLoc(new Point(1, 2));
        moveStrategy.update(pacman,  "right");
        assertEquals(3, board[pacman.getLoc().x][pacman.getLoc().y]);
        assertEquals(10, boardStore.getScore());
    }

    public void testEatGhost() {
        pacman.setLoc(new Point(2, 1));
        moveStrategy.update(pacman, "down");
        ghosts[0].setLoc(new Point(3, 1));
        moveStrategy.update(pacman, "down");
        assertEquals(260, boardStore.getScore());
    }

    public void testEatFruit() {
        pacman.setLoc(new Point(5, 13));
        moveStrategy.update(pacman, "right");
        assertEquals(3, board[pacman.getLoc().x][pacman.getLoc().y]);
        assertEquals(100, boardStore.getScore());
    }


    public void testExit() {
        pacman.setLoc(new Point(14,0));
        moveStrategy.update(pacman,  "left");
        assertTrue(pacman.getLoc().y == 27);

        pacman.setLoc(new Point(14,27));
        moveStrategy.update(pacman,  "right");
        assertTrue(pacman.getLoc().y == 0);
    }

    public void testReset() {
        pacman.reset();
        assertEquals(pacman.getLoc(), new Point(23, 13));
    }

}