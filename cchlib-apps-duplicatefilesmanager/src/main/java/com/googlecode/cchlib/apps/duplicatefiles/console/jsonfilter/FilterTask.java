package com.googlecode.cchlib.apps.duplicatefiles.console.jsonfilter;

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;
import java.util.List;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.CommandTask;
import com.googlecode.cchlib.apps.duplicatefiles.console.JSONLoaderHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.filefilter.FileFilterFactory;
import com.googlecode.cchlib.apps.duplicatefiles.console.filefilter.FileFiltersConfig;
import com.googlecode.cchlib.apps.duplicatefiles.console.filefilter.HandleFilter;
import com.googlecode.cchlib.apps.duplicatefiles.console.model.HashFiles;

/**
 * Filter JSON list base on file filters
 */
public class FilterTask extends HandleFilter implements CommandTask
{
    private final File       inputFile;
    private final FileFilter directoriesFileFilter;
    private final FileFilter filesFileFilter;
    private final boolean    notQuiet;
    private final boolean    verbose;
    private final boolean    removeNonDuplicates;

    /**
     * Create a {@link FilterTask} based on <code>cli</code>
     *
     * @param cli Parameters from CLI
     * @throws CLIParametersException if any
     */
    public FilterTask( final CLIParameters cli ) throws CLIParametersException
    {
        this.inputFile           = cli.getJsonInputFile();
        this.verbose             = cli.isVerbose();
        this.notQuiet            = !cli.isQuiet();
        this.removeNonDuplicates = cli.isOnlyDuplicates();

        final FileFiltersConfig ffc = cli.getFileFiltersConfig();

        this.filesFileFilter       = FileFilterFactory.getFileFilterForFiles( ffc, this.verbose );
        this.directoriesFileFilter = FileFilterFactory.getFileFilterForDirectories( ffc, this.verbose );

        if( this.verbose ) {
            CLIHelper.trace( "Files FileFilter", this.filesFileFilter );
            CLIHelper.trace( "Directories FileFilter", this.directoriesFileFilter );
        }
   }

    @Override
    public List<HashFiles> doTask() throws CLIParametersException
    {
        final List<HashFiles>     list     = JSONLoaderHelper.loadDuplicate( this.inputFile );
        final Iterator<HashFiles> iterator = list.iterator();

        while( iterator.hasNext() ) {
            final HashFiles hf = iterator.next();

            handleFiles( hf );

            if( this.removeNonDuplicates ) {
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

            if( this.filesFileFilter != null ) {
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

                if( this.notQuiet ) { // NOSONAR
                    CLIHelper.printMessage( "Ignore:" + file.getPath() );
                }
            }
        }
    }

    @Override
    protected FileFilter getDirectoriesFileFilter()
    {
        return this.directoriesFileFilter;
    }

    @Override
    protected FileFilter getFilesFileFilter()
    {
        return this.filesFileFilter;
    }
}
