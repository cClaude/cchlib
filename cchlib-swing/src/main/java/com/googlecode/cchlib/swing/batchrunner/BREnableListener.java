package com.googlecode.cchlib.swing.batchrunner;

import java.util.EventListener;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRPanel;

/**
 * An {@link EventListener} event gets fired whenever a bean changes
 * on {@link BRPanel}.
 * You can register an {@link BREnableListener} to disable your custom
 * commands when defaults commands are disabled.
 *
 * @since 1.4.8
 */
public interface BREnableListener extends EventListener
{
    /**
     * This method gets called when {@link BRPanel}
     * enable properties is changed.
     *
     * @param enable true if this component should be enabled, false otherwise
     */
    void setEnabled( boolean enable );
}
