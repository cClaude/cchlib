package com.googlecode.cchlib.swing.batchrunner.ihm;

import java.io.Serializable;
import com.googlecode.cchlib.swing.batchrunner.BRExitable;
import com.googlecode.cchlib.swing.batchrunner.misc.BRXLocaleResources;

/**
 * Initial parameters for {@link BRFrame}
 */
public interface BRFrameBuilder extends Serializable
{
    /**
     * Return the BRPanelLocaleResources for the app
     * @return the BRPanelLocaleResources
     */
    BRPanelLocaleResources getBRPanelLocaleResources();

    /**
     * Return the BRXLocaleResources for the app
     * @return the BRXLocaleResources
     */
    BRXLocaleResources getBRXLocaleResources();

    /**
     * Define method to quit application
     * @return a method to quit application
     */
    BRExitable getBRExitable();
}
