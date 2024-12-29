package edu.rice.comp504.model;

import edu.rice.comp504.model.cmd.ILineCmd;
import edu.rice.comp504.model.strategy.CompositeStrategy;
import edu.rice.comp504.model.strategy.HorizontalStrategy;
import edu.rice.comp504.model.strategy.IUpdateStrategy;
import edu.rice.comp504.model.strategy.VerticalStrategy;

import java.awt.*;


/**
 * Represents a line that moves.
 */
public class MovingLine {
    private Point startLine;
    private Point endLine;
    private Point vel;
    private IUpdateStrategy strategy;

    /**
     * Constructor.
     * @param startLine  The start location for the moving line
     * @param endLine The end location for the moving line
     * @param vel The moving line velocity
     */
    public MovingLine(Point startLine, Point endLine, Point vel) {
        this.startLine = startLine;
        this.endLine = endLine;
        this.vel = vel;
        this.strategy = LineStore.getOnlyStratFac().make();
    }


    /**
     * Get the line velocity.
     * @return The line velocity
     */
    public Point getVelocity() {
        return vel;
    }

    /**
     * Get the line strategy.
     * @return The line strategy
     */
    public IUpdateStrategy getStrategy() {
        return strategy;
    }

    /**
     * Set the new strategy.
     * @param strategy The new strategy.
     */
    public void setStrategy(IUpdateStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Get the line left end location.
     * @return The start of the line
     */
    public Point getStartLine() {
        return startLine;
    }

    /**
     * Get the line right end location.
     * @return The end of the line
     */
    public Point getEndLine() {
        return endLine;
    }

    /**
     * Set the new line location.
     * @param vel  The velocity (x,y) used to change the line location
     */
    public void nextLocation(Point vel) {
        this.startLine.translate((int) vel.getX(), (int) vel.getY());
        this.endLine.translate((int) vel.getX(), (int) vel.getY());
    }

    /**
     * Generate a random number.
     * @param base  The mininum value
     * @param limit The maximum number from the base
     * @return A randomly generated number
     */
    public static int getRnd(int base, int limit) {
        return (int)Math.floor(Math.random() * limit + base);
    }

    /**
     * Update the line location using the strategy.
     */
    public void update() {
        this.strategy.updateState(this);
    }


}
