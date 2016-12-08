package com.googlecode.cchlib.apps.duplicatefiles.console.taskhash;

import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.model.HashFiles;
import com.googlecode.cchlib.apps.duplicatefiles.console.tasks.CommandTaskFactory;

/**
 * Factory for {@link HashComputeTask}
 */
public class HashComputeTaskFactory implements CommandTaskFactory<HashFiles>
{
    @Override
    public HashComputeTask newInstance( final CLIParameters cli )
            throws CLIParametersException
    {
        return new HashComputeTask( cli );
    }
}
