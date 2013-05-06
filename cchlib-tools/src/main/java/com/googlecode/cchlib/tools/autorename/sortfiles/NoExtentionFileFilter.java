package com.googlecode.cchlib.tools.autorename.sortfiles;

import java.io.File;
import com.googlecode.cchlib.io.SerializableFileFilter;

/**
 *
 */
public class NoExtentionFileFilter implements SerializableFileFilter
{
    private static final long serialVersionUID = 1L;

    public NoExtentionFileFilter()
    {
    }

    @Override
    public boolean accept( File file )
    {
        return file.getName().indexOf( '.' ) == -1;
    }
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "NoExtentionFileFilter []" );
        return builder.toString();
    }
}
