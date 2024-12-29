package edu.rice.comp504.model;

import edu.rice.comp504.model.strategy.IStrategyFactory;
import edu.rice.comp504.model.strategy.StrategyFactory;
import edu.rice.comp504.model.strategy.collision.*;
import edu.rice.comp504.model.strategy.update.*;
import junit.framework.TestCase;

public class StrategyFactoryTest extends TestCase {

    public void testMakeUpdateStrategy() {
        IStrategyFactory factory = new StrategyFactory();

        assertTrue(factory.makeUpdateStrategy("horizontal") instanceof HorizontalStrategy);
        assertTrue(factory.makeUpdateStrategy("vertical") instanceof VerticalStrategy);
        assertTrue(factory.makeUpdateStrategy("diagonal") instanceof DiagonalStrategy);
        assertTrue(factory.makeUpdateStrategy("rotation") instanceof RotationStrategy);
        assertTrue(factory.makeUpdateStrategy("stairs") instanceof StairsStrategy);
        assertTrue(factory.makeUpdateStrategy("waterfall") instanceof WaterfallStrategy);
        assertTrue(factory.makeUpdateStrategy("pulse") instanceof PulseStrategy);
        assertTrue(factory.makeUpdateStrategy("zig_zag") instanceof ZigZagStrategy);
        assertTrue(factory.makeUpdateStrategy("inverted_squares") instanceof InvertedSquaresStrategy);
        assertTrue(factory.makeUpdateStrategy("inverted_trapezoids") instanceof InvertedTrapezoidsStrategy);
        assertTrue(factory.makeUpdateStrategy("square") instanceof SquareStrategy);
        assertTrue(factory.makeUpdateStrategy("rhombus") instanceof RhombusStrategy);
        assertTrue(factory.makeUpdateStrategy("unknown") instanceof NullUpdateStrategy);
    }

    public void testMakeCollisionStrategy() {
        IStrategyFactory factory = new StrategyFactory();

        assertTrue(factory.makeCollisionStrategy("shrink") instanceof ShrinkStrategy);
        assertTrue(factory.makeCollisionStrategy("fade") instanceof FadeStrategy);
        assertTrue(factory.makeCollisionStrategy("change_color") instanceof ChangeColorStrategy);
        assertTrue(factory.makeCollisionStrategy("add_cross") instanceof AddCrossStrategy);
        assertTrue(factory.makeCollisionStrategy("fish_convert") instanceof FishConvertStrategy);
        assertTrue(factory.makeCollisionStrategy("change_direction") instanceof ChangeDirectionStrategy);
        assertTrue(factory.makeCollisionStrategy("eat") instanceof EatStrategy);
        assertTrue(factory.makeCollisionStrategy("destroy") instanceof DestroyStrategy);
        assertTrue(factory.makeCollisionStrategy("stuck") instanceof StuckStrategy);
        assertTrue(factory.makeCollisionStrategy("translate") instanceof TranslateStrategy);
        assertTrue(factory.makeCollisionStrategy("unknown") instanceof NullCollisionStrategy);
    }

}
