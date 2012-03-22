package com.googlecode.cchlib.io.filefilter;

import java.io.File;
import com.googlecode.cchlib.io.SerializableFileFilter;

/**
 * Create a {@link java.io.FileFilter} that select only directories
 *
 * @since 4.1.7
 */
public final class DirectoryFileFilter implements SerializableFileFilter
{
    private static final long serialVersionUID = 1L;

    @Override
    public boolean accept(File file)
    {
        return file.isDirectory();
    }
}
