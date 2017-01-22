package com.googlecode.cchlib.util.populator;

public class PopulatorRuntimeException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    protected PopulatorRuntimeException( final String message )
    {
        super( message );
    }

    protected PopulatorRuntimeException( final Throwable cause )
    {
        super( cause );
    }

    protected PopulatorRuntimeException( final String message, final Throwable cause )
    {
        super( message, cause );
    }
}
