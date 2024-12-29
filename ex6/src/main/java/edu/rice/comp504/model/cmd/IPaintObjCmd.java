package edu.rice.comp504.model.cmd;

import edu.rice.comp504.model.paintobj.APaintObject;

/**
 * The IPaintObjCmd is an interface used to pass commands to balls in the BallWorld.  The
 * paintobj must execute the command.
 */
public interface IPaintObjCmd {

    /**
     * Execute the command.
     * @param context The receiver paintobj on which the command is executed.
     */
    void execute(APaintObject context);
}
