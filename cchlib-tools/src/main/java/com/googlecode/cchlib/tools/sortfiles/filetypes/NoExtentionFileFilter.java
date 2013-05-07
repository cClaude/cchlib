package com.googlecode.cchlib.tools.sortfiles.filetypes;

import java.io.File;

/**
 *
 */
public class NoExtentionFileFilter implements XFileFilter
{
    private static final long serialVersionUID = 1L;

    public NoExtentionFileFilter()
    {
    }

    @Override
    public boolean accept( File file )
    {
        return file.isFile() && file.getName().indexOf( '.' ) == -1;
    }

    @Override
    public String toDisplay()
    {
        return "NoExtentionFileFilter []";
    }
}
