package com.googlecode.cchlib.apps.duplicatefiles.console.jsonfilter;

import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.CommandTaskFactory;

/**
 * Factory for {@link HashFilterTask}
 */
public class HashFilterTaskFactory implements CommandTaskFactory
{
    @Override
    public HashFilterTask newInstance( final CLIParameters cli )
            throws CLIParametersException
    {
        return new HashFilterTask( cli );
    }
}
