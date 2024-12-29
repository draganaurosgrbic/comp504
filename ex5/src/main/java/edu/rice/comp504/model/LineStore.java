package edu.rice.comp504.model;

import edu.rice.comp504.model.cmd.ILineCmd;
import edu.rice.comp504.model.strategy.LineStrategyFac;

/**
 * Stores the short and long moving lines.
 */
public class LineStore  {
    private ShortMovingLine sml;
    private LongMovingLine lml;
    private static LineStrategyFac ONLY;

    /**
     * Constructor.
     */
    public LineStore() {
    }

    /**
     * Get the singleton strategy factory.
     * @return The strategy factory
     */
    public static LineStrategyFac getOnlyStratFac() {
        if (ONLY == null) {
            ONLY = new LineStrategyFac();
        }
        return ONLY;
    }
    /**
     * Ensure that receives get the command.
     * @param cmd The command to pass to receivers
     */
    public void sendRecvCmd(ILineCmd cmd) {
        cmd.execute(sml);
        cmd.execute(lml);
    }

    /**
     * Reset the lines to null so that they no longer appear on the canvas.
     */
    public void reset() {
        sml = ShortMovingLine.reset();
        lml = LongMovingLine.reset();
    }

    /**
     * Make a new line.
     * @param type  The type of line to draw. This will either be a long or short line.
     */
    public MovingLine makeLine(String type) {
        if (type.equals("short")) {
            sml = ShortMovingLine.make();
            return sml;
        } else if (type.equals("long")) {
            lml = LongMovingLine.make();
            return lml;
        }
        return null;
    }
}
