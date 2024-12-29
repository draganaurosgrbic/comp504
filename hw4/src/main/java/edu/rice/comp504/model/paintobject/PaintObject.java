package edu.rice.comp504.model.paintobject;

import edu.rice.comp504.model.PaintWorldStore;
import edu.rice.comp504.model.command.UpdateStateCommand;
import edu.rice.comp504.model.strategy.collision.ICollisionStrategy;
import edu.rice.comp504.model.strategy.update.IUpdateStrategy;
import edu.rice.comp504.model.strategy.update.RotationStrategy;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PaintObject implements PropertyChangeListener {
    private String type;
    private Point location;
    private Point velocity;
    private int size;
    private String color;
    private double opacity;
    private boolean hasCross;
    private IUpdateStrategy updateStrategy;
    private boolean strategySwitchable;
    private ICollisionStrategy collisionStrategy;

    private int state;
    private int horizontalDistance;
    private int verticalDistance;
    private int diagonalDistance;
    private double angle;
    private boolean rotation;

    /**
     * Constructor call.
     */
    public PaintObject(String type, Point location, Point velocity, int size, String color) {
        this.type = type;
        this.location = location;
        this.velocity = velocity;
        this.size = size;
        this.color = color;
        this.opacity = 1;
    }

    public boolean isRotation() {
        return rotation;
    }


    public int getSize() {
        return size;
    }

    public double getOpacity() {
        return opacity;
    }

    public String getColor() {
        return color;
    }

    public boolean getHasCross() {
        return hasCross;
    }

    public String getType() {
        return type;
    }

    public IUpdateStrategy getUpdateStrategy() {
        return updateStrategy;
    }

    /**
     * Method sets the update strategy of paint object.
     */
    public void setUpdateStrategy(IUpdateStrategy updateStrategy) {
        state = 0;
        horizontalDistance = 0;
        verticalDistance = 0;
        diagonalDistance = 0;
        angle = 0;
        rotation = updateStrategy instanceof RotationStrategy;

        this.updateStrategy = updateStrategy;
    }

    /**
     * Method detects whether paint object collides with a wall.
     */
    public void detectCollisionWithWall() {
        Point dimensions = PaintWorldStore.getCanvasDimensions();

        if (location.x - size <= 0 || location.x + size >= dimensions.x) {
            velocity.x *= -1;
        } else if (location.y - size <= 0 || location.y + size >= dimensions.y) {
            velocity.y *= -1;
        }
    }

    /**
     * Method changes color of paint object.
     */
    public void changeColor() {
        String newColor;
        while (true) {
            newColor = PaintObjectFactory.getColor();
            if (!newColor.equals(color)) {
                color = newColor;
                break;
            }
        }
    }

    /**
     * Method increases size of paint object angle.
     */
    public void addToAngle() {
        angle += 0.1;
        if (angle >= 360) {
            angle = 0;
        }
    }

    /**
     * Method increases size of paint object angle by a specific amount.
     */
    public void addToAngle(int amount) {
        angle += amount;
        if (angle >= 360) {
            angle -= 360;
        }
    }

    public ICollisionStrategy getCollisionStrategy() {
        return collisionStrategy;
    }

    public void setCollisionStrategy(ICollisionStrategy collisionStrategy) {
        this.collisionStrategy = collisionStrategy;
    }

    public boolean isStrategySwitchable() {
        return strategySwitchable;
    }

    public void setStrategySwitchable(boolean strategySwitchable) {
        this.strategySwitchable = strategySwitchable;
    }

    public boolean detectCollisionWithBall(PaintObject object) {
        double distance = Math.sqrt(Math.pow(location.x - object.location.x, 2) + Math.pow(location.y - object.location.y, 2));
        return this != object && distance <= size + object.size;
    }

    /**
     * Method determines next location of paint object.
     */
    public void nextLocation(int velX, int velY) {
        Point dimensions = PaintWorldStore.getCanvasDimensions();

        location.x += velX;
        location.y += velY;

        if (location.x - size < 0) {
            location.x = size;
        }

        if (location.x + size > dimensions.x) {
            location.x = dimensions.x - size;
        }

        if (location.y - size < 0) {
            location.y = size;
        }

        if (location.y + size > dimensions.y) {
            location.y = dimensions.y - size;
        }

        if (velX * velY != 0) {
            angle = Integer.signum(velX * velY) * 45;
        } else if (velY != 0) {
            angle = Integer.signum(velY) * 90;
        } else {
            angle = 0;
        }
    }

    public void setHasCross(boolean hasCross) {
        this.hasCross = hasCross;
    }

    public boolean isBall() {
        return type.equals("ball") && !isEmpty();
    }

    public boolean isEmpty() {
        return size <= 0 || opacity <= 0;
    }

    /**
     * Method increases size of paint object by the size of object being eaten.
     */
    public void eat(PaintObject object) {
        size += object.size / 2;

        Point dimensions = PaintWorldStore.getCanvasDimensions();

        if (location.x < size) {
            location.x = size;
        }
        if (location.y < size) {
            location.y = size;
        }
        if (location.x > dimensions.x - size) {
            location.x = dimensions.x - size;
        }
        if (location.y > dimensions.y - size) {
            location.y = dimensions.y - size;
        }
    }

    public void remove() {
        size = 0;
    }

    public void decreaseSize() {
        --size;
    }

    public void decreaseOpacity() {
        opacity -= 0.1;
    }

    public void convertToFish() {
        type = "fish";
        size = PaintObjectFactory.FISH_SIZE;
    }

    public Point getVelocity() {
        return velocity;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getHorizontalDistance() {
        return horizontalDistance;
    }

    public int getVerticalDistance() {
        return verticalDistance;
    }

    public int getDiagonalDistance() {
        return diagonalDistance;
    }

    public void increaseHorizontalDistance() {
        ++horizontalDistance;
    }

    public void increaseVerticalDistance() {
        ++verticalDistance;
    }

    public void increaseDiagonalDistance() {
        ++diagonalDistance;
    }

    public void resetHorizontalDistance() {
        horizontalDistance = 0;
    }

    public void resetVerticalDistance() {
        verticalDistance = 0;
    }

    public void resetDiagonalDistance() {
        diagonalDistance = 0;
    }

    public double getAngle() {
        return angle;
    }

    public void invertVelX() {
        velocity.x *= -1;
    }

    public void invertVelY() {
        velocity.y *= -1;
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        ((UpdateStateCommand) e.getNewValue()).execute(this);
    }
}
