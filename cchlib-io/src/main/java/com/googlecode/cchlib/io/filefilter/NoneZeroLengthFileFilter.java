package com.googlecode.cchlib.io.filefilter;

import com.googlecode.cchlib.io.SerializableFileFilter;
import java.io.File;

/**
 *
 * @since 4.1.7
 */
public final class NoneZeroLengthFileFilter implements SerializableFileFilter
{
    private static final long serialVersionUID = 1L;

    @Override
    public boolean accept(File file)
    {
        return file.length() != 0;
    }
}
