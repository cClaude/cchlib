package com.googlecode.cchlib.apps.duplicatefiles.console;

import java.io.File;
import javax.annotation.Nonnull;

/**
 * Handle console output
 */
public class CLIHelper
{
    private CLIHelper()
    {
        // All static
    }

    /**
     * Print {@code message} to stdout
     *
     * @param message Message to print
     */
    @SuppressWarnings("squid:S106")
    public static void printMessage( final String message )
    {
        System.out.println( message );
    }

    /**
     * Print {@code message} to stderr
     *
     * @param message Message to print
     */
    @SuppressWarnings("squid:S106")
    public static void printError( final String message )
    {
        System.err.println( message );
    }

    /**
     * Print {@code message} and {@code cause} related to a file to stderr
     *
     * @param message Message to print
     * @param file    Related file
     * @param cause   Related cause
     */
    public static void printError(
        @Nonnull final String    message,
        @Nonnull final File      file,
        @Nonnull final Exception cause
        )
    {
        printError( message + " '" + file + "' : " + cause.getMessage() );
    }

    /**
     * Print {@code message} and {@code cause} to stderr
     *
     * @param message Message to print
     * @param cause   Related cause
     */
    public static void printError( final String message, final Exception cause )
    {
        printError( message + " - " + cause.getMessage() );
    }

    /**
     * Print a {@code description} and {@code object} to stderr
     *
     * @param description Message to print
     * @param object      Related object
     */
    public static void trace( final String description, final Object object )
    {
        printError( "INFO: " + description + " : " + object );
    }

    /**
     * Print error to stdout then print help and quit
     *
     * @param cli   Current CLIParameters
     * @param clipe Exception to expose
     */
    public static void printErrorAndExit( final CLIParameters cli, final CLIParametersException clipe )
    {
        final StringBuilder sb = new StringBuilder();

        if( clipe.getParameterName() != null ) {
            sb.append( clipe.getParameterName() );
            sb.append( " : " );
            sb.append( clipe.getCliMessage() );
        }

        if( clipe.getCause() != null ) {
            sb.append( " - cause by " );
            sb.append( clipe.getCause().getMessage() );
        }

        printError( sb.toString() );
        cli.printHelp();
        exit( 1 );
    }

    @SuppressWarnings("squid:S1147")
    private static void exit( final int exitStatus )
    {
        System.exit( exitStatus );
    }
}
