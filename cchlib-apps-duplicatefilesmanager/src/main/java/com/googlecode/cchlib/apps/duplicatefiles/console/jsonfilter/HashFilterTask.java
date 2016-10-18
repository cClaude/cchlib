package com.googlecode.cchlib.apps.duplicatefiles.console.jsonfilter;

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;
import java.util.List;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.CommandTask;
import com.googlecode.cchlib.apps.duplicatefiles.console.HashFile;
import com.googlecode.cchlib.apps.duplicatefiles.console.JSONLoaderHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.filefilter.FileFilterFactory;
import com.googlecode.cchlib.apps.duplicatefiles.console.filefilter.FileFiltersConfig;
import com.googlecode.cchlib.apps.duplicatefiles.console.filefilter.HandleFilter;

/**
 * Filter JSON list base on file filters
 */
public class HashFilterTask extends HandleFilter implements CommandTask
{
    private final File       jsonInputFile;
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
    public HashFilterTask( final CLIParameters cli ) throws CLIParametersException
    {
        this.jsonInputFile = cli.getJsonInputFile();
        this.verbose       = cli.isVerbose();
        this.notQuiet      = !cli.isQuiet();

        final FileFiltersConfig ffc = cli.getFileFiltersConfig();

        this.filesFileFilter       = FileFilterFactory.getFileFilterForFiles( ffc, this.verbose );
        this.directoriesFileFilter = FileFilterFactory.getFileFilterForDirectories( ffc, this.verbose );

        if( this.verbose ) {
            CLIHelper.trace( "Files FileFilter", this.filesFileFilter );
            CLIHelper.trace( "Directories FileFilter", this.directoriesFileFilter );
        }
   }

    @Override
    public List<HashFile> doTask() throws CLIParametersException
    {
        final List<HashFile>     list     = JSONLoaderHelper.loadHash( this.jsonInputFile );
        final Iterator<HashFile> iterator = list.iterator();

        while( iterator.hasNext() ) {
            final HashFile hf = iterator.next();

            if( this.filesFileFilter != null ) {

                if( shouldRemoveFile( hf.getFile() ) ) {
                    iterator.remove();

                    if( this.notQuiet ) { // NOSONAR
                        CLIHelper.printMessage( "Ignore:" + hf.getFile().getPath() );
                    }
                }
            }
        }

        return list;
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
