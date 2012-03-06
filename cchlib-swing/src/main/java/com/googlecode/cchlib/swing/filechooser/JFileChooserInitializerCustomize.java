package com.googlecode.cchlib.swing.filechooser;

import java.io.File;
import java.io.Serializable;

import javax.swing.JFileChooser;

/**
 * Customize JFileChooser
 */
public interface JFileChooserInitializerCustomize extends Serializable
{
    /**
     * This method was call once, when JFileChooser is
     * ready.
     * @param jFileChooser
     */
    public void perfomeConfig(JFileChooser jFileChooser);

    /**
     * @param currentDirectory the currentDirectory to set
     */
    public void restoreCurrentDirectory( File currentDirectory );
}