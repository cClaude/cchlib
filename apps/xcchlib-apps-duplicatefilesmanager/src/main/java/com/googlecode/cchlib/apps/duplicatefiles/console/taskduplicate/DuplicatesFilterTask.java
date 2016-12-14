package com.googlecode.cchlib.apps.duplicatefiles.console.taskduplicate;

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
    private final CLIParameters cli;

    /**
     * Create a {@link FilterTask} based on {@code cli}
     *
     * @param cli Parameters from CLI
     * @throws CLIParametersException if any
     */
    DuplicatesFilterTask( final CLIParameters cli ) throws CLIParametersException
    {
        super( cli );

        this.cli = cli;
   }

    @Override
    public List<HashFiles> doTask() throws CLIParametersException
    {
        final List<HashFiles> list = JSONLoaderHelper.loadDuplicate( this.cli );

        if( isOnlyDuplicates() ) {
            removeNonDuplicates( list );
        }

        return list;
    }
}
