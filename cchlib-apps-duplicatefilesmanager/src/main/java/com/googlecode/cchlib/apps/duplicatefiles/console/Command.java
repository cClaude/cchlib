package com.googlecode.cchlib.apps.duplicatefiles.console;

import com.googlecode.cchlib.apps.duplicatefiles.console.filterduplicate.DuplicatesFilterTaskFactory;
import com.googlecode.cchlib.apps.duplicatefiles.console.hash.HashComputeTaskFactory;
import com.googlecode.cchlib.apps.duplicatefiles.console.jsonfilter.FilterTaskFactory;

/**
 * Commands list
 */
public enum Command {
    /**
     * Build HashFiles list
     */
    Hash( HashComputeTaskFactory.class ), // NOSONAR

    /**
     * Filter an existing HashFiles list
     */
    Filter( FilterTaskFactory.class ), // NOSONAR

    /**
     * Filter an existing HashFiles list
     */
    DuplicateFilter( DuplicatesFilterTaskFactory.class ), // NOSONAR
    ;

    private Class<? extends CommandTaskFactory> clazz;

    private Command( final Class<? extends CommandTaskFactory> clazz )
    {
        this.clazz = clazz;
    }

    /**
     * Create a {@link CommandTask} from {@link CLIParameters}
     *
     * @param cli Current {@link CLIParameters} to create task
     * @return current {@link CommandTask}
     * @throws CLIParametersException if any (command not found)
     */
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
