package edu.rice.comp504;

import edu.rice.comp504.adapter.DispatchAdapter;
import edu.rice.comp504.model.BallWorldStore;
import edu.rice.comp504.model.ball.Ball;
import edu.rice.comp504.model.strategy.RotatingStrategy;
import junit.framework.TestCase;

import java.beans.PropertyChangeListener;

public class UnitTests extends TestCase {

    public void testLoadBall() {
        DispatchAdapter adapter = new DispatchAdapter();
        adapter.setCanvasDims("width=300&height=150");

        PropertyChangeListener listener = adapter.loadBall("strategy=rotating", "ball");
        assertTrue(listener instanceof Ball);

        Ball ball = (Ball) listener;
        assertTrue(ball.getStrategy() instanceof RotatingStrategy);
        assertEquals(ball.getStrategy().getName(), "rotating");
    }

    public void testUpdateBalls() {
        DispatchAdapter adapter = new DispatchAdapter();
        adapter.setCanvasDims("width=300&height=150");

        Ball ball1 = (Ball) adapter.loadBall("strategy=horizontal", "ball");
        int x1 = ball1.getLocation().x, y1 = ball1.getLocation().y;

        Ball ball2 = (Ball) adapter.loadBall("strategy=vertical", "ball");
        int x2 = ball2.getLocation().x, y2 = ball2.getLocation().y;

        Ball ball3 = (Ball) adapter.loadBall("strategy=diagonal", "ball");
        int x3 = ball3.getLocation().x, y3 = ball3.getLocation().y;

        Ball ball4 = (Ball) adapter.loadBall("strategy=rotating", "ball");

        Ball ball5 = (Ball) adapter.loadBall("strategy=stairs", "ball");
        int x5 = ball5.getLocation().x, y5 = ball5.getLocation().y;

        Ball ball6 = (Ball) adapter.loadBall("strategy=waterfall", "ball");
        int x6 = ball6.getLocation().x, y6 = ball6.getLocation().y;

        Ball ball7 = (Ball) adapter.loadBall("strategy=pulse", "ball");
        int x7 = ball7.getLocation().x, y7 = ball7.getLocation().y;

        Ball ball8 = (Ball) adapter.loadBall("strategy=zig_zag", "ball");
        int x8 = ball8.getLocation().x, y8 = ball8.getLocation().y;

        Ball ball9 = (Ball) adapter.loadBall("strategy=inverted_squares", "ball");
        int x9 = ball9.getLocation().x, y9 = ball9.getLocation().y;

        Ball ball10 = (Ball) adapter.loadBall("strategy=inverted_trapezoids", "ball");
        int x10 = ball10.getLocation().x, y10 = ball10.getLocation().y;

        Ball ball11 = (Ball) adapter.loadBall("strategy=square", "ball");
        int x11 = ball11.getLocation().x, y11 = ball11.getLocation().y;

        Ball ball12 = (Ball) adapter.loadBall("strategy=rhombus", "ball");
        int x12 = ball12.getLocation().x, y12 = ball12.getLocation().y;

        BallWorldStore.setCanvasDims("width=600&height=300");

        int rotateX = (int) (BallWorldStore.getCanvasDims().x / 2 + BallWorldStore.getCanvasDims().y / 4 * Math.cos(ball4.getAngle()));
        int rotateY = (int) (BallWorldStore.getCanvasDims().y / 2 + BallWorldStore.getCanvasDims().y / 4 * Math.sin(ball4.getAngle()));

        adapter.updateBallWorld();

        assertEquals(ball1.getLocation().x, x1 + ball1.getVel().x);
        assertEquals(ball1.getLocation().y, y1);

        assertEquals(ball2.getLocation().x, x2);
        assertEquals(ball2.getLocation().y, y2 + ball2.getVel().y);

        assertEquals(ball3.getLocation().x, x3 + ball3.getVel().x);
        assertEquals(ball3.getLocation().y, y3 + ball3.getVel().y);

        assertEquals(ball4.getLocation().x, rotateX);
        assertEquals(ball4.getLocation().y, rotateY);

        assertEquals(ball5.getLocation().x, x5 + ball5.getVel().x);
        assertEquals(ball5.getLocation().y, y5);

        assertEquals(ball6.getLocation().x, x6 + ball6.getVel().x);
        assertEquals(ball6.getLocation().y, y6 + ball6.getVel().y);

        assertEquals(ball7.getLocation().x, x7 + ball7.getVel().x);
        assertEquals(ball7.getLocation().y, y7 + ball7.getVel().y);

        assertEquals(ball8.getLocation().x, x8 + ball8.getVel().x);
        assertEquals(ball8.getLocation().y, y8 + ball8.getVel().y);

        assertEquals(ball9.getLocation().x, x9 + ball9.getVel().x);
        assertEquals(ball9.getLocation().y, y9);

        assertEquals(ball10.getLocation().x, x10 + ball10.getVel().x);
        assertEquals(ball10.getLocation().y, y10 + ball10.getVel().y);

        assertEquals(ball11.getLocation().x, x11 + ball11.getVel().x);
        assertEquals(ball11.getLocation().y, y11);

        assertEquals(ball12.getLocation().x, x12 + ball12.getVel().x);
        assertEquals(ball12.getLocation().y, y12 + ball12.getVel().y);

        x1 = ball1.getLocation().x;
        y1 = ball1.getLocation().y;
        x2 = ball2.getLocation().x;
        y2 = ball2.getLocation().y;
        x3 = ball3.getLocation().x;
        y3 = ball3.getLocation().y;
        x5 = ball5.getLocation().x;
        y5 = ball5.getLocation().y;
        x6 = ball6.getLocation().x;
        y6 = ball6.getLocation().y;
        x7 = ball7.getLocation().x;
        y7 = ball7.getLocation().y;
        x8 = ball8.getLocation().x;
        y8 = ball8.getLocation().y;
        x9 = ball9.getLocation().x;
        y9 = ball9.getLocation().y;
        x10 = ball10.getLocation().x;
        y10 = ball10.getLocation().y;
        x11 = ball11.getLocation().x;
        y11 = ball11.getLocation().y;
        x12 = ball12.getLocation().x;
        y12 = ball12.getLocation().y;

        for (int i = 0; i < 10; ++i) {
            adapter.updateBallWorld();
        }

        assertEquals(ball1.getLocation().x, x1 + 10 * ball1.getVel().x);
        assertEquals(ball1.getLocation().y, y1);

        assertEquals(ball2.getLocation().x, x2);
        assertEquals(ball2.getLocation().y, y2 + 10 * ball2.getVel().y);

        assertEquals(ball3.getLocation().x, x3 + 10 * ball3.getVel().x);
        assertEquals(ball3.getLocation().y, y3 + 10 * ball3.getVel().y);

        assertEquals(ball5.getLocation().x, x5 + 9 * ball5.getVel().x);
        assertEquals(ball5.getLocation().y, y5 + ball5.getVel().y);

        assertEquals(ball6.getLocation().x, x6 + 9 * ball6.getVel().x);
        assertEquals(ball6.getLocation().y, y6 + 10 * ball6.getVel().y);

        assertEquals(ball7.getLocation().x, x7 + 9 * ball7.getVel().x);
        assertEquals(ball7.getLocation().y, y7 - 8 * ball7.getVel().y);

        assertEquals(ball8.getLocation().x, x8 + 10 * ball8.getVel().x);
        assertEquals(ball8.getLocation().y, y8 - 8 * ball8.getVel().y);

        assertEquals(ball9.getLocation().x, x9 + 9 * ball9.getVel().x);
        assertEquals(ball9.getLocation().y, y9 + ball9.getVel().y);

        assertEquals(ball10.getLocation().x, x10 + 10 * ball10.getVel().x);
        assertEquals(ball10.getLocation().y, y10 - 9 * ball10.getVel().y);

        assertEquals(ball11.getLocation().x, x11 + 9 * ball11.getVel().x);
        assertEquals(ball11.getLocation().y, y11 + ball11.getVel().y);

        assertEquals(ball12.getLocation().x, x12 + 10 * ball12.getVel().x);
        assertEquals(ball12.getLocation().y, y12 - 8 * ball12.getVel().y);
    }

