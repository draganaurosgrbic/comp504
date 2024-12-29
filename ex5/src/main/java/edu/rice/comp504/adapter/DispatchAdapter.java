package edu.rice.comp504.adapter;


import edu.rice.comp504.model.LineStore;
import edu.rice.comp504.model.MovingLine;
import edu.rice.comp504.model.cmd.SwitchStrCmd;
import edu.rice.comp504.model.cmd.UpdateLineCmd;

/**
 * The dispatch adapter is responsible for ensuring that the moving lines are properly updated.
 */
public class DispatchAdapter {
    private final LineStore lineStore;

    /**
     * constructor.
     */
    public DispatchAdapter(LineStore ls) {
        this.lineStore = ls;
    }

    /**
     * Get the line store.
     * @return The line store
     */
    public LineStore getLineStore() {
        return this.lineStore;
    }

    /**
     * Return store containing a new line with a random initial strategy.
     * @return The line store
     */
    public LineStore loadLine(String kind) {
        lineStore.makeLine(kind);
        return this.lineStore;
    }

    /**
     * Update the lines.
     * @return The line store
     */
    public LineStore updateLines() {
        this.lineStore.sendRecvCmd(UpdateLineCmd.make());
        return this.lineStore;
    }

    /**
     * Switch the line strategies.
     * @return The line store
     */
    public LineStore switchStrategy() {
        this.lineStore.sendRecvCmd(SwitchStrCmd.make());
        return this.lineStore;
    }

    /**
     * Reset lines to original position.
     * @return The line store
     */
    public LineStore resetLines() {
        lineStore.reset();
        return this.lineStore;
    }
}
