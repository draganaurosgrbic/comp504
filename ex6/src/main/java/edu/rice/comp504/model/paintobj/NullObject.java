package edu.rice.comp504.model.paintobj;

import edu.rice.comp504.model.strategy.NullStrategy;

import java.awt.*;
import java.beans.PropertyChangeEvent;

/**
 * NullObject represents an initial or unexpected paint object type.
 */
public class NullObject extends APaintObject {
    private static NullObject ONLY;

    /**
     * Constructor.
     */
    public NullObject() {
        super(new Point(400,400), new Point(0,0), "black", "null", NullStrategy.makeStrategy());
    }

    /**
     * Make a null object.  There is only one (singleton).
     * @return A null object
     */
    public static NullObject make() {
        if (ONLY == null) {
            ONLY = new NullObject();
        }
        return ONLY;
    }

    @Override
    public boolean detectCollisionBoundary() {
        return false;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
