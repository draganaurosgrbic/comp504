package edu.rice.comp504.model;

import edu.rice.comp504.model.paintobject.PaintObject;
import edu.rice.comp504.model.strategy.update.*;
import junit.framework.TestCase;

import java.awt.*;

public class UpdateStrategyTest extends TestCase {

    private final int TEST_CANVAS_DIMENSIONS_X = 1000;

    private final int TEST_CANVAS_DIMENSIONS_Y = 1000;

    private final String TEST_TYPE = "ball";
    private final int TEST_LOCATION_X = TEST_CANVAS_DIMENSIONS_X / 2;
    private final int TEST_LOCATION_Y = TEST_CANVAS_DIMENSIONS_Y / 2;
    private final int TEST_VELOCITY_X = 10;
    private final int TEST_VELOCITY_Y = 10;
    private final int TEST_SIZE = 10;
    private final String TEST_COLOR = "red";

    private final int STATE_CHANGE_INTERVAL = 10;


    public void testGetName() {
        assertEquals(HorizontalStrategy.getOnly().getName(), "horizontal");
        assertEquals(VerticalStrategy.getOnly().getName(), "vertical");
        assertEquals(DiagonalStrategy.getOnly().getName(), "diagonal");
        assertEquals(RotationStrategy.getOnly().getName(), "rotation");
        assertEquals(StairsStrategy.getOnly().getName(), "stairs");
        assertEquals(WaterfallStrategy.getOnly().getName(), "waterfall");
        assertEquals(PulseStrategy.getOnly().getName(), "pulse");
        assertEquals(ZigZagStrategy.getOnly().getName(), "zig_zag");
        assertEquals(InvertedSquaresStrategy.getOnly().getName(), "inverted_squares");
        assertEquals(InvertedTrapezoidsStrategy.getOnly().getName(), "inverted_trapezoids");
        assertEquals(SquareStrategy.getOnly().getName(), "square");
        assertEquals(RhombusStrategy.getOnly().getName(), "rhombus");
        assertEquals(NullUpdateStrategy.getOnly().getName(), "null");
    }

