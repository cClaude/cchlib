package com.googlecode.cchlib.io.filefilter;

import com.googlecode.cchlib.io.SerializableFileFilter;
import java.io.File;

/**
 * Create a {@link java.io.FileFilter} that select only files (not directories)
 *
 * @since 4.1.7
 */
public final class FileFileFilter implements SerializableFileFilter
{
    private static final long serialVersionUID = 1L;

    @Override
    public boolean accept(File file)
    {
        return file.isFile();
    }
}
