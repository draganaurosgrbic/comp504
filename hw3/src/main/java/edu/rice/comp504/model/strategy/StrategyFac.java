package edu.rice.comp504.model.strategy;

public class StrategyFac implements IStrategyFac {

    @Override
    public IUpdateStrategy make(String type) {
        switch (type) {
            case "horizontal":
                return HorizontalStrategy.getOnly();
            case "vertical":
                return VerticalStrategy.getOnly();
            case "diagonal":
                return DiagonalStrategy.getOnly();
            case "rotating":
                return RotatingStrategy.getOnly();

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
                return null;
        }
    }
}
