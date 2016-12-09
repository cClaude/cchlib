package com.googlecode.cchlib.apps.duplicatefiles.console.taskquicklist;

import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.taskquicklist.model.AbstractFileInfo;
import com.googlecode.cchlib.apps.duplicatefiles.console.tasks.CommandTaskFactory;

/**
 * Factory for {@link QuickListTask}
 */
public class QuickListTaskFactory implements CommandTaskFactory<AbstractFileInfo>
{
    @Override
    public QuickListTask newInstance( final CLIParameters cli )
            throws CLIParametersException
    {
        return new QuickListTask( cli );
    }
}
