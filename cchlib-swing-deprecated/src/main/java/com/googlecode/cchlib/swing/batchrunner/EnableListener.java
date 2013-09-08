package com.googlecode.cchlib.swing.batchrunner;

import java.util.EventListener;

/**
 * An {@link EventListener} event gets fired whenever a bean changes 
 * on panel. 
 * You can register an {@link EnableListener} to disable your custom
 * commands when defaults commands are disabled.
 *
 */
@Deprecated
public interface EnableListener extends EventListener
{
    /**
     * This method gets called when panel enable properties is changed.
     *
     * @param enable true if this component should be enabled, false otherwise
     */
    public void setEnabled( boolean enable );
}
