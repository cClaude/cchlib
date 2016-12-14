package com.googlecode.cchlib.apps.duplicatefiles.console.tasks;

import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;

@SuppressWarnings("squid:S1609") // Need a super class
public interface CommandTaskFactory<T>
{
    CommandTask<T> newInstance( CLIParameters cli ) throws CLIParametersException;
}