    public void testUpdateState() {
        PaintWorldStore.setCanvasDimensions(TEST_CANVAS_DIMENSIONS_X + "", TEST_CANVAS_DIMENSIONS_Y + "");
        Point dimensions = PaintWorldStore.getCanvasDimensions();

        PaintObject object = createTestObject();
        HorizontalStrategy.getOnly().updateState(object);
        assertEquals(object.getLocation().x, TEST_LOCATION_X + TEST_VELOCITY_X);
        assertEquals(object.getLocation().y, TEST_LOCATION_Y);

        object = createTestObject();
        VerticalStrategy.getOnly().updateState(object);
        assertEquals(object.getLocation().x, TEST_LOCATION_X);
        assertEquals(object.getLocation().y, TEST_LOCATION_Y + TEST_VELOCITY_Y);

        object = createTestObject();
        DiagonalStrategy.getOnly().updateState(object);
        assertEquals(object.getLocation().x, TEST_LOCATION_X + TEST_VELOCITY_X);
        assertEquals(object.getLocation().y, TEST_LOCATION_Y + TEST_VELOCITY_Y);

        object = createTestObject();
        RotationStrategy.getOnly().updateState(object);
        assertEquals(object.getLocation().x, dimensions.x / 2 + dimensions.y / 4);
        assertEquals(object.getLocation().y, dimensions.y / 2);

        object = createTestObject();
        for (int i = 0; i < STATE_CHANGE_INTERVAL; ++i) {
            StairsStrategy.getOnly().updateState(object);
            assertEquals(object.getLocation().x, TEST_LOCATION_X + (i + 1) * TEST_VELOCITY_X);
            assertEquals(object.getLocation().y, TEST_LOCATION_Y);
        }
        for (int i = 0; i < STATE_CHANGE_INTERVAL; ++i) {
            StairsStrategy.getOnly().updateState(object);
            assertEquals(object.getLocation().x, TEST_LOCATION_X + STATE_CHANGE_INTERVAL * TEST_VELOCITY_X);
            assertEquals(object.getLocation().y, TEST_LOCATION_Y + (i + 1) * TEST_VELOCITY_Y);
        }

        object = createTestObject();
        for (int i = 0; i < STATE_CHANGE_INTERVAL; ++i) {
            WaterfallStrategy.getOnly().updateState(object);
            assertEquals(object.getLocation().x, TEST_LOCATION_X + (i + 1) * TEST_VELOCITY_X);
            assertEquals(object.getLocation().y, TEST_LOCATION_Y + (i + 1) * TEST_VELOCITY_Y);
        }
        for (int i = 0; i < STATE_CHANGE_INTERVAL; ++i) {
            WaterfallStrategy.getOnly().updateState(object);
            assertEquals(object.getLocation().x, TEST_LOCATION_X + STATE_CHANGE_INTERVAL * TEST_VELOCITY_X);
            assertEquals(object.getLocation().y, TEST_LOCATION_Y + STATE_CHANGE_INTERVAL * TEST_VELOCITY_Y + (i + 1) * TEST_VELOCITY_Y);
        }

        object = createTestObject();
        for (int i = 0; i < STATE_CHANGE_INTERVAL; ++i) {
            PulseStrategy.getOnly().updateState(object);
            assertEquals(object.getLocation().x, TEST_LOCATION_X + (i + 1) * TEST_VELOCITY_X);
            assertEquals(object.getLocation().y, TEST_LOCATION_Y + (i + 1) * TEST_VELOCITY_Y);
        }
        for (int i = 0; i < STATE_CHANGE_INTERVAL; ++i) {
            PulseStrategy.getOnly().updateState(object);
            assertEquals(object.getLocation().x, TEST_LOCATION_X + STATE_CHANGE_INTERVAL * TEST_VELOCITY_X);
            assertEquals(object.getLocation().y, TEST_LOCATION_Y + STATE_CHANGE_INTERVAL * TEST_VELOCITY_Y - (i + 1) * TEST_VELOCITY_Y);
        }

        object = createTestObject();
        for (int i = 0; i < STATE_CHANGE_INTERVAL; ++i) {
            ZigZagStrategy.getOnly().updateState(object);
            assertEquals(object.getLocation().x, TEST_LOCATION_X + (i + 1) * TEST_VELOCITY_X);
            assertEquals(object.getLocation().y, TEST_LOCATION_Y + (i + 1) * TEST_VELOCITY_Y);
        }

        object = createTestObject();
        for (int i = 0; i < STATE_CHANGE_INTERVAL; ++i) {
            InvertedSquaresStrategy.getOnly().updateState(object);
            assertEquals(object.getLocation().x, TEST_LOCATION_X + (i + 1) * TEST_VELOCITY_X);
            assertEquals(object.getLocation().y, TEST_LOCATION_Y);
        }
        for (int i = 0; i < STATE_CHANGE_INTERVAL; ++i) {
            InvertedSquaresStrategy.getOnly().updateState(object);
            assertEquals(object.getLocation().x, TEST_LOCATION_X + STATE_CHANGE_INTERVAL * TEST_VELOCITY_X);
            assertEquals(object.getLocation().y, TEST_LOCATION_Y + (i + 1) * TEST_VELOCITY_Y);
        }

        object = createTestObject();
        for (int i = 0; i < STATE_CHANGE_INTERVAL; ++i) {
            InvertedTrapezoidsStrategy.getOnly().updateState(object);
            assertEquals(object.getLocation().x, TEST_LOCATION_X + (i + 1) * TEST_VELOCITY_X);
            assertEquals(object.getLocation().y, TEST_LOCATION_Y + (i + 1) * TEST_VELOCITY_Y);
        }
        for (int i = 0; i < STATE_CHANGE_INTERVAL; ++i) {
            InvertedTrapezoidsStrategy.getOnly().updateState(object);
            assertEquals(object.getLocation().x, TEST_LOCATION_X + STATE_CHANGE_INTERVAL * TEST_VELOCITY_X + (i + 1) * TEST_VELOCITY_X);
            assertEquals(object.getLocation().y, TEST_LOCATION_Y + STATE_CHANGE_INTERVAL * TEST_VELOCITY_Y);
        }

        object = createTestObject();
        for (int i = 0; i < STATE_CHANGE_INTERVAL; ++i) {
            SquareStrategy.getOnly().updateState(object);
            assertEquals(object.getLocation().x, TEST_LOCATION_X + (i + 1) * TEST_VELOCITY_X);
            assertEquals(object.getLocation().y, TEST_LOCATION_Y);
        }
        for (int i = 0; i < STATE_CHANGE_INTERVAL; ++i) {
            SquareStrategy.getOnly().updateState(object);
            assertEquals(object.getLocation().x, TEST_LOCATION_X + STATE_CHANGE_INTERVAL * TEST_VELOCITY_X);
            assertEquals(object.getLocation().y, TEST_LOCATION_Y - (i + 1) * TEST_VELOCITY_Y);
        }

        object = createTestObject();
        for (int i = 0; i < STATE_CHANGE_INTERVAL; ++i) {
            RhombusStrategy.getOnly().updateState(object);
            assertEquals(object.getLocation().x, TEST_LOCATION_X + (i + 1) * TEST_VELOCITY_X);
            assertEquals(object.getLocation().y, TEST_LOCATION_Y + (i + 1) * TEST_VELOCITY_Y);
        }
        for (int i = 0; i < STATE_CHANGE_INTERVAL; ++i) {
            RhombusStrategy.getOnly().updateState(object);
            assertEquals(object.getLocation().x, TEST_LOCATION_X + STATE_CHANGE_INTERVAL * TEST_VELOCITY_X + (i + 1) * TEST_VELOCITY_X);
            assertEquals(object.getLocation().y, TEST_LOCATION_Y + STATE_CHANGE_INTERVAL * TEST_VELOCITY_Y - (i + 1) * TEST_VELOCITY_Y);
        }
        for (int i = 0; i < STATE_CHANGE_INTERVAL; ++i) {
            RhombusStrategy.getOnly().updateState(object);
            assertEquals(object.getLocation().x, TEST_LOCATION_X + 2 * STATE_CHANGE_INTERVAL * TEST_VELOCITY_X - (i + 1) * TEST_VELOCITY_X);
            assertEquals(object.getLocation().y, TEST_LOCATION_Y - (i + 1) * TEST_VELOCITY_Y);
        }
        for (int i = 0; i < STATE_CHANGE_INTERVAL; ++i) {
            RhombusStrategy.getOnly().updateState(object);
            assertEquals(object.getLocation().x, TEST_LOCATION_X + STATE_CHANGE_INTERVAL * TEST_VELOCITY_X - (i + 1) * TEST_VELOCITY_X);
            assertEquals(object.getLocation().y, TEST_LOCATION_Y - STATE_CHANGE_INTERVAL * TEST_VELOCITY_Y + (i + 1) * TEST_VELOCITY_Y);
        }

        object = createTestObject();
        NullUpdateStrategy.getOnly().updateState(object);
        assertEquals(object.getLocation().x, TEST_LOCATION_X);
        assertEquals(object.getLocation().y, TEST_LOCATION_Y);
    }

    private PaintObject createTestObject() {
        return new PaintObject(TEST_TYPE, new Point(TEST_LOCATION_X, TEST_LOCATION_Y), new Point(TEST_VELOCITY_X, TEST_VELOCITY_Y), TEST_SIZE, TEST_COLOR);
    }

}
