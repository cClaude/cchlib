package com.googlecode.cchlib.apps.duplicatefiles.console.tasks;

import java.util.List;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.model.HashFiles;

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
    List<HashFiles> doTask()
        throws
            CommandTaskException, // NOSONAR
            CLIParametersException;

    /**
     * Store <code>listResult</code> to JSON file
     *
     * @param listResult Result to store
     * @throws CLIParametersException if any
     */
    void saveResultIfRequired( List<HashFiles> listResult )
        throws CLIParametersException;
}
