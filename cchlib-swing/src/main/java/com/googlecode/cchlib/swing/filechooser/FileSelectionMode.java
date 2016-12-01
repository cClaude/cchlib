package com.googlecode.cchlib.swing.filechooser;

import javax.swing.JFileChooser;

public enum FileSelectionMode
{
    FILES_ONLY( JFileChooser.FILES_ONLY ),
    DIRECTORIES_ONLY( JFileChooser.DIRECTORIES_ONLY ),
    FILES_AND_DIRECTORIES( JFileChooser.FILES_AND_DIRECTORIES ),
    ;

    private int value;

    private FileSelectionMode( final int value )
    {
        this.value = value;
    }

    public int getValue()
    {
        return this.value;
    }
}
