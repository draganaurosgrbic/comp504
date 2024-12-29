package edu.rice.comp504.model;

import edu.rice.comp504.model.command.UpdateStateCommand;
import edu.rice.comp504.model.paintobject.PaintObject;
import edu.rice.comp504.model.paintobject.IPaintObjectFactory;
import edu.rice.comp504.model.paintobject.PaintObjectFactory;
import edu.rice.comp504.model.strategy.IStrategyFactory;
import edu.rice.comp504.model.strategy.StrategyFactory;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PaintWorldStore {

    private static Point dimensions;
    public static String EVENT_FIELD = "theClock";

    private PropertyChangeSupport support;
    private IPaintObjectFactory objectFactory;
    private IStrategyFactory strategyFactory;

    /**
     * Constructor call.
     */
    public PaintWorldStore() {
        support = new PropertyChangeSupport(this);
        objectFactory = new PaintObjectFactory();
        strategyFactory = new StrategyFactory();
    }

    public PropertyChangeSupport getSupport() {
        return support;
    }

    public static Point getCanvasDimensions() {
        return dimensions;
    }

    public static void setCanvasDimensions(String cWidth, String cHeight) {
        dimensions = new Point(Integer.parseInt(cWidth), Integer.parseInt(cHeight));
    }

    public PropertyChangeListener[] paintWorld() {
        return support.getPropertyChangeListeners(EVENT_FIELD);
    }

    public void removeObject(PaintObject object) {
        support.removePropertyChangeListener(EVENT_FIELD, object);
    }

    /**
     * Method loads paint object into paint world.
     */
    public void loadObject(String type, String updateStrategy, String strategySwitchable, String collisionStrategy) {
        PaintObject object = objectFactory.make(type, updateStrategy.equals("rotation"));
        object.setUpdateStrategy(strategyFactory.makeUpdateStrategy(updateStrategy));
        object.setStrategySwitchable(strategySwitchable.equals("true"));
        if (object.isBall()) {
            object.setCollisionStrategy(strategyFactory.makeCollisionStrategy(collisionStrategy));
        }
        support.addPropertyChangeListener(EVENT_FIELD, object);
    }

    /**
     * Method removes paint objects from paint world.
     */
    public void removeObjects(String id) {
        for (PropertyChangeListener listener: paintWorld()) {
            PaintObject object = (PaintObject) listener;
            if (object.getUpdateStrategy().getName().equals(id)) {
                support.removePropertyChangeListener(EVENT_FIELD, listener);
            }
        }
    }

    /**
     * Method switches strategies to paint objects in paint world.
     */
    public void switchStrategies(String fromStrategy, String toStrategy) {
        for (PropertyChangeListener listener: paintWorld()) {
            PaintObject object = (PaintObject) listener;
            if (object.getUpdateStrategy().getName().equals(fromStrategy) && object.isStrategySwitchable()) {
                object.setUpdateStrategy(strategyFactory.makeUpdateStrategy(toStrategy));
            }
        }
    }

    public PropertyChangeListener[] updateWorld() {
        support.firePropertyChange(EVENT_FIELD, null, new UpdateStateCommand(this));
        return paintWorld();
    }

    /**
     * Method removes all paint objects from paint world.
     */
    public void clearWorld() {
        for (PropertyChangeListener listener: paintWorld()) {
            support.removePropertyChangeListener(EVENT_FIELD, listener);
        }
    }

}
