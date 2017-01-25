package com.googlecode.cchlib.cli.it;

import java.util.Arrays;
import java.util.Set;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.googlecode.cchlib.cli.ConsoleParametersException;
import com.googlecode.cchlib.cli.ConsoleParametersFactory;

public class ConsoleParametersFactoryTest
{
    private static final Logger LOGGER = Logger.getLogger( ConsoleParametersFactoryTest.class );

    public static void main( final String[] args )
        throws ParseException
    {
        LOGGER.info( "main( " + Arrays.toString( args ) + " )" );

        final CLIParametersFactory factory = ConsoleParametersFactory.newInstance( CLIParametersFactory.class, CLIParameters.class );

        try {
            final CLIParameters consoleParams = factory.newConsoleParameters( args );

            if( ! consoleParams.isHelpRequired() ) {
                mainStart( consoleParams );
            }

        } catch( final MissingArgumentException | ConsoleParametersException e ) {
            factory.printHelp( e );
        }
    }

    private static void mainStart( final CLIParameters consoleParams )
        throws ConsoleParametersException
    {
        final AppInstance              app     = new AppInstance( consoleParams );
        final Set<CLIParametersOption> options = consoleParams.getSelectedOptions();

        if( options.contains( CLIParametersOption.ExportDatabase ) ) {
            app.doExport();
        }

        if( options.contains( CLIParametersOption.RemoveMissingFiles ) ) {
            app.doRemoveMissingFiles();
        }
    }

    @Test
    public void test_none() throws ParseException
    {
        main( new String[] {} );
    }

    @Test
    public void test_help() throws ParseException
    {
        main( new String[] { "--help" } );
    }

    @Test
    public void test_with_params() throws ParseException
    {
        main( new String[] {
                "--config",
                "myconfig/MyPicsDBConfig.json",
                "--DeletePeriodes"
                } );
    }

    @Test
    public void test_with_params2() throws ParseException
    {
        main( new String[] {
                "--config",
                "myconfig/MyPicsDBConfig.json",
                "--RemoveMissingFiles",
                "--CheckupRemoveExtraEntries"
                } );
    }
}
