package edu.rice.comp504.model;

import edu.rice.comp504.model.paintobj.APaintObject;
import java.beans.PropertyChangeListener;

/**
 * The dispatch adapter provides the interface between the controller and model.
 */
public class DispatcherAdapter {
    private final BallWorldStore bwStore;

    /**
     * Constructor.
     * @param store  The ball world store.
     */
    public DispatcherAdapter(BallWorldStore store) {
        bwStore = store;
    }

    /**
     * Call the update method on all the paintobj observers to update their position in the paintobj world.
     */
    public PropertyChangeListener[] updateBallWorld() {
        return bwStore.updateBallWorld();
    }

    /**
     * Load a paintobj into the paintobj world.
     * @param type  The paintobj type (line, ball)
     * @return A paintobj
     */
    public APaintObject loadPaintObj(String type) {
        return bwStore.loadPaintObj(type);
    }

    /**
     * Remove all property change listeners.
     */
    public void removeListeners() {
        bwStore.removeListeners();
    }
}
