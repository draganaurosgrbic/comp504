package edu.rice.comp504.model;

import edu.rice.comp504.model.ball.Ball;
import edu.rice.comp504.model.ball.BallFac;
import edu.rice.comp504.model.strategy.StrategyFac;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class BallWorldStore {
    private PropertyChangeSupport pcs;
    private BallFac ballFac;
    private StrategyFac strategyFac;
    private static Point dims;
    public static String EVENT_FIELD = "time_elapsed";

    /**
     * Constructor call.
     */
    public BallWorldStore() {
        pcs = new PropertyChangeSupport(this);
        ballFac = new BallFac();
        strategyFac = new StrategyFac();
    }

    public PropertyChangeSupport getPcs() {
        return pcs;
    }

    public static Point getCanvasDims() {
        return dims;
    }

    /**
     * Method stores canvas dimensions of the viewer.
     */
    public static void setCanvasDims(String dims) {
        try {
            BallWorldStore.dims = new Point();
            String[] array = dims.split("&");

            for (String item: array) {
                String[] subArray = item.split("=");
                if (subArray[0].equals("width")) {
                    BallWorldStore.dims.x = (int) Double.parseDouble(subArray[1]);
                } else if (subArray[0].equals("height")) {
                    BallWorldStore.dims.y = (int) Double.parseDouble(subArray[1]);
                }
            }
        } catch (Exception e) {
            throw new CustomException("Canvas dimensions received from the viewer are not parsable!");
        }
    }

    /**
     * Method loads ball of specific type (movement strategy) into the store.
     */
    public Ball loadBall(String body, String type) {
        try {
            String strategy = body.replace("strategy=", "");
            Ball ball = ballFac.make(strategy);
            ball.setStrategy(strategyFac.make(strategy));
            if (ball.getStrategy() == null) {
                throw new CustomException("Unknown ball type/strategy passed!");
            }
            addBallToStore(ball);
            return ball;
        } catch (Exception e) {
            throw new CustomException("Failed loading ball in the store!");
        }
    }

    /**
     * Method updates position of all balls in the store.
     */
    public PropertyChangeListener[] updateBallWorld() {
        try {
            pcs.firePropertyChange(EVENT_FIELD, false, true);
            return pcs.getPropertyChangeListeners(EVENT_FIELD);
        } catch (Exception e) {
            throw new CustomException("Failed updating positions of balls in the store!");
        }
    }

    /**
     * Method removes all balls from the store.
     */
    public void removeBallsFromStore() {
        try {
            for (PropertyChangeListener listener: pcs.getPropertyChangeListeners(EVENT_FIELD)) {
                pcs.removePropertyChangeListener(EVENT_FIELD, listener);
            }
        } catch (Exception e) {
            throw new CustomException("Failed clearing balls from the store!");
        }
    }

    /**
     * Method removes all balls of specific type (movement strategy) from the store.
     */
    public PropertyChangeListener[] removeStrategy(String strategy) {
        try {
            for (PropertyChangeListener listener: pcs.getPropertyChangeListeners(EVENT_FIELD)) {
                Ball ball = (Ball) listener;
                if (ball.getStrategy().getName().equals(strategy)) {
                    pcs.removePropertyChangeListener(EVENT_FIELD, listener);
                }
            }

            return pcs.getPropertyChangeListeners(EVENT_FIELD);
        } catch (Exception e) {
            throw new CustomException("Failed removing " + strategy + " balls from the store!");
        }
    }

    private void addBallToStore(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(EVENT_FIELD, pcl);
    }

}
