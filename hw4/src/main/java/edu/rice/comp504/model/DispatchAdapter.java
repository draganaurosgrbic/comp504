package edu.rice.comp504.model;

import java.beans.PropertyChangeListener;

public class DispatchAdapter {
    private final PaintWorldStore store;

    public DispatchAdapter(PaintWorldStore store) {
        this.store = store;
    }

    public void loadObject(String type, String updateStrategy, String strategySwitchable, String collisionStrategy) {
        store.loadObject(type, updateStrategy, strategySwitchable, collisionStrategy);
    }

    public void removeObjects(String id) {
        store.removeObjects(id);
    }

    public void switchStrategies(String fromStrategy, String toStrategy) {
        store.switchStrategies(fromStrategy, toStrategy);
    }

    public PropertyChangeListener[] updateWorld() {
        return store.updateWorld();
    }

    public void clearWorld() {
        store.clearWorld();
    }

}
