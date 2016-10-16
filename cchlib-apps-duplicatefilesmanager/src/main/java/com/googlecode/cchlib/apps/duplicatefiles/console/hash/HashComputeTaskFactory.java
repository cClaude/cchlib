package com.googlecode.cchlib.apps.duplicatefiles.console.hash;

import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.CommandTaskFactory;

/**
 * Factory for {@link HashComputeTask}
 */
public class HashComputeTaskFactory implements CommandTaskFactory
{
    @Override
    public HashComputeTask newInstance( final CLIParameters cli )
            throws CLIParametersException
    {
        return new HashComputeTask( cli );
    }
}
