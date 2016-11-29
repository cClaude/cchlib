package com.googlecode.cchlib.cli;

import java.io.File;

public class ConsoleParametersException extends Exception
{
    private static final long serialVersionUID = 1L;

    ConsoleParametersException( final String param )
    {
        super( param );
    }

    public ConsoleParametersException( final String param, final String message )
    {
        super( "Bad parameter value : " + param + " - " + message );
    }

    public <E extends Enum<E>> ConsoleParametersException( final E option, final String message )
    {
        this( option.toString(), message );
    }

    public static <E extends Enum<E>> ConsoleParametersException newFileNotFoundConsoleParametersException(
            final E    option,
            final File file
            )
    {
        return newFileNotFoundConsoleParametersException( option.toString(), file );
    }

    public static <E extends Enum<E>> ConsoleParametersException newFileNotFoundConsoleParametersException(
            final String param,
            final File   file
            )
    {
        return new ConsoleParametersException( param, "File not found: " + file );
    }
}
