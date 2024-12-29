package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.strategy.collision.*;
import edu.rice.comp504.model.strategy.update.*;

public class StrategyFactory implements IStrategyFactory {

    @Override
    public IUpdateStrategy makeUpdateStrategy(String type) {
        switch (type) {
            case "horizontal":
                return HorizontalStrategy.getOnly();
            case "vertical":
                return VerticalStrategy.getOnly();
            case "diagonal":
                return DiagonalStrategy.getOnly();
            case "rotation":
                return RotationStrategy.getOnly();
            case "stairs":
                return StairsStrategy.getOnly();
            case "waterfall":
                return WaterfallStrategy.getOnly();
            case "pulse":
                return PulseStrategy.getOnly();
            case "zig_zag":
                return ZigZagStrategy.getOnly();
            case "inverted_squares":
                return InvertedSquaresStrategy.getOnly();
            case "inverted_trapezoids":
                return InvertedTrapezoidsStrategy.getOnly();
            case "square":
                return SquareStrategy.getOnly();
            case "rhombus":
                return RhombusStrategy.getOnly();
            default:
                return NullUpdateStrategy.getOnly();
        }
    }

    @Override
    public ICollisionStrategy makeCollisionStrategy(String type) {
        switch (type) {
            case "shrink":
                return ShrinkStrategy.getOnly();
            case "fade":
                return FadeStrategy.getOnly();
            case "change_color":
                return ChangeColorStrategy.getOnly();
            case "add_cross":
                return AddCrossStrategy.getOnly();
            case "fish_convert":
                return FishConvertStrategy.getOnly();
            case "change_direction":
                return ChangeDirectionStrategy.getOnly();
            case "eat":
                return EatStrategy.getOnly();
            case "destroy":
                return DestroyStrategy.getOnly();
            case "stuck":
                return StuckStrategy.getOnly();
            case "translate":
                return TranslateStrategy.getOnly();
            default:
                return NullCollisionStrategy.getOnly();
        }
    }
}
