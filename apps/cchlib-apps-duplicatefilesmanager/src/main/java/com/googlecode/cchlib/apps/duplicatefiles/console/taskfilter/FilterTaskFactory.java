package com.googlecode.cchlib.apps.duplicatefiles.console.taskfilter;

import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.model.HashFiles;
import com.googlecode.cchlib.apps.duplicatefiles.console.tasks.CommandTaskFactory;

/**
 * Factory for {@link FilterTask}
 */
public class FilterTaskFactory implements CommandTaskFactory<HashFiles>
{
    @Override
    public FilterTask newInstance( final CLIParameters cli )
            throws CLIParametersException
    {
        return new FilterTask( cli );
    }
}
