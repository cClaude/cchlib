package com.googlecode.cchlib.io.filefilter;

import java.io.File;
import java.io.FileFilter;
import com.googlecode.cchlib.io.SerializableFileFilter;

/**
 * NEEDDOC
 * @since 4.1.7
 */
public final class XORFileFilter implements SerializableFileFilter
{
    private static final long serialVersionUID = 1L;
    private final FileFilter firstFileFilter;
    private final FileFilter secondFileFilter;

    /**
     * NEEDDOC
     * @param firstFileFilter
     * @param secondFileFilter
     */
    public XORFileFilter(
            final FileFilter firstFileFilter,
            final FileFilter secondFileFilter )
    {
        this.firstFileFilter = firstFileFilter;
        this.secondFileFilter = secondFileFilter;
    }

    @Override
    public boolean accept( final File file )
    {
        return this.firstFileFilter.accept(file) ^ this.secondFileFilter.accept(file);
    }
}
