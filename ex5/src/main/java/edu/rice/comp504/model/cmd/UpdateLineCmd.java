package edu.rice.comp504.model.cmd;

import edu.rice.comp504.model.MovingLine;

public class UpdateLineCmd implements ILineCmd  {
    private static UpdateLineCmd ONLY;

    /**
     * Constructor.
     */
    public UpdateLineCmd() {
    }

    /**
     * Get an update line command.
     * @return The update line command
     */
    public static UpdateLineCmd make() {
        if (ONLY == null) {
            ONLY = new UpdateLineCmd();
        }
        return ONLY;
    }

    @Override
    /**
     * Execute the command by calling the receiver's update line method.
     */
    public void execute(MovingLine context) {
        if (context == null) {
            return;
        }

        if (!context.getStrategy().getName().equals("composite")) {
            context.update();
        }
    }

}
