package com.googlecode.cchlib.apps.duplicatefiles.console;

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
