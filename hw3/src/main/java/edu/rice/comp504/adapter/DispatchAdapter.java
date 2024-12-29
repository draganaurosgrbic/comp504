package edu.rice.comp504.adapter;

import edu.rice.comp504.model.BallWorldStore;

import java.beans.PropertyChangeListener;

public class DispatchAdapter {
    private BallWorldStore bws;

    public DispatchAdapter() {
        bws = new BallWorldStore();
    }

    public BallWorldStore getBws() {
        return bws;
    }

    public void setCanvasDims(String dims) {
        BallWorldStore.setCanvasDims(dims);
    }

    public PropertyChangeListener loadBall(String body, String type) {
        return bws.loadBall(body, type);
    }

    public PropertyChangeListener[] updateBallWorld() {
        return bws.updateBallWorld();
    }

    public void removeAll() {
        bws.removeBallsFromStore();
    }

    public PropertyChangeListener[] removeStrategy(String strategy) {
        return bws.removeStrategy(strategy);
    }
}
