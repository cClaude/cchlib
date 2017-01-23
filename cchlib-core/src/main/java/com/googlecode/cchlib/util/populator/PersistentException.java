package com.googlecode.cchlib.util.populator;

public class PersistentException extends PopulatorRuntimeException
{
    private static final long serialVersionUID = 1L;

    public PersistentException( final String message, final Exception cause )
    {
        super( message, cause );
    }

    public PersistentException( final String message )
    {
        super( message );
    }
}
