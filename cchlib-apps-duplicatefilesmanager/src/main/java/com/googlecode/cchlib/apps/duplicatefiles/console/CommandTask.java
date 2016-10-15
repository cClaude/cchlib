package com.googlecode.cchlib.apps.duplicatefiles.console;

import java.util.List;

public interface CommandTask
{
    /**
     *
     * @return
     * @throws CommandTaskException
     * @throws CLIParametersException
     */
    List<HashFile> doTask()
        throws
            CommandTaskException, // NOSONAR
            CLIParametersException;
}
