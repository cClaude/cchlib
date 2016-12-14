package com.googlecode.cchlib.apps.duplicatefiles.console.tasks.tools;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import com.googlecode.cchlib.io.DirectoryIterator;

public class CollectFileInternalTask<T>
{
    public interface Config
    {
        File       getDirectoryFile();
        FileFilter getDirectoriesFileFilter();
        FileFilter getFilesFileFilter();
    }

    @FunctionalInterface
    public interface FileHandler<T>
    {
        T handleFile( File file );
    }

    private final Config         config;
    private final FileHandler<T> fileHandler;
    private final List<T>        listResult;

    public CollectFileInternalTask(
        final Config         config,
        final FileHandler<T> fileHandler
        )
    {
        this.config       = config;
        this.fileHandler  = fileHandler;
        this.listResult   = new ArrayList<>();
    }

    public List<T> runDirectories()
    {
        final DirectoryIterator iter = new DirectoryIterator(
                this.config.getDirectoryFile(),
                this.config.getDirectoriesFileFilter()
                );

        while( iter.hasNext() ) {
            final File   dir   = iter.next();
            final File[] files = dir.listFiles( this.config.getFilesFileFilter() );

            if( files != null ) {
                runFiles( files );
            }
        }

        return this.listResult;
    }

    private void runFiles( final File[] files )
    {
        for( final File file : files ) {
            if( file.isFile() ) {
                final T result = this.fileHandler.handleFile( file );

                if( result != null ) {
                    this.listResult.add( result );
                }
            }
        }
    }
}
