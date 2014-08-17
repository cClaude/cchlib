package com.googlecode.cchlib.apps.emptydirectories;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

final class MyExcludeDirectoriesFilter implements FolderFilter, Filter<Path>, FileFilter
{
    private static final String TRASH = "$Recycle.Bin";

    private final List<Path> pathList = new ArrayList<>();
    private final List<File> fileList = new ArrayList<>();

    @Override
    public boolean accept( final Path entry ) throws IOException
    {
        if( ! TRASH.equals(entry.getFileName() ) ) {
            return false;
        } else if( Files.isSymbolicLink( entry ) ) {
            return false;
        }

        this.pathList.add( entry );

        return false;
    }

    @Override
    public boolean accept( final File file )
    {
        if( ! TRASH.equals( file.getName() ) ) {
            return false;
        } else if( Files.isSymbolicLink( file.toPath() ) ) {
            return false;
        }

        this.fileList.add( file );

        return false;
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
        return new HashSet<>( pathList );
    }

    public Set<File> createFileSet()
    {
        return new HashSet<>( fileList );
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
