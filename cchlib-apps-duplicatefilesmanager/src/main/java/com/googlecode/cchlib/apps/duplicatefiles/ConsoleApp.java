package com.googlecode.cchlib.apps.duplicatefiles;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIHelperException;
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
     * @throws CLIHelperException
     * @throws NoSuchAlgorithmException
     */
    public static void main( final String[] args ) throws CLIHelperException, NoSuchAlgorithmException
    {
        final CLIHelper   cliHelper = new CLIHelper();

        cliHelper.parseArguments( args );

        if( cliHelper.getCommandLine() != null ) {
            startApp( cliHelper );
        }
    }

    private static void startApp( final CLIHelper cliHelper ) throws NoSuchAlgorithmException
    {
        final File jsonFile = cliHelper.getJSONFile();

        final String cmd = null;//commandLine.getOptionValue( COMMAND );

        if( (cmd == null) || cmd.equalsIgnoreCase( "hashlist" ) ) {
            final HashCompute       instance      = new HashCompute( cliHelper );
            final List<HashFile>    hashFiles     = instance.getAllHash();

            if( jsonFile != null ) {
                Json.toJSONSafe( jsonFile, hashFiles );
            }
        }
    }
}
