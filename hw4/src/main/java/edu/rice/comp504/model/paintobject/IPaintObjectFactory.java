package edu.rice.comp504.model.paintobject;

public interface IPaintObjectFactory {
    PaintObject make(String type, boolean rotation);
}
