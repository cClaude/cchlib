package com.googlecode.cchlib.apps.duplicatefiles.console.taskfilter;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.JSONLoaderHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.model.HashFiles;
import com.googlecode.cchlib.apps.duplicatefiles.console.tasks.CommandTask;
import com.googlecode.cchlib.apps.duplicatefiles.console.tasks.TaskCommon;

/**
 * Filter JSON list base on file filters
 */
public class FilterTask
    extends TaskCommon<HashFiles>
        implements CommandTask<HashFiles>
{
    private final CLIParameters cli;

    /**
     * Create a {@link FilterTask} based on {@code cli}
     *
     * @param cli Parameters from CLI
     * @throws CLIParametersException if any
     */
    public FilterTask( final CLIParameters cli ) throws CLIParametersException
    {
        super( cli );

        this.cli = cli;
   }

    @Override
    public List<HashFiles> doTask() throws CLIParametersException
    {
        final List<HashFiles>     list     = JSONLoaderHelper.loadDuplicate( this.cli );
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

                if( isNotQuiet() ) {
                    CLIHelper.printMessage( "Ignore:" + file.getPath() );
                }
            }
        }
    }

}
