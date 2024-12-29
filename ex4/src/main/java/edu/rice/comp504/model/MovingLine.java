package edu.rice.comp504.model;

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
     */
    public MovingLine() {
        init();
    }

    /**
     * Get the line velocity.
     * @return The line velocity
     */
    public Point getVelocity() {
        return vel;
    }

    /**
     * Get the line left end location.
     * @return The start of the line
     */
    public Point getStartLine() {
        return startLine;
    }

    /**
     * Set the line left end location.
     * @param sl  The new left end of the line.
     */
    public void setStartLine(Point sl) {
        this.startLine = sl;
    }

    /**
     * Get the line right end location.
     * @return The end of the line
     */
    public Point getEndLine() {
        return endLine;
    }

    /**
     * Set the line right end location.
     * @param el The new right end of the line.
     */
    public void setEndLine(Point el) {
        this.endLine = el;
    }

    /**
     * Set the new strategy.
     * @param strategy The new strategy.
     */
    public void setStrategy(IUpdateStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Generate a random number.
     * @param base  The mininum value
     * @param limit The maximum number from the base
     * @return A randomly generated number
     */
    private int getRnd(int base, int limit) {
        return (int)Math.floor(Math.random() * limit + base);
    }

    /**
     * initialize the moving line.
     */
    public void init() {
        this.vel = new Point(getRnd(10, 20), getRnd(10, 20));
        this.startLine = new Point(0, 0);
        this.endLine = new Point(200, 100);

        this.strategy = HorizontalStrategy.make();
    }

    /**
     * Update the line location using the strategy.
     */
    public void update() {
        this.strategy.updateState(this);
    }

    /**
     *  Switch the strategy.
     */
    public void switchStrategy() {
        switch (strategy.getName()) {
            case "horizontal":
                this.strategy = VerticalStrategy.make();
                break;
            case "vertical":
                this.strategy = CompositeStrategy.make();
                break;
            case "composite":
                this.strategy = HorizontalStrategy.make();
                break;
            default:
        }
    }

}
