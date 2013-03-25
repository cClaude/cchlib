package com.googlecode.cchlib.apps.emptydirectories;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.DirectoryStream.Filter;

final class MyExcludeDirectoriesFilter implements Filter<Path>, FileFilter
{
    //private final static Logger logger = Logger.getLogger( MyExcludeDirectoriesFilter.class );

    @Override
    public boolean accept( Path entry ) throws IOException
    {
        return entry.getFileName().equals( "$Recycle.Bin" );
    }

    @Override
    public boolean accept( File pathname )
    {
        return pathname.getName().equals( "$Recycle.Bin" );
    }
}