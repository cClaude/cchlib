package com.googlecode.cchlib.swing.filechooser;

import java.util.EventListener;

/**
 * NEEDDOC
 *
 * @since 4.1.6
 */
public interface JFileChooserInitializerListener extends EventListener
{
    /**
     * NEEDDOC
     * @param event NEEDDOC
     */
    void jFileChooserIsReady( JFileChooserInitializerEvent event );

    /**
     * NEEDDOC
     * @param event NEEDDOC
     */
    void jFileChooserInitializationError( JFileChooserInitializerEvent event );
}
