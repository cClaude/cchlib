package com.googlecode.cchlib.apps.duplicatefiles.console;

public interface CommandTaskFactory {

    CommandTask newInstance( CLIParameters cli ) throws CLIParametersException;

}
