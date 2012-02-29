package com.googlecode.cchlib.swing.batchrunner;

import java.util.EventListener;

/**
 * A "EventListener" event gets fired whenever a bean changes 
 * on {@link BatchRunnerPanel}. You can register an
 * EnableListener to disable your custom commands when defaults
 * commands are disabled.
 *
 */
public interface EnableListener extends EventListener
{
    /**
     * This method gets called when {@link BatchRunnerPanel} 
     * enable properties is changed.
     *
     * @param enable true if this component should be enabled, false otherwise
     */
    public void setEnabled( boolean enable );

}
