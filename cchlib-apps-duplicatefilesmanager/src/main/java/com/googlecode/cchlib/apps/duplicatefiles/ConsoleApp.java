package com.googlecode.cchlib.apps.duplicatefiles;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.HashCompute;
import com.googlecode.cchlib.apps.duplicatefiles.console.HashFile;
import com.googlecode.cchlib.apps.duplicatefiles.console.Json;

/**
 * Handle console mode
 */
public class ConsoleApp
{
    private static final Logger LOGGER = Logger.getLogger( DuplicateFilesApp.class );

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
    public static void main( final String[] args ) throws NoSuchAlgorithmException
    {
        final CLIParameters cli = new CLIParameters();

        try {
            cli.parseArguments( args );

            if( cli.getCommandLine() != null ) {
                startApp( cli );
            }
        }
        catch( final CLIParametersException e ) {
            CLIHelper.printError( cli, e );
        }
    }

    private static void startApp( final CLIParameters cli ) throws CLIParametersException, NoSuchAlgorithmException
    {
        final File jsonFile = cli.getJSONFile();

        final String cmd = null;//commandLine.getOptionValue( COMMAND );

        if( (cmd == null) || cmd.equalsIgnoreCase( "hashlist" ) ) {
            final HashCompute       instance      = new HashCompute( cli );
            final List<HashFile>    hashFiles     = instance.getAllHash();

            if( jsonFile != null ) {
                Json.toJSONSafe( jsonFile, hashFiles );
            }
        }
    }
}
