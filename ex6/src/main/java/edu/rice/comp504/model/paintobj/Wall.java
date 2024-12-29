package edu.rice.comp504.model.paintobj;

import edu.rice.comp504.model.cmd.UpdateStateCmd;
import edu.rice.comp504.model.strategy.IUpdateStrategy;

import java.awt.*;
import java.beans.PropertyChangeEvent;

/**
 * A vertical wall at a location within the canvas.
 */
public class Wall extends APaintObject {
    private int len;

    /**
     * Constructor for Wall.
     * @param loc The wall location.  The origin (0,0) is the upper left corner of the canvas.
     * @param vel The wall velocity.  The velocity is a vector with an x and y component.
     * @param len The vertical length of the wall.
     * @param strategy The wall strategy.
     */
    private Wall(Point loc, Point vel, int len, IUpdateStrategy strategy) {
        super(loc, vel, "black", "wall", strategy);
        this.len = len;
    }

    /**
     * Make a new inner wall
     * @return An inner wall.
     */
    public static Wall makeWall(IUpdateStrategy strategy, Point dims) {
        return new Wall(new Point((int)Math.floor(Math.random() * dims.x), (int)Math.floor(Math.random() * dims.y)),
                new Point(0, 0), (int)Math.floor(Math.random() * 200), strategy);
    }

    /**
     * Get the length of the inner wall.
     * @return The inner wall length.
     */
    public int getLength() {
        return this.len;
    }

    /**
     * Set the length of the inner wall.
     * @param l The inner wall length.
     */
    public void setLength(int l) {
        this.len = l;
    }

    /**
     * Inner wall does not move so it should always return false.
     */
    public boolean detectCollisionBoundary() {
        return false;
    }

    /**
     * Set the wall update strategy.
     * @param strat  The new strategy
     */
    public void setStrategy(IUpdateStrategy strat) {
        this.strategy = strat;
    }

    /**
     * Set the wall velocity.
     * @param vel The new velocity
     */
    public void setVelocity(Point vel) {
        this.vel = vel;
    }

    /**
     * Update state of the inner wall when the property change event occurs by executing the command.
     */
    public void propertyChange(PropertyChangeEvent e) {
        ((UpdateStateCmd) e.getNewValue()).execute(this);
    }
}
