package com.googlecode.cchlib.apps.duplicatefiles.console;

import java.io.File;
import javax.annotation.Nonnull;

public class CLIHelper
{
    private CLIHelper()
    {
        // All static
    }

    public static void printError( final String message )
    {
        System.err.println( message );// NOSONAR
    }

    public static void printError( //
        @Nonnull final String    message,
        @Nonnull final File      file,
        @Nonnull final Exception cause
        )
    {
        printError( message + " '" + file + "' : " + cause.getMessage() );
    }

    @SuppressWarnings("null")
    public static <T> T exit( final int exitStatus )
    {
        System.exit( exitStatus ); // NOSONAR

        return null; // Fake value
    }

    public static void printMessage( final String msg )
    {
        System.out.println( msg ); // NOSONAR
    }

    public static void printError( final String msg, final Exception cause )
    {
        printMessage( msg + " - " + cause.getMessage() );
    }

    public static void trace( final Object object )
    {
        printMessage( "" + object );
    }

    public static void printError( final CLIParameters cli, final CLIParametersException cause )
    {
        final StringBuilder sb = new StringBuilder();


        printError( cause.getCliMessage() );

        cli.printHelp();
        exit( 1 );
    }

}
