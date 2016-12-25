package com.googlecode.cchlib.io.exceptions;

import java.io.File;
import java.io.IOException;

/**
 * Created when a file can't be delete
 */
public class FileDeleteException extends IOException
{
    private static final long serialVersionUID = 1L;

    private final File file;

    public FileDeleteException( final File file )
    {
        this( file, file.getPath(), null );
    }

    public FileDeleteException( final File file, final String message, final Throwable cause )
    {
        super( message );

        initCause( cause ); // old JDK

        this.file = file;
    }

    /**
     * Returns {@link File} object that can't be delete
     * @return {@link File} object that can't be delete
     */
    public File getFile()
    {
        return this.file;
    }
}
