package com.googlecode.cchlib.swing.filechooser;

/**
 * {@link JFileChooserInitializer} Event
 *
 * @since 4.1.6
 */
@FunctionalInterface // Make sonar happy, but implementation should probably need to extend EventObject
public interface JFileChooserInitializerEvent
{
    /**
     * @return true when JFileChooser has been initialized
     */
    boolean isJFileChooserReady();
}
