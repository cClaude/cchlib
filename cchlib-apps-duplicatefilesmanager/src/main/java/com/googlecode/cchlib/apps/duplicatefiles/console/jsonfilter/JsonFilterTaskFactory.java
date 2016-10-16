package com.googlecode.cchlib.apps.duplicatefiles.console.jsonfilter;

import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.CommandTaskFactory;

/**
 * Factory for {@link JsonFilterTask}
 */
public class JsonFilterTaskFactory implements CommandTaskFactory
{
    @Override
    public JsonFilterTask newInstance( final CLIParameters cli )
            throws CLIParametersException
    {
        return new JsonFilterTask( cli );
    }
}
