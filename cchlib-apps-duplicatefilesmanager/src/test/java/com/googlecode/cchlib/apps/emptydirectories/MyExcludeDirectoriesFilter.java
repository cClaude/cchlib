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

public final class MyExcludeDirectoriesFilter
{
    private static final String[] TRASH = { "$Recycle.Bin", "Trash" };

    private final List<Path> pathList = new ArrayList<>();
    private final List<File> fileList = new ArrayList<>();

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
        final Path fileName = path.getFileName();

        if( fileName == null ) {
            return true;
        } else if( isInTrash( fileName.toString() ) ) {
            return true;
        } else if( Files.isSymbolicLink( path ) ) {
            return true;
        } else {
            return false;
        }
    }

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
        return this.pathList;
    }

    public List<File> getFileList()
    {
        return this.fileList;
    }

    public Set<Path> createPathSet()
    {
        return new HashSet<>( this.pathList );
    }

    public Set<File> createFileSet()
    {
        return new HashSet<>( this.fileList );
    }

    public Filter<Path> toFilter()
    {
        return entry -> MyExcludeDirectoriesFilter.this.accept( entry );
    }

    public FileFilter toFileFilter()
    {
        return pathname -> MyExcludeDirectoriesFilter.this.accept( pathname );
    }

//    public FolderFilter toFolderFilter()
//    {
//        return new FolderFilter() {
//            @Override
//            public Filter<Path> toFilter()
//            {
//                return MyExcludeDirectoriesFilter.this.toFilter();
//            }
//
//            @Override
//            public FileFilter toFileFilter()
//            {
//                return MyExcludeDirectoriesFilter.this.toFileFilter();
//            }
//        };
//    }
}
