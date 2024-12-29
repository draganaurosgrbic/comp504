package edu.rice.comp504.model.cmd;

import edu.rice.comp504.model.MovingLine;
import edu.rice.comp504.model.strategy.LineStrategyFac;

/**
 *  Switch strategies by using the switch strategy command.
 */
public class SwitchStrCmd implements ILineCmd {
    private static SwitchStrCmd ONLY;

    private LineStrategyFac factory;

    /**
     * Constructor.
     */
    public SwitchStrCmd() {
        factory = new LineStrategyFac();
    }

    /**
     * Make a switch strategy command.
     * @return The switch strategy command
     */
    public static SwitchStrCmd make() {
        if (ONLY == null) {
            ONLY = new SwitchStrCmd();
        }
        return ONLY;
    }

    @Override
    /**
     * Execute the command by calling the receiver's update line method.
     */
    public void execute(MovingLine context) {
        context.setStrategy(factory.switchStrategy(context));
    }

}
