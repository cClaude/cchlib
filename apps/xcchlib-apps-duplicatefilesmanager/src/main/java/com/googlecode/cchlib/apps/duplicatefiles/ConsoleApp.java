package com.googlecode.cchlib.apps.duplicatefiles;

import java.util.List;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.tasks.Command;
import com.googlecode.cchlib.apps.duplicatefiles.console.tasks.CommandTask;

/**
 * Handle console mode
 */
@SuppressWarnings("ucd") // CLI entry point
public class ConsoleApp
{
    private ConsoleApp()
    {
        // static only
    }

    /**
     * Launch application in console mode
     *
     * @param args Parameters from command line
     */
    public static void main( final String[] args )
    {
        final CLIParameters cli = new CLIParameters();

        try {
            cli.parseArguments( args );

            if( cli.getCommandLine() != null ) {
                startApp( cli );
            }
        }
        catch( final CLIParametersException e ) {
            CLIHelper.printErrorAndExit( cli, e );
        }
    }

    private static <T> void startApp( final CLIParameters cli )
        throws CLIParametersException
    {
        final Command        cmd        = cli.getCommand();
        final CommandTask<T> task       = cmd.newTask( cli );
        final List<T>        listResult = task.doTask();

        task.saveResultIfRequired( listResult );
    }
}
