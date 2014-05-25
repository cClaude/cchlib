package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search.errors;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

final class FileError implements Serializable
{
    private static final long serialVersionUID = 1L;
    private final File file;
    private final IOException cause;

    public FileError( final File file, final IOException cause )
    {
        this.file  = file;
        this.cause = cause;
    }

    public File getFile()
    {
        return file;
    }

    public Exception getCause()
    {
        return cause;
    }
}
