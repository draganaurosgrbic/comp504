package edu.rice.comp504.model.cmd;

import edu.rice.comp504.model.MovingLine;

/**
 * Interface used to pass commands to lines.
 */
public interface ILineCmd {

    /**
     * Execute the command on a line.
     * @param context The line.
     */
    public void execute(MovingLine context);
}
