package com.googlecode.cchlib.apps.duplicatefiles.console.tasks;

import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.taskduplicate.DuplicatesFilterTaskFactory;
import com.googlecode.cchlib.apps.duplicatefiles.console.taskfilter.FilterTaskFactory;
import com.googlecode.cchlib.apps.duplicatefiles.console.taskhash.HashComputeTaskFactory;

/**
 * Commands list
 */
@SuppressWarnings("squid:S00115") // use for command line.
public enum Command
{
    /**
     * Build HashFiles list
     */
    Hash( HashComputeTaskFactory.class ),

    /**
     * Filter an existing HashFiles list
     */
    Filter( FilterTaskFactory.class ),

    /**
     * Filter an existing HashFiles list
     */
    DuplicateFilter( DuplicatesFilterTaskFactory.class ),

    ;

    private Class<? extends CommandTaskFactory<?>> clazz;

    private Command( final Class<? extends CommandTaskFactory<?>> clazz )
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
    @SuppressWarnings("unchecked")
    public <R> CommandTask<R> newTask( final CLIParameters cli ) throws CLIParametersException
    {
        try {
            return (CommandTask<R>)this.clazz.newInstance().newInstance( cli );
        }
        catch( InstantiationException | IllegalAccessException e ) {
            throw new CommandTaskException( e );
        }
    }
}
