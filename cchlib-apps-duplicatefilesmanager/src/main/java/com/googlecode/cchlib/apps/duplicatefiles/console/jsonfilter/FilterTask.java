package com.googlecode.cchlib.apps.duplicatefiles.console.jsonfilter;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.CommandTask;
import com.googlecode.cchlib.apps.duplicatefiles.console.JSONLoaderHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.filefilter.HandleFilter;
import com.googlecode.cchlib.apps.duplicatefiles.console.model.HashFiles;

/**
 * Filter JSON list base on file filters
 */
public class FilterTask extends HandleFilter implements CommandTask
{
    private final File inputFile;

    /**
     * Create a {@link FilterTask} based on <code>cli</code>
     *
     * @param cli Parameters from CLI
     * @throws CLIParametersException if any
     */
    public FilterTask( final CLIParameters cli ) throws CLIParametersException
    {
        super( cli );

        this.inputFile = cli.getJsonInputFile();
   }

    @Override
    public List<HashFiles> doTask() throws CLIParametersException
    {
        final List<HashFiles>     list     = JSONLoaderHelper.loadDuplicate( this.inputFile );
        final Iterator<HashFiles> iterator = list.iterator();

        while( iterator.hasNext() ) {
            final HashFiles hf = iterator.next();

            handleFiles( hf );

            if( isOnlyDuplicates() ) {
                //
                // Check size
                //
                if( hf.getFiles().size() < 2 ) {
                    // This not a duplicate
                    iterator.remove();
                }
            } else {
                if( hf.getFiles().isEmpty() ) {
                    // No more files here
                    iterator.remove();
                }
            }
        }

        return list;
    }

    private void handleFiles( final HashFiles files )
    {
        final Iterator<File> iterator = files.getFiles().iterator();
        final long           hLength  = files.getLength();

        while( iterator.hasNext() ) {
            final File file       = iterator.next();
            boolean    removeFile = false;

            if( getFilesFileFilter() != null ) {
                //
                // Handle file filters
                //
                removeFile = shouldRemoveFile( file );
            }

            if( !removeFile ) {
                removeFile = file.length() != hLength;
            }

            if( removeFile ) {
                iterator.remove();

                if( isNotQuiet() ) { // NOSONAR
                    CLIHelper.printMessage( "Ignore:" + file.getPath() );
                }
            }
        }
    }

}
