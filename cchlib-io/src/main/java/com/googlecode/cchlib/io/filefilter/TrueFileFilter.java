package com.googlecode.cchlib.io.filefilter;

import com.googlecode.cchlib.io.SerializableFileFilter;
import java.io.File;

/**
 * A {@link java.io.Serializable} {@link java.io.FileFilter} with an
 * accept(File) method that always return true
 *
 * @since 4.1.7
 */
public final class TrueFileFilter implements SerializableFileFilter
{
    private static final long serialVersionUID = 1L;

    @Override
    public boolean accept(File file)
    {
        return true;
    }
}
