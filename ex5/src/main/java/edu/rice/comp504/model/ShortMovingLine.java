package edu.rice.comp504.model;

import java.awt.*;

/**
 * The short moving line.
 */
public class ShortMovingLine extends MovingLine {
    private static ShortMovingLine ONLY = null;
    /**
     * Constructor.
     */
    private ShortMovingLine(Point start, Point end, Point vel) {
        super(start, end, vel);
    }

    /**
     * Make a singleton short moving line.
     * @return The short moving line
     */
    public static ShortMovingLine make() {
        if (ONLY == null) {
            Point vel = new Point(getRnd(5, 10), getRnd(5, 10));
            Point startLine = new Point(0, 0);
            Point endLine = new Point(200, 100);
            ONLY = new ShortMovingLine(startLine, endLine, vel);
        }
        return ONLY;
    }

    /**
     * Reset the line.
     * @return A null line
     */
    public static ShortMovingLine reset() {
        ONLY = null;
        return ONLY;
    }

}
