package com.googlecode.cchlib.apps.duplicatefiles.console.duplicate;

import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.CommandTaskFactory;

/**
 * Factory for {@link DuplicateTask}
 */
public class DuplicateTaskFactory implements CommandTaskFactory {

    @Override
    public DuplicateTask newInstance( final CLIParameters cli ) throws CLIParametersException
    {
        return new DuplicateTask( cli );
    }

}
