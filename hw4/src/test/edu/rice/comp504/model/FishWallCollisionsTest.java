package edu.rice.comp504.model;

import edu.rice.comp504.model.paintobject.PaintObject;
import edu.rice.comp504.model.strategy.IStrategyFactory;
import edu.rice.comp504.model.strategy.StrategyFactory;
import junit.framework.TestCase;

import java.awt.*;

public class FishWallCollisionsTest extends TestCase {

    private final IStrategyFactory STRATEGY_FACTORY = new StrategyFactory();

    private final int TEST_CANVAS_DIMENSIONS_X = 600;

    private final int TEST_CANVAS_DIMENSIONS_Y = 300;

    private final String TEST_TYPE = "fish";
    private final int TEST_LOCATION_X = 0;
    private final int TEST_LOCATION_Y = 0;
    private final int TEST_VELOCITY_X = 1;
    private final int TEST_VELOCITY_Y = 1;
    private final int TEST_SIZE = 10;
    private final String TEST_COLOR = "red";


    public void testFishDirectionChange() {
        PaintWorldStore.setCanvasDimensions(TEST_CANVAS_DIMENSIONS_X + "", TEST_CANVAS_DIMENSIONS_Y + "");
        PaintWorldStore store = new PaintWorldStore();

        store.clearWorld();
        PaintObject object = createTestFish(TEST_CANVAS_DIMENSIONS_X, 50, "horizontal");
        store.getSupport().addPropertyChangeListener(PaintWorldStore.EVENT_FIELD, object);
        store.updateWorld();

        assertTrue(object.getVelocity().x < 0);
        assertTrue(object.getVelocity().y > 0);
        assertEquals(object.getAngle(), 0.0, 0.05);

        store.clearWorld();
        object = createTestFish(0, 50, "horizontal");
        object.invertVelX();
        store.getSupport().addPropertyChangeListener(PaintWorldStore.EVENT_FIELD, object);
        store.updateWorld();

        assertTrue(object.getVelocity().x > 0);
        assertTrue(object.getVelocity().y > 0);
        assertEquals(object.getAngle(), 0.0, 0.05);

        store.clearWorld();
        object = createTestFish(50, TEST_CANVAS_DIMENSIONS_Y, "vertical");
        store.getSupport().addPropertyChangeListener(PaintWorldStore.EVENT_FIELD, object);
        store.updateWorld();

        assertTrue(object.getVelocity().x > 0);
        assertTrue(object.getVelocity().y < 0);
        assertEquals(object.getAngle(), -90.0, 0.05);

        store.clearWorld();
        object = createTestFish(50, 0, "vertical");
        object.invertVelY();
        store.getSupport().addPropertyChangeListener(PaintWorldStore.EVENT_FIELD, object);
        store.updateWorld();

        assertTrue(object.getVelocity().x > 0);
        assertTrue(object.getVelocity().y > 0);
        assertEquals(object.getAngle(), 90.0, 0.05);
    }

    private PaintObject createTestFish(int deltaX, int deltaY, String updateStrategy) {
        PaintObject object = new PaintObject(TEST_TYPE, new Point(TEST_LOCATION_X + deltaX, TEST_LOCATION_Y + deltaY), new Point(TEST_VELOCITY_X, TEST_VELOCITY_Y), TEST_SIZE, TEST_COLOR);

        object.setUpdateStrategy(STRATEGY_FACTORY.makeUpdateStrategy(updateStrategy));

        return object;
    }

}
