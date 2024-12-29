package edu.rice.comp504.model.cmd;

import edu.rice.comp504.model.paintobj.APaintObject;
import edu.rice.comp504.model.paintobj.Ball;
import edu.rice.comp504.model.paintobj.Wall;
import edu.rice.comp504.model.strategy.StraightStrategy;

import java.awt.*;
import java.beans.PropertyChangeListener;

/**
 * The UpdateStateCmd will possibly update either the paint object location or attribute (color).
 */
public class UpdateStateCmd implements IPaintObjCmd {
    private final PropertyChangeListener[] iWalls;

    /**
     * The constructor.
     * @param iWalls The canvas inner walls
     *
     */
    public UpdateStateCmd(PropertyChangeListener[] iWalls) {
        this.iWalls = iWalls;
    }

    /**
     * Update the state of the paint object
     * @param context  The paint object.
     */
    public void execute(APaintObject context) {
        context.detectCollisionBoundary();

        context.getStrategy().updateState(context);

        if (context instanceof Ball) {
            Ball ball = (Ball) context;
            for (PropertyChangeListener listener: iWalls) {
                if (listener instanceof Wall) {
                    Wall wall = (Wall) listener;
                    if (ball.detectCollisionInnerWall(wall) && wall.getStrategy().getName().equals("null")) {
                        wall.setStrategy(StraightStrategy.makeStrategy());
                        wall.setVelocity(ball.getVelocity());
                        wall.setLocation(ball.getLocation());
                        wall.setLength(Math.min(ball.getRadius(), wall.getLength()));
                    }
                }
            }
        }
    }

}
