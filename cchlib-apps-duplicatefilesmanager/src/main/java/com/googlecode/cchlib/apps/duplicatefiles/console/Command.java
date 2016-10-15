package com.googlecode.cchlib.apps.duplicatefiles.console;

import com.googlecode.cchlib.apps.duplicatefiles.console.hash.HashComputeFactory;
import com.googlecode.cchlib.apps.duplicatefiles.console.jsonfilter.JsonFilterFactory;

/**
 *
 */
public enum Command {
    /**
     *
     */
    Hash( HashComputeFactory.class ),
    /**
     *
     */
    Filter( JsonFilterFactory.class ),
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
