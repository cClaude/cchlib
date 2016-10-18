package com.googlecode.cchlib.apps.duplicatefiles.console;

import java.util.List;
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
}
