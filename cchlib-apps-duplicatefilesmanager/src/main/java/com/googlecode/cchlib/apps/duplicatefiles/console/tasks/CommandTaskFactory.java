package com.googlecode.cchlib.apps.duplicatefiles.console.tasks;

import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;

/**
 *
 */
public interface CommandTaskFactory
{
    /**
     *
     * @param cli
     * @return
     * @throws CLIParametersException
     */
    CommandTask newInstance( CLIParameters cli ) throws CLIParametersException;
}
