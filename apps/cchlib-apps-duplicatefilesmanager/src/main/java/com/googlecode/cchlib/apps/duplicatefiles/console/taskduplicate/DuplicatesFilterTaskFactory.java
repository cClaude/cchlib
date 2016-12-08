package com.googlecode.cchlib.apps.duplicatefiles.console.taskduplicate;

import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.model.HashFiles;
import com.googlecode.cchlib.apps.duplicatefiles.console.tasks.CommandTaskFactory;

/**
 * Factory for {@link DuplicatesFilterTask}
 */
public class DuplicatesFilterTaskFactory implements CommandTaskFactory<HashFiles>
{
    @Override
    public DuplicatesFilterTask newInstance( final CLIParameters cli )
            throws CLIParametersException
    {
        return new DuplicatesFilterTask( cli );
    }
}
