package edu.rice.comp504.model.paintobj;

import edu.rice.comp504.model.strategy.IUpdateStrategy;

import java.awt.*;
import java.beans.PropertyChangeListener;

/**
 * A object that the view can draw on the user's canvas.
 */
public abstract class APaintObject implements PropertyChangeListener {
    Point loc;
    Point vel;
    IUpdateStrategy strategy;
    String color;
    String type;

    /**
     * Constructor.
     * @param loc  The location of the paintable object on the canvas
     * @param vel  The object velocity
     * @param strategy  The object update strategy
     */
    public APaintObject(Point loc, Point vel, String color, String type, IUpdateStrategy strategy) {
        this.loc = loc;
        this.vel = vel;
        this.color = color;
        this.type = type;
        this.strategy = strategy;


    }


    /**
     * Detects collision between a paint and a boundary wall.  Change direction if ball collides with boundary.
     */
    public abstract boolean detectCollisionBoundary();



    /**
     * Get the paintable object type.
     * @return The paintable object type
     */
    public String getType() {
        return type;
    }

    /**
     * Get the paint location in the paint world.
     * @return The paint location.
     */
    public Point getLocation() {
        return loc;
    }


    /**
     * Set the paint location in the canvas.  The origin (0,0) is the top left corner of the canvas.
     * @param loc  The paint coordinate.
     */
    public void setLocation(Point loc) {
        this.loc = loc;
    }

    /**
     * Check if the paint object is colorable.  For example, images are not colorable and would return false.
     */
    public boolean isColorable() {
        return false;
    }

    /**
     * Get the paintable object color.
     * @return The paintable object color
     */
    public String getColor() {
        return color;
    }

    /**
     * Set the paintable object color.
     * @param color The new color
     */
    public void setColor(String color) {
        if (isColorable()) {
            this.color = color;
        }
    }

    /**
     * Compute the next location of the paint in the paint world given the velocity.
     * @param velX The horizontal velocity
     * @param velY The vertical velocity
     */
    public void nextLocation(int velX, int velY) {
        loc.x += velX;
        loc.y += velY;
    }

    /**
     * Get the velocity of the paint.
     * @return The paint velocity
     */
    public  Point getVelocity() {
        return vel;
    }

    /**
     * Get the paint object strategy.
     * @return The paint object strategy
     */
    public IUpdateStrategy getStrategy() {
        return strategy;
    }

}
