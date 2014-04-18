package com.googlecode.cchlib.io.filefilter;

import com.googlecode.cchlib.io.SerializableFileFilter;
import java.io.File;

/**
 * TODOC
 *
 * @since 4.1.7
 */
public final class FileLengthFileFilter implements SerializableFileFilter
{
    private static final long serialVersionUID = 1L;
    private final long length;

    /**
     * TODOC
     * @param length
     */
    public FileLengthFileFilter( final long length )
    {
        this.length = length;
    }

    @Override
    public boolean accept(File file)
    {
        return file.length() == length;
    }
}
