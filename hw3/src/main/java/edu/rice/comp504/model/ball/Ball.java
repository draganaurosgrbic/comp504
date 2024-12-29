package edu.rice.comp504.model.ball;

import edu.rice.comp504.model.BallWorldStore;
import edu.rice.comp504.model.strategy.IUpdateStrategy;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Ball implements PropertyChangeListener {
    private Point loc;
    private int radius;
    private Point vel;
    private String color;
    private IUpdateStrategy strategy;


    private int distanceX;
    private int distanceY;
    private int distanceXY;
    private int state;
    private double angle;


    public Point getLocation() {
        return loc;
    }

    public void setLocation(Point loc) {
        this.loc = loc;
    }

    public int getRadius() {
        return radius;
    }

    public Point getVel() {
        return vel;
    }

    public IUpdateStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(IUpdateStrategy strategy) {
        this.strategy = strategy;
    }


    public int getDistanceX() {
        return distanceX;
    }

    public int getDistanceY() {
        return distanceY;
    }

    public int getDistanceXY() {
        return distanceXY;
    }

    public int getState() {
        return state;
    }

    public double getAngle() {
        return angle;
    }

    public void incrementDistanceX() {
        ++distanceX;
    }

    public void incrementDistanceY() {
        ++distanceY;
    }

    public void incrementDistanceXY() {
        ++distanceXY;
    }

    public void resetDistanceX() {
        distanceX = 0;
    }

    public void resetDistanceY() {
        distanceY = 0;
    }

    public void resetDistanceXY() {
        distanceXY = 0;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void invertVelX() {
        vel = new Point(-vel.x, vel.y);
    }

    public void invertVelY() {
        vel = new Point(vel.x, -vel.y);
    }

    /**
     * Method increases size of the angle inside circle around which the ball rotates.
     */
    public void incrementAngle() {
        angle += 0.1;
        if (angle >= 360) {
            angle = 0;
        }
    }

    protected Ball(Point loc, int radius, Point vel, String color) {
        this.loc = loc;
        this.radius = radius;
        this.vel = vel;
        this.color = color;
    }

    public boolean detectCollision() {
        return loc.x - radius <= 0 || loc.x + radius >= BallWorldStore.getCanvasDims().x
                || loc.y - radius <= 0 || loc.y + radius >= BallWorldStore.getCanvasDims().y;
    }

    /**
     * Method detects wall with which a collision occurred.
     */
    public int detectCollisionWall() {
        if (loc.x - radius <= 0) {
            return 1;
        }
        if (loc.x + radius >= BallWorldStore.getCanvasDims().x) {
            return 3;
        }
        if (loc.y - radius <= 0) {
            return 2;
        }
        if (loc.y + radius >= BallWorldStore.getCanvasDims().y) {
            return 4;
        }
        return 0;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        strategy.updateState(this);
        if (detectCollision()) {
            switch (detectCollisionWall()) {
                case 1:
                case 3:
                    vel = new Point(-vel.x, vel.y);
                    break;
                case 2:
                case 4:
                    vel = new Point(vel.x, -vel.y);
                    break;
                default:
                    break;
            }
        }
    }
}
