package model;

import junit.framework.TestCase;
import model.strategy.GhostMoveAvoid;
import model.strategy.GhostMoveRandom;
import model.strategy.GhostReturnToBase;
import model.strategy.PacmanMove;

import java.awt.*;

public class ModelTest extends TestCase {

    public void testBoardData() {
        DispatchAdapter adapter = new DispatchAdapter();
        assertEquals(adapter.getBoard().length, 31);
        assertEquals(adapter.getBoard()[0].length, 28);

        assertEquals(adapter.getPacman().getLoc(), new Point(23, 13));
        assertEquals(adapter.getGhosts().length, 4);
        assertEquals(adapter.getGhosts()[0].getLoc(), new Point(12, 13));
        assertEquals(adapter.getGhosts()[0].getColor(), "red");
        assertEquals(adapter.getGhosts()[1].getLoc(), new Point(12, 14));
        assertEquals(adapter.getGhosts()[1].getColor(), "blue");
        assertEquals(adapter.getGhosts()[2].getLoc(), new Point(13, 13));
        assertEquals(adapter.getGhosts()[2].getColor(), "yellow");
        assertEquals(adapter.getGhosts()[3].getLoc(), new Point(13, 14));
        assertEquals(adapter.getGhosts()[3].getColor(), "green");

        assertEquals(adapter.getPacmanDir(), new Point(0, 0));

        assertEquals(adapter.getScore(), 0);
        assertEquals(adapter.getLives(), 3);
        assertEquals(adapter.getGameLevel(), 1);
        assertEquals(adapter.getGameStatus(), 1);
    }

    public void testSetGhosts() {
        DispatchAdapter adapter = new DispatchAdapter();
        adapter.setNumGhosts(6);

        assertEquals(adapter.getGhosts().length, 6);
        assertEquals(adapter.getGhosts()[0].getLoc(), new Point(12, 13));
        assertEquals(adapter.getGhosts()[0].getColor(), "red");
        assertEquals(adapter.getGhosts()[1].getLoc(), new Point(12, 14));
        assertEquals(adapter.getGhosts()[1].getColor(), "blue");
        assertEquals(adapter.getGhosts()[2].getLoc(), new Point(13, 13));
        assertEquals(adapter.getGhosts()[2].getColor(), "yellow");
        assertEquals(adapter.getGhosts()[3].getLoc(), new Point(13, 14));
        assertEquals(adapter.getGhosts()[3].getColor(), "green");
        assertEquals(adapter.getGhosts()[4].getLoc(), new Point(11, 13));
        assertEquals(adapter.getGhosts()[4].getColor(), "red");
        assertEquals(adapter.getGhosts()[5].getLoc(), new Point(11, 14));
        assertEquals(adapter.getGhosts()[5].getColor(), "green");
    }

    public void testGameUpdates() {
        DispatchAdapter adapter = new DispatchAdapter();
        adapter.startGame();
        assertEquals(adapter.getGameStatus(), 0);

        adapter.pauseGame();
        assertEquals(adapter.getGameStatus(), 1);

        adapter.endGame();
        assertEquals(adapter.getScore(), 0);
        assertEquals(adapter.getLives(), 3);
        assertEquals(adapter.getGameLevel(), 1);
        assertEquals(adapter.getGameStatus(), 1);

        adapter.updateGameState("right");
        assertEquals(adapter.getPacman().getLoc(), new Point(23, 13));
        assertEquals(adapter.getGhosts().length, 4);
        assertEquals(adapter.getGhosts()[0].getLoc(), new Point(12, 13));
        assertEquals(adapter.getGhosts()[0].getColor(), "red");
        assertEquals(adapter.getGhosts()[1].getLoc(), new Point(12, 14));
        assertEquals(adapter.getGhosts()[1].getColor(), "blue");
        assertEquals(adapter.getGhosts()[2].getLoc(), new Point(13, 13));
        assertEquals(adapter.getGhosts()[2].getColor(), "yellow");
        assertEquals(adapter.getGhosts()[3].getLoc(), new Point(13, 14));
        assertEquals(adapter.getGhosts()[3].getColor(), "green");

        adapter.startGame();
        adapter.updateGameState("right");
        assertFalse(adapter.getPacman().getLoc().equals(new Point(23, 13)));
        assertEquals(adapter.getGhosts().length, 4);
        assertFalse(adapter.getGhosts()[0].getLoc().equals(new Point(12, 13)));
        assertEquals(adapter.getGhosts()[0].getColor(), "red");
        assertTrue(adapter.getGhosts()[1].getLoc().equals(new Point(12, 14)));
        assertEquals(adapter.getGhosts()[1].getColor(), "blue");
        assertFalse(adapter.getGhosts()[2].getLoc().equals(new Point(13, 13)));
        assertEquals(adapter.getGhosts()[2].getColor(), "yellow");
        assertFalse(adapter.getGhosts()[3].getLoc().equals(new Point(13, 14)));
        assertEquals(adapter.getGhosts()[3].getColor(), "green");

        adapter.updateGameState("right");
        adapter.updateGameState("left");
        adapter.updateGameState("up");
        adapter.updateGameState("down");
        adapter.updateGameState("right");
        adapter.updateGameState("right");
        assertFalse(adapter.getPacman().getLoc().equals(new Point(23, 13)));

        assertTrue(MazeUtils.searchPath(new int[][]{}, new Point(), new Point(1, 1)).isEmpty());
    }

    public void testReturnToBase() {
        DispatchAdapter adapter = new DispatchAdapter();
        adapter.getGhosts()[0].setStrategy(new GhostReturnToBase(adapter.getGhosts()[0]));
        adapter.getGhosts()[0].update();
        adapter.getGhosts()[0].update();
        adapter.getGhosts()[0].update();

        assertEquals(adapter.getGhosts()[0].getColor(), "red");
        assertTrue(adapter.getGhosts()[0].getStrategy() instanceof GhostMoveRandom);

        adapter.getPacman().setLoc(new Point(-100, -100));
        adapter.getGhosts()[2].update();
        adapter.getGhosts()[2].update();
        adapter.getGhosts()[2].update();
        assertEquals(adapter.getGhosts()[2].getColor(), "yellow");
        assertTrue(adapter.getGhosts()[2].getStrategy() instanceof GhostMoveAvoid);
    }

    public void testInteractions() {
        BoardStore store = new BoardStore();
        assertEquals(store.getScore(), 0);
        assertEquals(store.getLives(), 3);
        assertEquals(store.getGameLevel(), 1);
        assertEquals(store.getGameStatus(), 1);

        store.pacmanDies();
        assertEquals(store.getLives(), 2);
        store.pacmanDies();
        assertEquals(store.getLives(), 1);

        store.pacmanDies();
        assertEquals(store.getScore(), 0);
        assertEquals(store.getLives(), 3);
        assertEquals(store.getGameLevel(), 1);
        assertEquals(store.getGameStatus(), 3);

        Pacman pacman = new Pacman(0, 0, store);
        Ghost[] ghosts = new Ghost[] {new Ghost(0, 0, "red", GhostMoveRandom.getOnly(), store)};
        PacmanMove pacmanMove = (PacmanMove) pacman.getStrategy();

        pacmanMove.detectGhosts(pacman, ghosts);
        assertEquals(store.getLives(), 2);

        pacmanMove.detectGhosts(pacman, ghosts);
        assertEquals(store.getLives(), 1);
    }

}
