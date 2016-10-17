package com.googlecode.cchlib.apps.duplicatefiles.console;

import com.googlecode.cchlib.apps.duplicatefiles.console.duplicate.DuplicateTaskFactory;
import com.googlecode.cchlib.apps.duplicatefiles.console.hash.HashComputeTaskFactory;
import com.googlecode.cchlib.apps.duplicatefiles.console.jsonfilter.JsonFilterTaskFactory;

/**
 *
 */
public enum Command {
    /**
     * Build hash list
     */
    Hash( HashComputeTaskFactory.class ), // NOSONAR

    /**
     * Filter an existing hash list
     */
    Filter( JsonFilterTaskFactory.class ), // NOSONAR

    /**
     * Filter an existing hash list
     */
    Duplicate( DuplicateTaskFactory.class ), // NOSONAR
    ;

    private Class<? extends CommandTaskFactory> clazz;

    private Command( final Class<? extends CommandTaskFactory> clazz )
    {
        this.clazz = clazz;
    }

    public CommandTask newTask( final CLIParameters cli ) throws CLIParametersException
    {
        try {
            return this.clazz.newInstance().newInstance( cli );
        }
        catch( InstantiationException | IllegalAccessException e ) {
            throw new CommandTaskException( e );
        }
    }
}
