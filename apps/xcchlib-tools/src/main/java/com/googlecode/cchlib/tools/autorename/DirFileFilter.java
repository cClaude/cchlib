package com.googlecode.cchlib.tools.autorename;

import java.io.File;
import java.io.FileFilter;

public class DirFileFilter implements FileFilter
{
    public DirFileFilter()
    {
        // Empty
    }

    @Override
    public boolean accept( final File file )
    {
        return file.isDirectory();
    }
}
