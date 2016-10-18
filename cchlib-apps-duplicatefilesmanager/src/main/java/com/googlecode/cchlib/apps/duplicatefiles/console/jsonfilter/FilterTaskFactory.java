package com.googlecode.cchlib.apps.duplicatefiles.console.jsonfilter;

import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.CommandTaskFactory;

/**
 * Factory for {@link FilterTask}
 */
public class FilterTaskFactory implements CommandTaskFactory
{
    @Override
    public FilterTask newInstance( final CLIParameters cli )
            throws CLIParametersException
    {
        return new FilterTask( cli );
    }
}
