package edu.rice.comp504.model.command;

import edu.rice.comp504.model.paintobject.PaintObject;

public interface IPaintObjectCommand {
    void execute(PaintObject object);
}
