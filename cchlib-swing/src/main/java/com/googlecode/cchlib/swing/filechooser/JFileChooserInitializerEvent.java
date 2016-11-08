package com.googlecode.cchlib.swing.filechooser;

/**
 * {@link JFileChooserInitializer} Event
 *
 * @since 4.1.6
 */
public interface JFileChooserInitializerEvent
{
    /**
     * @return true when JFileChooser has been initialized
     */
    boolean isJFileChooserReady();
}
