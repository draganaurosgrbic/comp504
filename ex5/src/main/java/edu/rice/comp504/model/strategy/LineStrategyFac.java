package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.MovingLine;

/**
 * Factory that creates line strategies.
 */
public class LineStrategyFac implements IStrategyFac {

    /**
     * Constructor.
     */
    public LineStrategyFac() {

    }

    @Override
    public IUpdateStrategy make() {
        //randomly select initial strategy
        int sel = (int) Math.floor(Math.random() * 3);

        switch (sel) {
            case 0:
                return HorizontalStrategy.make();
            case 1:
                return VerticalStrategy.make();
            default:
                return CompositeStrategy.make();
        }
    }

    /**
     * Switch the strategy.
     * @param context The current line.
     * @return The new line strategy for the line
     */
    public IUpdateStrategy switchStrategy(MovingLine context) {
        switch (context.getStrategy().getName()) {
            case "horizontal": return VerticalStrategy.make();
            case "vertical": return CompositeStrategy.make();
            case "composite": return HorizontalStrategy.make();
            default: return VerticalStrategy.make();
        }
    }
}
