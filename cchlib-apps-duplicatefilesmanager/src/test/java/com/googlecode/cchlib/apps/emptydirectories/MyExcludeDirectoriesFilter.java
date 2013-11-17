package com.googlecode.cchlib.apps.emptydirectories;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

final class MyExcludeDirectoriesFilter implements FolderFilter, Filter<Path>, FileFilter
{
    private static final String TRASH = "$Recycle.Bin";
    private List<Path> pathList = new ArrayList<Path>();
    private List<File> fileList = new ArrayList<File>();
 
    @Override
    public boolean accept( final Path entry ) throws IOException
    {
        this.pathList.add( entry );

        return entry.getFileName().equals( TRASH );
    }

    @Override
    public boolean accept( File file )
    {
        this.fileList.add( file );

        return TRASH.equals( file.getName() );
    }
    
    public List<Path> getPathList()
    {
        return pathList;
    }

    public List<File> getFileList()
    {
        return fileList;
    }
    
    public Set<Path> createPathSet()
    {
        return new HashSet<Path>( pathList );
    }

    public Set<File> createFileSet()
    {
        return new HashSet<File>( fileList );
    }

    @Override
    public Filter<Path> toFilter()
    {
        return this;
    }

    @Override
    public FileFilter toFileFilter()
    {
        return this;
    }
}