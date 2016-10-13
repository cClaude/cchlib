package com.googlecode.cchlib.apps.duplicatefiles;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters.Command;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.HashFile;
import com.googlecode.cchlib.apps.duplicatefiles.console.JSONHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.JSONHelperException;
import com.googlecode.cchlib.apps.duplicatefiles.console.hash.HashCompute;
import com.googlecode.cchlib.apps.duplicatefiles.console.jsonfilter.JsonFilter;

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
            CLIHelper.printErrorAndExit( cli, e );
        }
        catch( final JSONHelperException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void startApp( final CLIParameters cli )
            throws CLIParametersException, NoSuchAlgorithmException, JSONHelperException
    {
        final Command cmd = cli.getCommand();

        switch( cmd ) {
            case Filter:
                doFilter( cli );
                break;
            case Hash:
                doHash( cli );
                break;
        }

    }

    private static void doFilter( final CLIParameters cli )
        throws CLIParametersException, JSONHelperException
    {
        final File              jsonFile      = cli.getJsonOutputFile();
        final JsonFilter        instance      = new JsonFilter( cli );
        final List<HashFile>    hashFiles     = instance.getAllHash();

        if( jsonFile != null ) {
            try {
                JSONHelper.toJSON( jsonFile, hashFiles );
            }
            catch( final JSONHelperException e ) {
                CLIHelper.printError( "Error while writing JSON result", jsonFile, e );
            }
        }
    }

    private static void doHash( final CLIParameters cli )
            throws CLIParametersException, NoSuchAlgorithmException
    {
        final File              jsonFile      = cli.getJsonOutputFile();
        final HashCompute       instance      = new HashCompute( cli );
        final List<HashFile>    hashFiles     = instance.getAllHash();

        if( jsonFile != null ) {
            try {
                JSONHelper.toJSON( jsonFile, hashFiles );
            }
            catch( final JSONHelperException e ) {
                CLIHelper.printError( "Error while writing JSON result", jsonFile, e );
            }
        }
    }
}
