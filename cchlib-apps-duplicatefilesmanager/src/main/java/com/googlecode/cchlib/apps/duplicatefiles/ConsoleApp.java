package com.googlecode.cchlib.apps.duplicatefiles;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.model.HashFiles;
import com.googlecode.cchlib.apps.duplicatefiles.console.tasks.Command;
import com.googlecode.cchlib.apps.duplicatefiles.console.tasks.CommandTask;

/**
 * Handle console mode
 */
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
     * @throws CLIParametersException
     * @throws NoSuchAlgorithmException
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

    private static void startApp( final CLIParameters cli )
        throws CLIParametersException
    {
        final Command           cmd         = cli.getCommand();
        final CommandTask       task        = cmd.newTask( cli );
        final List<HashFiles>   listResult  = task.doTask();

        task.saveResultIfRequired( listResult );
    }
}
