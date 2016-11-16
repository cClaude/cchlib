package com.googlecode.cchlib.io.filefilter;

import java.io.File;
import java.io.FileFilter;
import com.googlecode.cchlib.io.SerializableFileFilter;

/**
 * NEEDDOC
 *
 * @since 4.1.7
 */
public final class NOTFileFilter implements SerializableFileFilter
{
    private final FileFilter aFileFilter;
    private static final long serialVersionUID = 1L;

    /**
     * NEEDDOC
     * @param aFileFilter
     */
    public NOTFileFilter( final FileFilter aFileFilter )
    {
        this.aFileFilter = aFileFilter;
    }

    @Override
    public boolean accept(final File file)
    {
        return !this.aFileFilter.accept(file);
    }
}
