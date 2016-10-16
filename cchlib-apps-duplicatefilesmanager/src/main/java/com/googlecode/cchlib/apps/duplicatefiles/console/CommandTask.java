package com.googlecode.cchlib.apps.duplicatefiles.console;

import java.util.List;

/**
 * Handle --command option
 */
public interface CommandTask
{
    /**
     * Run related task for this command
     *
     * @return related result
     * @throws CommandTaskException if any
     * @throws CLIParametersException if any
     */
    List<HashFile> doTask()
        throws
            CommandTaskException, // NOSONAR
            CLIParametersException;
}
