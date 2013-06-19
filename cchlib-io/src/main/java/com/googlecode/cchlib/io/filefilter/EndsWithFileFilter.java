package com.googlecode.cchlib.io.filefilter;

import java.io.File;
import com.googlecode.cchlib.io.SerializableFileFilter;

/**
 * Case sensitive EndsWithFileFilter, Check if match with end of String giving using {@link File#getName()}
 *
 * @since 4.1.7
 */
public class EndsWithFileFilter implements SerializableFileFilter
{
    private static final long serialVersionUID = 1L;
    private String endsWith;

    /**
     * Create an EndsWithFileFilter
     * 
     * @param endsWith Value to check (not dot append)
     */
    public EndsWithFileFilter( final String endsWith )
    {
        this.endsWith = endsWith;
    }

    @Override
    public boolean accept( final File file )
    {
        return file.getName().endsWith( endsWith );
    }
}
