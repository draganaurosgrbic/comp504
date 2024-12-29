package edu.rice.comp504.model.command;

import edu.rice.comp504.model.PaintWorldStore;
import edu.rice.comp504.model.paintobject.PaintObject;

import java.beans.PropertyChangeListener;

public class UpdateStateCommand implements IPaintObjectCommand {

    private final PaintWorldStore store;

    public UpdateStateCommand(PaintWorldStore store) {
        this.store = store;
    }

    @Override
    public void execute(PaintObject object) {
        object.detectCollisionWithWall();
        object.getUpdateStrategy().updateState(object);

        if (object.isBall()) {
            for (PropertyChangeListener listener: store.paintWorld()) {
                PaintObject otherObject = (PaintObject) listener;
                if (otherObject.isBall()) {
                    if (object.detectCollisionWithBall(otherObject)) {
                        object.getCollisionStrategy().updateBalls(object, otherObject);
                        if (object.isEmpty()) {
                            store.removeObject(object);
                        }
                        if (otherObject.isEmpty()) {
                            store.removeObject(otherObject);
                        }
                    }
                }
            }
        }
    }
}
