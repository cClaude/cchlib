package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result;

import java.util.NoSuchElementException;

public class SelectException extends Exception {
    private static final long serialVersionUID = 1L;

    public SelectException( final NoSuchElementException cause )
    {
        super( cause );
    }
}
