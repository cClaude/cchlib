package com.googlecode.cchlib.io.filefilter;

import java.io.File;
import com.googlecode.cchlib.io.SerializableFileFilter;

/**
 * NEEDDOC
 *
 * @since 4.1.7
 */
public final class NOTFileFilter implements SerializableFileFilter
{
    private static final long serialVersionUID = 1L;
    private final SerializableFileFilter aFileFilter;

    /**
     * NEEDDOC
     * @param aFileFilter NEEDDOC
     */
    public NOTFileFilter( final SerializableFileFilter aFileFilter )
    {
        this.aFileFilter = aFileFilter;
    }

    @Override
    public boolean accept( final File file )
    {
        return !this.aFileFilter.accept( file );
    }
}
