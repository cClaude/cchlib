package com.googlecode.cchlib.io.filefilter;

import java.io.File;
import com.googlecode.cchlib.io.SerializableFileFilter;

/**
 * NEEDDOC
 *
 * @since 4.1.7
 */
public final class FileLengthFileFilter implements SerializableFileFilter
{
    private static final long serialVersionUID = 1L;
    private final long length;

    /**
     * NEEDDOC
     * @param length
     */
    public FileLengthFileFilter( final long length )
    {
        this.length = length;
    }

    @Override
    public boolean accept(final File file)
    {
        return file.length() == this.length;
    }
}
