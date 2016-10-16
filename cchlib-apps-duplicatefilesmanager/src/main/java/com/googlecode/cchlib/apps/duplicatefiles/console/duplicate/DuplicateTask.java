package com.googlecode.cchlib.apps.duplicatefiles.console.duplicate;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.CommandTask;
import com.googlecode.cchlib.apps.duplicatefiles.console.CommandTaskException;
import com.googlecode.cchlib.apps.duplicatefiles.console.HashFile;
import com.googlecode.cchlib.apps.duplicatefiles.console.filefilter.FileFiltersConfig;
import com.googlecode.cchlib.apps.duplicatefiles.console.jsonfilter.AbstractJsonFilterTask;

public class DuplicateTask
    extends AbstractJsonFilterTask
        implements CommandTask
{
    private final boolean        verbose;
    private final boolean        notQuiet;
    private final FilenameFilter directoriesFileFilter;
    private final FilenameFilter filesFileFilter;
    private final File           jsonInputFile;

    public DuplicateTask( final CLIParameters cli ) throws CLIParametersException
    {
        this.jsonInputFile = cli.getJsonInputFile();

        final FileFiltersConfig ffc = cli.getFileFiltersConfig();

        this.filesFileFilter       = FileFiltersConfig.getFilenameFilterForFiles( ffc );
        this.directoriesFileFilter = FileFiltersConfig.getFilenameFilterForDirectories( ffc );

        this.verbose  = cli.isVerbose();
        this.notQuiet = ! cli.isQuiet();

        if( this.verbose ) {
            CLIHelper.trace( "Files FileFilter", this.filesFileFilter );
            CLIHelper.trace( "Directories FileFilter", this.directoriesFileFilter );
        }
    }

    @Override
    public List<HashFile> doTask() throws CommandTaskException, CLIParametersException
    {
        final List<HashFile> list = load();

        Collections.sort(
            list,
            ( hf1, hf2 ) -> hf1.getHash().compareTo( hf2.getHash() )
            );

        final List<HashFile> result       = new ArrayList<>();
        HashFile             prevHashFile = null;
        String               prevHash     = null;

        for( final HashFile hf : list ) {
            if( (prevHash != null) && (hf.getHash().compareTo( prevHash ) == 0) ) {
                // At least 2 occurrences.
                if( prevHashFile != null ) {
                    // Previous occurrences not yet saved
                    result.add( prevHashFile );

                    if( this.notQuiet ) {
                        CLIHelper.printMessage( prevHashFile.toString() );
                    }

                    prevHashFile = null;
                }
                result.add( hf );

                if( this.notQuiet ) {
                    CLIHelper.printMessage( hf.toString() );
                }
            } else {
                if( prevHashFile != null ) {
                    // Previous value not stored
                    if( this.verbose ) {
                        CLIHelper.trace( "#Ignore", prevHashFile );
                    }
                }
                // New file - store values
                prevHashFile = hf;
                prevHash     = hf.getHash();
            }
        }

        return result;
    }

    @Override
    protected File getJsonInputFile()
    {
        return this.jsonInputFile;
    }
}
