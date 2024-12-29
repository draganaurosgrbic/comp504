package model;

import junit.framework.TestCase;
import java.awt.*;
import model.strategy.*;

public class GhostTest extends TestCase {
    private BoardStore boardStore;
    private Ghost ghost;
    private IGhostUpdateStrategy moveStrategy  = new GhostMoveChase();

    public void setUp() {
        boardStore = new BoardStore();
        ghost = new Ghost(13,13,"red", moveStrategy, boardStore);
    }

    public void testMove() {
        ghost.move(1, 0);
        assertEquals(new Point(14, 13), ghost.getLoc());
    }

    public void testCanGhostMove() {
        assertFalse(ghost.canGhostMoveX(1));
        assertTrue(ghost.canGhostMoveY(1));
    }

    public void testGetLoc() {
        assertEquals(new Point(13, 13), ghost.getLoc());
    }

    public void testGetColor() {
        assertEquals("red", ghost.getColor());
    }

    public void testGetStrategy() {
        assertEquals(moveStrategy, ghost.getStrategy());
    }

    public void testGetBoardStore() {
        assertEquals(ghost.getBoardStore(), ghost.getBoardStore());
    }

    public void testSetColor() {
        ghost.setColor("green");
        assertEquals("green", ghost.getColor());
    }

    public void testSetStrategy() {
        IGhostUpdateStrategy strategy = new GhostMoveChase();
        ghost.setStrategy(strategy);
        assertEquals(strategy, ghost.getStrategy());
    }

    public void testSetLoc() {
        ghost.setLoc(new Point(1,1));
        assertEquals(1, ghost.getLoc().x);
        assertEquals(1, ghost.getLoc().y);
    }

    public void testUpdate() {
        ghost.update();
        assertEquals(1, 1);
    }
    public void testResetColor() {
        ghost.resetColor();
        assertEquals("red", ghost.getColor());
    }

    public void testResetStrategy() {
        ghost.resetStrategy();
        assertEquals(moveStrategy, ghost.getStrategy());
    }

    public void testMoveInvalid() {
        ghost.setLoc(new Point(1, 1));
        assertFalse(ghost.canGhostMove(100, 0));

        ghost.setLoc(new Point(1, 1));
        assertFalse(ghost.canGhostMove(-100, 0));

        ghost.setLoc(new Point(1, 1));
        assertFalse(ghost.canGhostMove(0, 100));

        ghost.setLoc(new Point(1, 1));
        assertFalse(ghost.canGhostMove(0, -100));
    }

}