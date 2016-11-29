package com.googlecode.cchlib.io.filefilter;

import java.io.File;
import com.googlecode.cchlib.io.SerializableFileFilter;

/**
 * NEEDDOC
 * @since 4.1.7
 */
public final class XORFileFilter implements SerializableFileFilter
{
    private static final long serialVersionUID = 1L;
    private final SerializableFileFilter firstFileFilter;
    private final SerializableFileFilter secondFileFilter;

    /**
     * NEEDDOC
     * @param firstFileFilter   a SerializableFileFilter
     * @param secondFileFilter  a SerializableFileFilter
     */
    public XORFileFilter(
        final SerializableFileFilter firstFileFilter,
        final SerializableFileFilter secondFileFilter
        )
    {
        this.firstFileFilter  = firstFileFilter;
        this.secondFileFilter = secondFileFilter;
    }

    @Override
    public boolean accept( final File file )
    {
        return this.firstFileFilter.accept(file) ^ this.secondFileFilter.accept(file);
    }
}
