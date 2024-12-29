package edu.rice.comp504.model.paintobj;

import edu.rice.comp504.model.BallWorldStore;
import edu.rice.comp504.model.cmd.UpdateStateCmd;
import edu.rice.comp504.model.strategy.IUpdateStrategy;

import java.awt.*;
import java.beans.PropertyChangeEvent;

/**
 * A circle that the view can draw on the user's canvas.
 */
public class Ball extends APaintObject{
    private int radius;

    /**
     * Constructor for Ball.
     * @param loc The paint obj location.  The origin (0,0) is the upper left corner of the canvas.
     * @param radius  The paintobj radius.
     * @param vel The paintobj velocity.  The velocity is a vector with an x and y component.
     * @param color The paintobj color.
     * @param strategy The paint object strategy.
     */
    private Ball(Point loc, int radius, Point vel, String color, IUpdateStrategy strategy) {
        super(loc, vel, color, "ball", strategy);
        this.radius = radius;
    }

    /**
     * Make a new ball.
     * @return A ball.
     */
    public static Ball makeBall(IUpdateStrategy strategy, Point dims) {
        return new Ball(new Point((int)Math.floor(Math.random() * dims.x), (int)Math.floor(Math.random() * dims.y)),
                (int)Math.floor(Math.random() * 40 + 10),
                new Point((int)Math.floor(Math.random() * 15 + 1), (int)Math.floor(Math.random() * 15) + 1),
                "green", strategy);
    }



    /**
     * Get the radius of the ball.
     * @return The ball radius.
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Set the radius of the ball.
     * @param r The ball radius.
     */
    public void setRadius(int r) {
        radius = r;
    }


    /**
     * Detects collision between a ball and a boundary wall.  Change direction if ball collides with a boundary.
     * @return True if balls collided with boundary wall.
     */
    public boolean detectCollisionBoundary() {
        // The canvas dimensions
        Point dims = BallWorldStore.getCanvasDims();
        // right wall collision
        if ((vel.x < 0) && ((loc.x - radius) <= 0)) {
            vel.x = vel.x * -1;
            return true;
        } else if ((vel.x > 0) && ((loc.x + radius) >= dims.x)) {
            // left wall collision
            vel.x = vel.x * -1;
            return true;
        }
        // top wall collision
        if ((vel.y < 0) && ((loc.y - radius) <= 0)) {
            vel.y = vel.y * -1;
            return true;
        } else if ((vel.y > 0) && ((loc.y + radius) >= dims.y)) {
            // bottom wall collision
            vel.y = vel.y * -1;
            return true;
        }
        return false;
    }

    /**
     * Detects collision between a ball and an inner wall.
     * @param wall The inner wall.
     * @return True if balls collided with inner wall.
     */
    public boolean detectCollisionInnerWall(Wall wall) {
        Point wallLoc = wall.getLocation();
        // ball crosses wall in the x direction
        boolean crossedWallX = (loc.x < wallLoc.x) && ((loc.x + radius) >= wallLoc.x) ||
                loc.x > wallLoc.x && ((loc.x - radius <= wallLoc.x));
        // ball crosses wall in the y direction
        boolean crossedWallY = ((loc.y + radius) >= wallLoc.y) && ((loc.y - radius) <= (wallLoc.y + wall.getLength()));
        return crossedWallX && crossedWallY;
    }

    /**
     * Update state of the ball when the property change event occurs by executing the command.
     */
    public void propertyChange(PropertyChangeEvent e) {
        ((UpdateStateCmd) e.getNewValue()).execute(this);
    }
}
