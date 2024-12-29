package edu.rice.comp504.model;

import edu.rice.comp504.model.paintobject.PaintObject;
import edu.rice.comp504.model.strategy.collision.ShrinkStrategy;
import edu.rice.comp504.model.strategy.update.HorizontalStrategy;
import edu.rice.comp504.model.strategy.update.VerticalStrategy;
import junit.framework.TestCase;

import java.awt.*;
import java.beans.PropertyChangeListener;

public class PaintWorldStoreTest extends TestCase {

    private final int TEST_CANVAS_DIMENSIONS_X = 600;

    private final int TEST_CANVAS_DIMENSIONS_Y = 300;

    public void testLoadObject() {
        PaintWorldStore.setCanvasDimensions(TEST_CANVAS_DIMENSIONS_X + "", TEST_CANVAS_DIMENSIONS_Y + "");
        PaintWorldStore store = new PaintWorldStore();
        DispatchAdapter adapter = new DispatchAdapter(store);

        adapter.loadObject("ball", "horizontal", "false", "shrink");
        PropertyChangeListener[] world = store.paintWorld();
        assertEquals(world.length, 1);
        PaintObject object = (PaintObject) world[0];
        assertTrue(object.isBall());
        assertEquals(object.getType(), "ball");
        assertTrue(object.getUpdateStrategy() instanceof HorizontalStrategy);
        assertFalse(object.isStrategySwitchable());
        assertTrue(object.getCollisionStrategy() instanceof ShrinkStrategy);
    }

    public void testRemoveObjects() {
        PaintWorldStore.setCanvasDimensions(TEST_CANVAS_DIMENSIONS_X + "", TEST_CANVAS_DIMENSIONS_Y + "");
        PaintWorldStore store = new PaintWorldStore();
        DispatchAdapter adapter = new DispatchAdapter(store);

        adapter.loadObject("ball", "horizontal", "false", "shrink");
        adapter.removeObjects("horizontal");
        PropertyChangeListener[] world = store.paintWorld();
        assertEquals(world.length, 0);
    }

    public void testSwitchStrategies() {
        PaintWorldStore.setCanvasDimensions(TEST_CANVAS_DIMENSIONS_X + "", TEST_CANVAS_DIMENSIONS_Y + "");
        PaintWorldStore store = new PaintWorldStore();
        DispatchAdapter adapter = new DispatchAdapter(store);

        adapter.loadObject("ball", "horizontal", "false", "shrink");
        adapter.loadObject("ball", "horizontal", "true", "shrink");
        adapter.switchStrategies("horizontal", "vertical");
        PropertyChangeListener[] world = store.paintWorld();
        assertEquals(world.length, 2);
        PaintObject object1 = (PaintObject) world[0], object2 = (PaintObject) world[1];
        assertTrue(object1.getUpdateStrategy() instanceof HorizontalStrategy);
        assertTrue(object2.getUpdateStrategy() instanceof VerticalStrategy);
    }

    public void testUpdateWorld() {
        PaintWorldStore.setCanvasDimensions(TEST_CANVAS_DIMENSIONS_X + "", TEST_CANVAS_DIMENSIONS_Y + "");
        PaintWorldStore store = new PaintWorldStore();
        DispatchAdapter adapter = new DispatchAdapter(store);

        adapter.loadObject("ball", "horizontal", "false", "shrink");
        PropertyChangeListener[] world = store.paintWorld();
        assertEquals(world.length, 1);
        PaintObject object = (PaintObject) world[0];
        Point initialLocation = new Point(object.getLocation().x, object.getLocation().y);

        world = adapter.updateWorld();
        assertEquals(world.length, 1);
        object = (PaintObject) world[0];

        assertEquals(object.getLocation().x, initialLocation.x + object.getVelocity().x);
        assertEquals(object.getLocation().y, initialLocation.y);
    }

    public void testClearWorld() {
        PaintWorldStore.setCanvasDimensions(TEST_CANVAS_DIMENSIONS_X + "", TEST_CANVAS_DIMENSIONS_Y + "");
        PaintWorldStore store = new PaintWorldStore();
        DispatchAdapter adapter = new DispatchAdapter(store);

        adapter.loadObject("ball", "horizontal", "false", "shrink");
        adapter.clearWorld();
        PropertyChangeListener[] world = store.paintWorld();
        assertEquals(world.length, 0);
    }

    public void testRemoveObject() {
        PaintWorldStore.setCanvasDimensions(TEST_CANVAS_DIMENSIONS_X + "", TEST_CANVAS_DIMENSIONS_Y + "");
        PaintWorldStore store = new PaintWorldStore();
        DispatchAdapter adapter = new DispatchAdapter(store);

        adapter.loadObject("ball", "rotation", "false", "shrink");
        PropertyChangeListener[] world = store.paintWorld();
        assertEquals(world.length, 1);
        PaintObject object = (PaintObject) world[0];

        store.removeObject(object);
        world = store.paintWorld();
        assertEquals(world.length, 0);
    }

}
