package com.googlecode.cchlib.util.properties;

public class PersistentException extends PropertiesPopulatorRuntimeException
{
    private static final long serialVersionUID = 1L;

    public PersistentException( final String message )
    {
        super( message );
    }
}
