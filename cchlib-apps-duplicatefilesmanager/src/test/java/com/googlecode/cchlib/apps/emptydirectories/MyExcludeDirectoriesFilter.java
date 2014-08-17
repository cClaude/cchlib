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
    private static final String[] TRASH = { "$Recycle.Bin", "Trash" };

    private final List<Path> pathList = new ArrayList<>();
    private final List<File> fileList = new ArrayList<>();

    @Override
    public boolean accept( final Path entry ) throws IOException
    {
        if( souldBeExclude( entry ) ) {
            return false;
        }

        this.pathList.add( entry );

        return false;
    }

    private boolean souldBeExclude( final Path path )
    {
        if( isInTrash( path.getFileName().toString() ) ) {
            return true;
        } else if( Files.isSymbolicLink( path ) ) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean accept( final File file )
    {
        if( souldBeExclude( file.toPath() ) ) {
            return false;
        }

        this.fileList.add( file );

        return false;
    }

    private boolean isInTrash( final String filename )
    {
        for( final String name : TRASH ) {
            if( name.equals( filename ) ) {
                return true;
            }
        }
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
