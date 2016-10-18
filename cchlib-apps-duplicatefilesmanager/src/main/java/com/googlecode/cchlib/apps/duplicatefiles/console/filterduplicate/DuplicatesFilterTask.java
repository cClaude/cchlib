package com.googlecode.cchlib.apps.duplicatefiles.console.filterduplicate;

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;
import java.util.List;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.CommandTask;
import com.googlecode.cchlib.apps.duplicatefiles.console.HashFiles;
import com.googlecode.cchlib.apps.duplicatefiles.console.JSONLoaderHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.filefilter.FileFilterFactory;
import com.googlecode.cchlib.apps.duplicatefiles.console.filefilter.FileFiltersConfig;
import com.googlecode.cchlib.apps.duplicatefiles.console.filefilter.HandleFilter;
import com.googlecode.cchlib.apps.duplicatefiles.console.jsonfilter.HashFilterTask;

/**
 * Filter Duplicates JSON list base
 *  - on file filters
 *  - test if files exists
 */
public class DuplicatesFilterTask extends HandleFilter implements CommandTask
{
    private final File       duplicateInputFile;
    private final FileFilter directoriesFileFilter;
    private final FileFilter filesFileFilter;
    private final boolean    notQuiet;
    private final boolean    verbose;

    /**
     * Create a {@link HashFilterTask} based on <code>cli</code>
     *
     * @param cli Parameters from CLI
     * @throws CLIParametersException if any
     */
    public DuplicatesFilterTask( final CLIParameters cli ) throws CLIParametersException
    {
        this.duplicateInputFile = cli.getJsonInputFile();
        this.verbose            = cli.isVerbose();
        this.notQuiet           = !cli.isQuiet();

        final FileFiltersConfig ffc = cli.getFileFiltersConfig();

        this.filesFileFilter       = FileFilterFactory.getFileFilterForFiles( ffc, this.verbose );
        this.directoriesFileFilter = FileFilterFactory.getFileFilterForDirectories( ffc, this.verbose );

        if( this.verbose ) {
            CLIHelper.trace( "Files FileFilter", this.getFilesFileFilter() );
            CLIHelper.trace( "Directories FileFilter", this.directoriesFileFilter );
        }
   }

    @Override
    public List<HashFiles> doTask() throws CLIParametersException
    {
        final List<HashFiles>     list     = JSONLoaderHelper.loadDuplicate( this.duplicateInputFile );
        final Iterator<HashFiles> iterator = list.iterator();

        while( iterator.hasNext() ) {
            final HashFiles hf = iterator.next();

            handleSet( hf );

            if( hf.getFiles().size() < 2 ) {
                // No more a duplicate
                iterator.remove();
            }
        }

        return list;
    }

    private void handleSet( final HashFiles hf )
    {
        final long           length   = hf.getLength();
        final Iterator<File> iterator = hf.getFiles().iterator();

        while( iterator.hasNext() ) {
            final File file = iterator.next();

            if( file.length() != length ) {
                iterator.remove();
            } else {
                // Length ok, look for filters
                if( this.getFilesFileFilter() != null ) {
                    // There is a file filter
                    if( shouldRemoveFile( file ) ) { // NOSONAR
                        iterator.remove();

                        if( this.notQuiet ) { // NOSONAR
                            CLIHelper.printMessage( "Ignore:" + file.getPath() );
                        }
                    }
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
