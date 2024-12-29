package edu.rice.comp504.model;

import edu.rice.comp504.model.cmd.UpdateStateCmd;
import edu.rice.comp504.model.paintobj.APaintObject;
import edu.rice.comp504.model.paintobj.Ball;
import edu.rice.comp504.model.paintobj.NullObject;
import edu.rice.comp504.model.paintobj.Wall;
import edu.rice.comp504.model.strategy.NullStrategy;
import edu.rice.comp504.model.strategy.StraightStrategy;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Store for canvas dimensions, balls, and inner walls.
 */
public class BallWorldStore {
    private static Point dims;
    private final PropertyChangeSupport pcs;

    /**
     * Constructor.
     */
    public BallWorldStore() {
        pcs = new PropertyChangeSupport(this);
    }

    /**
     * Get the canvas dimensions.
     * @return The canvas dimensions
     */
    public static Point getCanvasDims() {
        return dims;
    }

    /**
     * Set the canvas dimensions.
     * @param cHeight The canvas height (y).
     * @param cWidth  The canvas width (x).
     */
    public static void setCanvasDims(String cHeight, String cWidth) {
        dims = new Point(Integer.parseInt(cHeight), Integer.parseInt(cWidth));
    }

    /**
     * Call the update method on all the paint object observers to update their position in the paint object world.
     */
    public PropertyChangeListener[] updateBallWorld() {
        UpdateStateCmd cmd = new UpdateStateCmd(pcs.getPropertyChangeListeners("theClock"));
        pcs.firePropertyChange("theClock", null, cmd);
        return pcs.getPropertyChangeListeners("theClock");
    }

    /**
     * Load a paint object into the paint object world.
     * @param type  The paint object type (inner wall, ball).
     * @return A new paint object in the ball world.
     */
    public APaintObject loadPaintObj(String type) {
        APaintObject po;

        if (type.equals("ball")) {
            po = Ball.makeBall(StraightStrategy.makeStrategy(), dims);
        } else if (type.equals("wall")) {
            po = Wall.makeWall(NullStrategy.makeStrategy(), dims);
        } else {
            po = NullObject.make();
        }

        addListener(po);
        return po;
    }

    /**
     * Add a ball or inner wall to the property change listener list.
     * @param pcl  The ball or inner wall
     */
    private void addListener(PropertyChangeListener pcl) {
        this.pcs.addPropertyChangeListener("theClock", pcl);
    }

    /**
     * Remove all property change listeners.
     */
    public void removeListeners() {
        PropertyChangeListener[] pclArr = pcs.getPropertyChangeListeners();
        for (PropertyChangeListener pcl: pclArr) {
            pcs.removePropertyChangeListener(pcl);
        }
    }
}
