package com.googlecode.cchlib.apps.duplicatefiles.console.tasks;

import java.util.List;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;

/**
 * Handle --command option
 */
public interface CommandTask<R>
{
    /**
     * Run related task for this command
     *
     * @return related result
     * @throws CommandTaskException if any
     * @throws CLIParametersException if any
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    List<R> doTask()
        throws
            CommandTaskException,
            CLIParametersException;

    /**
     * Store <code>listResult</code> to JSON file
     *
     * @param listResult Result to store
     * @throws CLIParametersException if any
     */
    void saveResultIfRequired( List<R> listResult )
        throws CLIParametersException;
}
