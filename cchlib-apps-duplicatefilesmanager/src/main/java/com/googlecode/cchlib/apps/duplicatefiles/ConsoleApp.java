package com.googlecode.cchlib.apps.duplicatefiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.Command;
import com.googlecode.cchlib.apps.duplicatefiles.console.CommandTask;
import com.googlecode.cchlib.apps.duplicatefiles.console.HashFile;
import com.googlecode.cchlib.apps.duplicatefiles.console.JSONHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.JSONHelperException;

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
        final List<HashFile>    hashFiles   = task.doTask();

        if( hashFiles != null ) {
            final File jsonFile = cli.getJsonOutputFile();

            if( jsonFile != null ) {
                createParentDirsOf( jsonFile ); // TODO should be optional

                try {
                    final boolean prettyJson = cli.isPrettyJson();

                    JSONHelper.toJSON( jsonFile, hashFiles, prettyJson );
                }
                catch( final JSONHelperException e ) {
                    CLIHelper.printError( "Error while writing JSON result", jsonFile, e );
                }
            }
        }
    }

    private static void createParentDirsOf( final File file )
        throws CLIParametersException
    {
        final File parentDirFile = file.getParentFile();

        if( ! parentDirFile.exists() ) {
            final Path dir = parentDirFile.toPath();
            try {
                Files.createDirectories( dir );
            }
            catch( final IOException e ) {
                throw new CLIParametersException(
                        "none (can not create parent dir)",
                        parentDirFile.getPath(),
                        e
                        );
            }
        }
   }
}
