package com.googlecode.cchlib.apps.duplicatefiles.console.jsonfilter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Iterator;
import java.util.List;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.CommandTask;
import com.googlecode.cchlib.apps.duplicatefiles.console.HashFile;
import com.googlecode.cchlib.apps.duplicatefiles.console.filefilter.FileFiltersConfig;

/**
 *
 */
public class JsonFilterTask
    extends AbstractJsonFilterTask
        implements CommandTask
{
    private final File           jsonInputFile;
    private final FilenameFilter directoriesFileFilter;
    private final FilenameFilter filesFileFilter;
    private final boolean        quiet;
    private final boolean        verbose;

    /**
     * Create a {@link JsonFilterTask} based on <code>cli</code>
     *
     * @param cli Parameters from CLI
     * @throws CLIParametersException if any
     */
    public JsonFilterTask( final CLIParameters cli ) throws CLIParametersException
    {
        this.jsonInputFile = cli.getJsonInputFile();

        final FileFiltersConfig ffc = cli.getFileFiltersConfig();

        this.filesFileFilter       = FileFiltersConfig.getFilenameFilterForFiles( ffc );
        this.directoriesFileFilter = FileFiltersConfig.getFilenameFilterForDirectories( ffc );

        this.verbose = cli.isVerbose();
        this.quiet   = cli.isQuiet();

        if( this.verbose ) {
            CLIHelper.trace( "Files FileFilter", this.filesFileFilter );
            CLIHelper.trace( "Directories FileFilter", this.directoriesFileFilter );
        }
   }

    @Override
    public List<HashFile> doTask() throws CLIParametersException
    {
        final List<HashFile> list = load();

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

                    if( ! this.quiet ) {
                        CLIHelper.printMessage( "Ignore:" + hf.getFile().getPath() );
                    }
                }
            }
        }

        return list;
    }

    private boolean removeEntryAccordingToFiles( final File file )
    {
        return this.filesFileFilter.accept( file.getParentFile(), file.getName() );
    }

    private boolean removeEntryAccordingToDirectories( final File file )
    {
        File currentFile = file.getParentFile();

        while( currentFile != null ) {
            final File currentParent = currentFile.getParentFile();

            if( this.directoriesFileFilter.accept( currentParent, currentFile.getName() ) ) {
                return true;
            }
            currentFile = currentParent;
        }

        return false;
    }

    @Override
    protected File getJsonInputFile()
    {
        return this.jsonInputFile;
    }
}
