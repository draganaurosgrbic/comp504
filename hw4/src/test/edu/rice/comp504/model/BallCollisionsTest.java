package edu.rice.comp504.model;

import edu.rice.comp504.model.paintobject.PaintObject;
import edu.rice.comp504.model.paintobject.PaintObjectFactory;
import edu.rice.comp504.model.strategy.IStrategyFactory;
import edu.rice.comp504.model.strategy.StrategyFactory;
import edu.rice.comp504.model.strategy.update.NullUpdateStrategy;
import junit.framework.TestCase;

import java.awt.*;

public class BallCollisionsTest extends TestCase {

    private final IStrategyFactory STRATEGY_FACTORY = new StrategyFactory();

    private final int TEST_CANVAS_DIMENSIONS_X = 600;

    private final int TEST_CANVAS_DIMENSIONS_Y = 300;

    private final String TEST_TYPE = "ball";
    private final int TEST_LOCATION_X = 10;
    private final int TEST_LOCATION_Y = 10;
    private final int TEST_VELOCITY_X = 1;
    private final int TEST_VELOCITY_Y = 1;
    private final int TEST_SIZE = 10;
    private final String TEST_COLOR = "red";

    public void testShrinkInteraction() {
        PaintWorldStore.setCanvasDimensions(TEST_CANVAS_DIMENSIONS_X + "", TEST_CANVAS_DIMENSIONS_Y + "");
        PaintWorldStore store = new PaintWorldStore();

        PaintObject object1 = createTestBall(0, 0, "shrink");
        PaintObject object2 = createTestBall(1, 1, "shrink");
        store.getSupport().addPropertyChangeListener(PaintWorldStore.EVENT_FIELD, object1);
        store.getSupport().addPropertyChangeListener(PaintWorldStore.EVENT_FIELD, object2);
        store.updateWorld();

        assertEquals(object1.getSize(), TEST_SIZE - 1);
        assertEquals(object2.getSize(), TEST_SIZE - 1);
        assertEquals(store.paintWorld().length, 2);
    }

    public void testFadeInteraction() {
        PaintWorldStore.setCanvasDimensions(TEST_CANVAS_DIMENSIONS_X + "", TEST_CANVAS_DIMENSIONS_Y + "");
        PaintWorldStore store = new PaintWorldStore();

        PaintObject object1 = createTestBall(0, 0, "fade");
        PaintObject object2 = createTestBall(1, 1, "fade");
        store.getSupport().addPropertyChangeListener(PaintWorldStore.EVENT_FIELD, object1);
        store.getSupport().addPropertyChangeListener(PaintWorldStore.EVENT_FIELD, object2);
        store.updateWorld();

        assertEquals(object1.getOpacity(), 1 - 0.1, 0.05);
        assertEquals(object2.getOpacity(), 1 - 0.1, 0.05);
        assertEquals(store.paintWorld().length, 2);
    }

    public void testChangeColorInteraction() {
        PaintWorldStore.setCanvasDimensions(TEST_CANVAS_DIMENSIONS_X + "", TEST_CANVAS_DIMENSIONS_Y + "");
        PaintWorldStore store = new PaintWorldStore();

        PaintObject object1 = createTestBall(0, 0, "change_color");
        PaintObject object2 = createTestBall(1, 1, "change_color");
        store.getSupport().addPropertyChangeListener(PaintWorldStore.EVENT_FIELD, object1);
        store.getSupport().addPropertyChangeListener(PaintWorldStore.EVENT_FIELD, object2);
        store.updateWorld();

        assertFalse(object1.getColor().equals(TEST_COLOR));
        assertFalse(object2.getColor().equals(TEST_COLOR));
        assertEquals(store.paintWorld().length, 2);
    }

    public void testAddCrossInteraction() {
        PaintWorldStore.setCanvasDimensions(TEST_CANVAS_DIMENSIONS_X + "", TEST_CANVAS_DIMENSIONS_Y + "");
        PaintWorldStore store = new PaintWorldStore();

        PaintObject object1 = createTestBall(0, 0, "add_cross");
        PaintObject object2 = createTestBall(1, 1, "add_cross");
        store.getSupport().addPropertyChangeListener(PaintWorldStore.EVENT_FIELD, object1);
        store.getSupport().addPropertyChangeListener(PaintWorldStore.EVENT_FIELD, object2);
        store.updateWorld();

        assertTrue(object1.getHasCross());
        assertTrue(object2.getHasCross());
        assertEquals(store.paintWorld().length, 2);
    }

    public void testFishConvertInteraction() {
        PaintWorldStore.setCanvasDimensions(TEST_CANVAS_DIMENSIONS_X + "", TEST_CANVAS_DIMENSIONS_Y + "");
        PaintWorldStore store = new PaintWorldStore();

        PaintObject object1 = createTestBall(0, 0, "fish_convert");
        PaintObject object2 = createTestBall(1, 1, "fish_convert");
        store.getSupport().addPropertyChangeListener(PaintWorldStore.EVENT_FIELD, object1);
        store.getSupport().addPropertyChangeListener(PaintWorldStore.EVENT_FIELD, object2);
        store.updateWorld();

        assertTrue(object1.isBall());
        assertEquals(object1.getType(), "ball");
        assertEquals(object1.getSize(), TEST_SIZE);

        assertFalse(object2.isBall());
        assertEquals(object2.getType(), "fish");
        assertEquals(object2.getSize(), PaintObjectFactory.FISH_SIZE);

        assertEquals(store.paintWorld().length, 2);
    }

