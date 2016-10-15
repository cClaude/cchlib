package com.googlecode.cchlib.apps.duplicatefiles.console.hash;

import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.CommandTask;
import com.googlecode.cchlib.apps.duplicatefiles.console.CommandTaskFactory;

public class HashComputeFactory implements CommandTaskFactory
{
    @Override
    public CommandTask newInstance( final CLIParameters cli )
            throws CLIParametersException
    {
        return new HashCompute( cli );
    }
}
