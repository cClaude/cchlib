package com.googlecode.cchlib.apps.duplicatefiles.console.jsonfilter;

import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.CommandTask;
import com.googlecode.cchlib.apps.duplicatefiles.console.CommandTaskFactory;

public class JsonFilterFactory implements CommandTaskFactory
{
    @Override
    public CommandTask newInstance( final CLIParameters cli )
            throws CLIParametersException
    {
        return new JsonFilter( cli );
    }
}
