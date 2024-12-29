package edu.rice.comp504.model;

import java.awt.*;

/**
 * The long moving line.
 */
public class LongMovingLine extends MovingLine {
    private static LongMovingLine ONLY = null;
    /**
     * Constructor.
     */
    private LongMovingLine(Point start, Point end, Point vel) {
        super(start, end, vel);
    }

    /**
     * Make a singleton long moving line.
     * @return The long moving line
     */
    public static LongMovingLine make() {
        if (ONLY == null) {
            Point vel = new Point(getRnd(5, 15), getRnd(5, 10));
            Point startLine = new Point(0, 0);
            Point endLine = new Point(400, 200);
            ONLY = new LongMovingLine(startLine, endLine, vel);
        }
        return ONLY;
    }

    /**
     * Reset the line.
     * @return A null line
     */
    public static LongMovingLine reset() {
        ONLY = null;
        return ONLY;
    }
}