    public void testChangeDirectionInteraction() {
        PaintWorldStore.setCanvasDimensions(TEST_CANVAS_DIMENSIONS_X + "", TEST_CANVAS_DIMENSIONS_Y + "");
        PaintWorldStore store = new PaintWorldStore();

        PaintObject object1 = createTestBall(0, 0, "change_direction");
        PaintObject object2 = createTestBall(1, 1, "change_direction");
        store.getSupport().addPropertyChangeListener(PaintWorldStore.EVENT_FIELD, object1);
        store.getSupport().addPropertyChangeListener(PaintWorldStore.EVENT_FIELD, object2);
        store.updateWorld();

        // assertEquals(object1.getVelocity().x, TEST_VELOCITY_X);
        // assertEquals(object1.getVelocity().y, -TEST_VELOCITY_Y);
        assertEquals(object2.getVelocity().x, -TEST_VELOCITY_X);
        assertEquals(object2.getVelocity().y, -TEST_VELOCITY_Y);

        assertEquals(store.paintWorld().length, 2);
    }

    public void testEatInteraction() {
        PaintWorldStore.setCanvasDimensions(TEST_CANVAS_DIMENSIONS_X + "", TEST_CANVAS_DIMENSIONS_Y + "");
        PaintWorldStore store = new PaintWorldStore();

        PaintObject object1 = createTestBall(0, 0, "eat");
        PaintObject object2 = createTestBall(1, 1, "eat");
        store.getSupport().addPropertyChangeListener(PaintWorldStore.EVENT_FIELD, object1);
        store.getSupport().addPropertyChangeListener(PaintWorldStore.EVENT_FIELD, object2);
        store.updateWorld();

        assertTrue(object1.getSize() > TEST_SIZE);
        assertEquals(object2.getSize(), 0);
        assertTrue(object2.isEmpty());
        assertFalse(object2.isBall());

        assertEquals(store.paintWorld().length, 1);
    }

    public void testDestroyInteraction() {
        PaintWorldStore.setCanvasDimensions(TEST_CANVAS_DIMENSIONS_X + "", TEST_CANVAS_DIMENSIONS_Y + "");
        PaintWorldStore store = new PaintWorldStore();

        PaintObject object1 = createTestBall(0, 0, "destroy");
        PaintObject object2 = createTestBall(1, 1, "destroy");
        store.getSupport().addPropertyChangeListener(PaintWorldStore.EVENT_FIELD, object1);
        store.getSupport().addPropertyChangeListener(PaintWorldStore.EVENT_FIELD, object2);
        store.updateWorld();

        assertEquals(object2.getSize(), 0);
        assertTrue(object2.isEmpty());
        assertFalse(object2.isBall());

        assertEquals(store.paintWorld().length, 1);
    }

    public void testStuckInteraction() {
        PaintWorldStore.setCanvasDimensions(TEST_CANVAS_DIMENSIONS_X + "", TEST_CANVAS_DIMENSIONS_Y + "");
        PaintWorldStore store = new PaintWorldStore();

        PaintObject object1 = createTestBall(0, 0, "stuck");
        PaintObject object2 = createTestBall(1, 1, "stuck");
        store.getSupport().addPropertyChangeListener(PaintWorldStore.EVENT_FIELD, object1);
        store.getSupport().addPropertyChangeListener(PaintWorldStore.EVENT_FIELD, object2);
        store.updateWorld();

        assertTrue(object1.getUpdateStrategy() instanceof NullUpdateStrategy);
        assertTrue(object2.getUpdateStrategy() instanceof NullUpdateStrategy);

        assertEquals(store.paintWorld().length, 2);
    }

    public void testTranslateInteraction() {
        PaintWorldStore.setCanvasDimensions(TEST_CANVAS_DIMENSIONS_X + "", TEST_CANVAS_DIMENSIONS_Y + "");
        PaintWorldStore store = new PaintWorldStore();

        PaintObject object1 = createTestBall(0, 0, "translate");
        PaintObject object2 = createTestBall(1, 1, "translate");
        store.getSupport().addPropertyChangeListener(PaintWorldStore.EVENT_FIELD, object1);
        store.getSupport().addPropertyChangeListener(PaintWorldStore.EVENT_FIELD, object2);
        store.updateWorld();

        assertEquals(object1.getLocation().x, TEST_LOCATION_X);
        assertEquals(object1.getLocation().y, TEST_LOCATION_Y);
        assertEquals(object2.getLocation().x, TEST_CANVAS_DIMENSIONS_X - TEST_LOCATION_X);
        assertEquals(object2.getLocation().y, TEST_CANVAS_DIMENSIONS_Y - TEST_LOCATION_Y - 1);

        assertEquals(store.paintWorld().length, 2);
    }

    private PaintObject createTestBall(int deltaX, int deltaY, String collisionStrategy) {
        PaintObject object = new PaintObject(TEST_TYPE, new Point(TEST_LOCATION_X + deltaX, TEST_LOCATION_Y + deltaY), new Point(TEST_VELOCITY_X, TEST_VELOCITY_Y), TEST_SIZE, TEST_COLOR);

        object.setUpdateStrategy(STRATEGY_FACTORY.makeUpdateStrategy("horizontal"));
        object.setCollisionStrategy(STRATEGY_FACTORY.makeCollisionStrategy(collisionStrategy));

        return object;
    }

}
