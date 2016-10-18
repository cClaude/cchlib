package com.googlecode.cchlib.apps.duplicatefiles.console;

import java.util.List;

/**
 * Handle --command option
 */
public interface CommandTask // NOSONAR
{
    /**
     * Run related task for this command
     *
     * @return related result
     * @throws CommandTaskException if any
     * @throws CLIParametersException if any
     */
    List<?> doTask()
        throws
            CommandTaskException, // NOSONAR
            CLIParametersException;
}
