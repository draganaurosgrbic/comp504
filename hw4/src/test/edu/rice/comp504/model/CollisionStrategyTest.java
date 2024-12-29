package edu.rice.comp504.model;

import edu.rice.comp504.model.paintobject.PaintObject;
import edu.rice.comp504.model.paintobject.PaintObjectFactory;
import edu.rice.comp504.model.strategy.collision.*;
import edu.rice.comp504.model.strategy.update.NullUpdateStrategy;
import edu.rice.comp504.model.strategy.update.RotationStrategy;
import junit.framework.TestCase;

import java.awt.*;

public class CollisionStrategyTest extends TestCase {

    private final int TEST_CANVAS_DIMENSIONS_X = 600;

    private final int TEST_CANVAS_DIMENSIONS_Y = 300;

    private final String TEST_TYPE = "ball";
    private final int TEST_LOCATION_X = 10;
    private final int TEST_LOCATION_Y = 10;
    private final int TEST_VELOCITY_X = 10;
    private final int TEST_VELOCITY_Y = 10;
    private final int TEST_SIZE = 10;
    private final String TEST_COLOR = "red";

    public void testGetName() {
        assertEquals(ShrinkStrategy.getOnly().getName(), "shrink");
        assertEquals(FadeStrategy.getOnly().getName(), "fade");
        assertEquals(ChangeColorStrategy.getOnly().getName(), "change_color");
        assertEquals(AddCrossStrategy.getOnly().getName(), "add_cross");
        assertEquals(FishConvertStrategy.getOnly().getName(), "fish_convert");
        assertEquals(ChangeDirectionStrategy.getOnly().getName(), "change_direction");
        assertEquals(EatStrategy.getOnly().getName(), "eat");
        assertEquals(DestroyStrategy.getOnly().getName(), "destroy");
        assertEquals(StuckStrategy.getOnly().getName(), "stuck");
        assertEquals(TranslateStrategy.getOnly().getName(), "translate");
        assertEquals(NullCollisionStrategy.getOnly().getName(), "null");
    }

    public void testUpdateBalls() {
        PaintWorldStore.setCanvasDimensions(TEST_CANVAS_DIMENSIONS_X + "", TEST_CANVAS_DIMENSIONS_Y + "");

        PaintObject ball = createTestBall();
        PaintObject otherBall = createTestBall();
        ShrinkStrategy.getOnly().updateBalls(ball, otherBall);
        assertEquals(otherBall.getSize(), TEST_SIZE - 1);

        ball = createTestBall();
        otherBall = createTestBall();
        FadeStrategy.getOnly().updateBalls(ball, otherBall);
        assertEquals(otherBall.getOpacity(), 1 - 0.1, 0.05);

        ball = createTestBall();
        otherBall = createTestBall();
        ChangeColorStrategy.getOnly().updateBalls(ball, otherBall);
        assertFalse(otherBall.getColor().equals(TEST_COLOR));

        ball = createTestBall();
        otherBall = createTestBall();
        AddCrossStrategy.getOnly().updateBalls(ball, otherBall);
        assertTrue(otherBall.getHasCross());

        ball = createTestBall();
        otherBall = createTestBall();
        FishConvertStrategy.getOnly().updateBalls(ball, otherBall);
        assertFalse(otherBall.isBall());
        assertEquals(otherBall.getType(), "fish");
        assertEquals(otherBall.getSize(), PaintObjectFactory.FISH_SIZE);

        ball = createTestBall();
        otherBall = createTestBall();
        ChangeDirectionStrategy.getOnly().updateBalls(ball, otherBall);
        assertEquals(otherBall.getVelocity().x, -TEST_VELOCITY_X);
        assertEquals(otherBall.getVelocity().y, -TEST_VELOCITY_Y);

        ball = createTestBall();
        otherBall = createTestBall();
        EatStrategy.getOnly().updateBalls(ball, otherBall);
        assertTrue(ball.getSize() > TEST_SIZE);
        assertEquals(otherBall.getSize(), 0);
        assertTrue(otherBall.isEmpty());
        assertFalse(otherBall.isBall());

        ball = createTestBall();
        otherBall = createTestBall();
        DestroyStrategy.getOnly().updateBalls(ball, otherBall);
        assertEquals(otherBall.getSize(), 0);
        assertTrue(otherBall.isEmpty());
        assertFalse(otherBall.isBall());

        ball = createTestBall();
        otherBall = createTestBall();
        StuckStrategy.getOnly().updateBalls(ball, otherBall);
        assertTrue(otherBall.getUpdateStrategy() instanceof NullUpdateStrategy);

        ball = createTestBall();
        otherBall = createTestBall();
        TranslateStrategy.getOnly().updateBalls(ball, otherBall);
        assertEquals(otherBall.getLocation().x, TEST_CANVAS_DIMENSIONS_X - TEST_LOCATION_X);
        assertEquals(otherBall.getLocation().y, TEST_CANVAS_DIMENSIONS_Y - TEST_LOCATION_Y);

        ball = createTestBall();
        otherBall = createTestBall();
        otherBall.setUpdateStrategy(RotationStrategy.getOnly());
        TranslateStrategy.getOnly().updateBalls(ball, otherBall);
        assertEquals(otherBall.getLocation().x, TEST_LOCATION_X);
        assertEquals(otherBall.getLocation().y, TEST_LOCATION_Y);
        assertEquals(otherBall.getAngle(), 180.0, 0.05);

        ball = createTestBall();
        otherBall = createTestBall();
        NullCollisionStrategy.getOnly().updateBalls(ball, otherBall);

        assertEquals(ball.getType(), "ball");
        assertEquals(ball.getLocation().x, TEST_LOCATION_X);
        assertEquals(ball.getLocation().y, TEST_LOCATION_Y);
        assertEquals(ball.getVelocity().x, TEST_VELOCITY_X);
        assertEquals(ball.getVelocity().y, TEST_VELOCITY_Y);
        assertEquals(ball.getSize(), TEST_SIZE);
        assertEquals(ball.getColor(), TEST_COLOR);
        assertEquals(ball.getOpacity(), 1, 0.05);
        assertFalse(ball.getHasCross());
        assertTrue(ball.isBall());
        assertFalse(ball.isEmpty());

        assertEquals(otherBall.getType(), "ball");
        assertEquals(otherBall.getLocation().x, TEST_LOCATION_X);
        assertEquals(otherBall.getLocation().y, TEST_LOCATION_Y);
        assertEquals(otherBall.getVelocity().x, TEST_VELOCITY_X);
        assertEquals(otherBall.getVelocity().y, TEST_VELOCITY_Y);
        assertEquals(otherBall.getSize(), TEST_SIZE);
        assertEquals(otherBall.getColor(), TEST_COLOR);
        assertEquals(otherBall.getOpacity(), 1, 0.05);
        assertFalse(otherBall.getHasCross());
        assertTrue(otherBall.isBall());
        assertFalse(otherBall.isEmpty());
    }

    private PaintObject createTestBall() {
        return new PaintObject(TEST_TYPE, new Point(TEST_LOCATION_X, TEST_LOCATION_Y), new Point(TEST_VELOCITY_X, TEST_VELOCITY_Y), TEST_SIZE, TEST_COLOR);
    }

}
