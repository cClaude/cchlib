package com.googlecode.cchlib.apps.duplicatefiles.console;

import java.io.File;
import java.io.IOException;
import javax.annotation.Nonnull;

public class Console
{
    private Console()
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

    public static void printError( final String string, final IOException e )
    {
        // TODO Auto-generated method stub

    }

}
