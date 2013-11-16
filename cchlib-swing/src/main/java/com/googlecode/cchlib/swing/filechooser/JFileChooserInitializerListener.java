package com.googlecode.cchlib.swing.filechooser;

import java.util.EventListener;

/**
 *
 *
 * @since 4.1.6
 */
public interface JFileChooserInitializerListener extends EventListener
{
    /**
     *
     * @param event
     */
    void jFileChooserIsReady(JFileChooserInitializerEvent event);

    /**
     *
     * @param event
     */
    void jFileChooserInitializationError(JFileChooserInitializerEvent event);
}
