package com.googlecode.cchlib.apps.duplicatefiles.console.taskduplicate;

import java.io.File;
import java.util.List;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.JSONLoaderHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.model.HashFiles;
import com.googlecode.cchlib.apps.duplicatefiles.console.taskfilter.FilterTask;
import com.googlecode.cchlib.apps.duplicatefiles.console.tasks.CommandTask;
import com.googlecode.cchlib.apps.duplicatefiles.console.tasks.TaskCommon;

/**
 * Filter Duplicates JSON list base
 *  - on file filters
 *  - test if files exists
 */
class DuplicatesFilterTask
    extends TaskCommon<HashFiles>
        implements CommandTask<HashFiles>
{
    private final File inputFile;

    /**
     * Create a {@link FilterTask} based on <code>cli</code>
     *
     * @param cli Parameters from CLI
     * @throws CLIParametersException if any
     */
    DuplicatesFilterTask( final CLIParameters cli ) throws CLIParametersException
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
