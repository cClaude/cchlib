package com.googlecode.cchlib.io.filefilter;

import com.googlecode.cchlib.io.SerializableFileFilter;
import java.io.File;
import java.io.FileFilter;

/**
 * TODOC
 * @since 4.1.7
 */
public final class XORFileFilter implements SerializableFileFilter
{
    private static final long serialVersionUID = 1L;
    private final FileFilter firstFileFilter;
    private final FileFilter secondFileFilter;

    /**
     * TODOC
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
    public boolean accept( File file )
    {
        return firstFileFilter.accept(file) ^ secondFileFilter.accept(file);
    }
}
