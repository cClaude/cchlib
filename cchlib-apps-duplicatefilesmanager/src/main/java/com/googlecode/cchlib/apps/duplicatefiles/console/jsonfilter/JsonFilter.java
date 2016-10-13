package com.googlecode.cchlib.apps.duplicatefiles.console.jsonfilter;

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.HashFile;
import com.googlecode.cchlib.apps.duplicatefiles.console.JSONHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.JSONHelperException;

public class JsonFilter
{
    private final File       jsonInputFile;
    private final FileFilter directoriesFileFilter;
    private final FileFilter filesFileFilter;

    public JsonFilter( final CLIParameters cli ) throws CLIParametersException
    {
        this.jsonInputFile         = cli.getJsonInputFile();
        this.directoriesFileFilter = cli.getDirectoriesFileFilter();
        this.filesFileFilter       = cli.getFilesFileFilter();
    }

    public List<HashFile> getAllHash() throws JSONHelperException
    {
        final List<HashFile> list = JSONHelper.load(
                this.jsonInputFile,
                new TypeReference<List<HashFile>>() {}
                );

        final Iterator<HashFile> iterator = list.iterator();

        while( iterator.hasNext() ) {
            final HashFile hf = iterator.next();

            if( this.filesFileFilter != null ) {
                boolean removeEntry = false;

                if( this.directoriesFileFilter != null ) {
                    removeEntry = removeEntryAccordingToDirectories( hf.getFile() );
                }
                if( !removeEntry && (this.filesFileFilter != null) ) {
                    removeEntry = removeEntryAccordingToFiles( hf.getFile() );
                }

                if( removeEntry ) {
                    iterator.remove();
                }
            }
        }

        return list;
    }

    private boolean removeEntryAccordingToFiles( final File file )
    {
        return this.filesFileFilter.accept( file );
    }

    private boolean removeEntryAccordingToDirectories( final File file )
    {
        // TODO Auto-generated method stub
        return false;
    }

}