    public void testAddBall() {
        DispatchAdapter adapter = new DispatchAdapter();
        adapter.setCanvasDims("width=300&height=150");

        int ballsCount = adapter.getBws().getPcs().getPropertyChangeListeners(BallWorldStore.EVENT_FIELD).length;
        assertEquals(ballsCount, 0);

        adapter.loadBall("strategy=rotating", "ball");
        int ballsCount1 = adapter.getBws().getPcs().getPropertyChangeListeners(BallWorldStore.EVENT_FIELD).length;
        assertEquals(ballsCount1, ballsCount + 1);

        adapter.loadBall("strategy=rotating", "ball");
        int ballsCount2 = adapter.getBws().getPcs().getPropertyChangeListeners(BallWorldStore.EVENT_FIELD).length;
        assertEquals(ballsCount2, ballsCount + 2);
    }

    public void testRemoveBalls() {
        DispatchAdapter adapter = new DispatchAdapter();
        adapter.setCanvasDims("width=300&height=150");

        int ballsCount = adapter.getBws().getPcs().getPropertyChangeListeners(BallWorldStore.EVENT_FIELD).length;
        assertEquals(ballsCount, 0);

        adapter.loadBall("strategy=rotating", "ball");
        int ballsCount1 = adapter.getBws().getPcs().getPropertyChangeListeners(BallWorldStore.EVENT_FIELD).length;
        assertEquals(ballsCount1, ballsCount + 1);

        adapter.loadBall("strategy=rotating", "ball");
        int ballsCount2 = adapter.getBws().getPcs().getPropertyChangeListeners(BallWorldStore.EVENT_FIELD).length;
        assertEquals(ballsCount2, ballsCount + 2);

        adapter.removeAll();
        ballsCount = adapter.getBws().getPcs().getPropertyChangeListeners(BallWorldStore.EVENT_FIELD).length;
        assertEquals(ballsCount, 0);
    }

}
