package com.googlecode.cchlib.apps.duplicatefiles.console.filterduplicate;

import java.io.File;
import java.util.List;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.CommandTask;
import com.googlecode.cchlib.apps.duplicatefiles.console.JSONLoaderHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.filefilter.HandleFilter;
import com.googlecode.cchlib.apps.duplicatefiles.console.jsonfilter.FilterTask;
import com.googlecode.cchlib.apps.duplicatefiles.console.model.HashFiles;

/**
 * Filter Duplicates JSON list base
 *  - on file filters
 *  - test if files exists
 */
public class DuplicatesFilterTask extends HandleFilter implements CommandTask
{
    private final File inputFile;

    /**
     * Create a {@link FilterTask} based on <code>cli</code>
     *
     * @param cli Parameters from CLI
     * @throws CLIParametersException if any
     */
    public DuplicatesFilterTask( final CLIParameters cli ) throws CLIParametersException
    {
        super( cli );

        this.inputFile = cli.getJsonInputFile();
   }

    @Override
    public List<HashFiles> doTask() throws CLIParametersException
    {
        final List<HashFiles> list = JSONLoaderHelper.loadDuplicate( this.inputFile );

        if( isOnlyDuplicates() ) {
            removeNonDuplicates( list );
        }

        return list;
    }
}
