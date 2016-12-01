package com.googlecode.cchlib.swing.filechooser;

import javax.swing.JFileChooser;

/**
 * Enum for file selection mode
 */
public enum FileSelectionMode
{
    /**
     * Value for {@link JFileChooser#FILES_ONLY}
     */
    FILES_ONLY( JFileChooser.FILES_ONLY ),
    /**
     * Value for {@link JFileChooser#DIRECTORIES_ONLY}
     */
    DIRECTORIES_ONLY( JFileChooser.DIRECTORIES_ONLY ),
    /**
     * Value for {@link JFileChooser#FILES_AND_DIRECTORIES}
     */
    FILES_AND_DIRECTORIES( JFileChooser.FILES_AND_DIRECTORIES ),
    ;

    private int value;

    private FileSelectionMode( final int value )
    {
        this.value = value;
    }

    /**
     * @return corresponding value for swing
     * @see JFileChooser
     */
    public int getValue()
    {
        return this.value;
    }
}
