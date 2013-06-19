package com.googlecode.cchlib.io.filefilter;

import com.googlecode.cchlib.io.SerializableFileFilter;
import java.io.File;
import java.io.FileFilter;

/**
 * TODOC
 *
 * @since 4.1.7
 */
public final class NOTFileFilter implements SerializableFileFilter
{
    private final FileFilter aFileFilter;
    private static final long serialVersionUID = 1L;

    /**
     * TODOC
     * @param aFileFilter
     */
    public NOTFileFilter( FileFilter aFileFilter )
    {
        this.aFileFilter = aFileFilter;
    }

    @Override
    public boolean accept(File file)
    {
        return !aFileFilter.accept(file);
    }
}
