package com.googlecode.cchlib.util.properties;

public class PropertiesPopulatorRuntimeException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    protected PropertiesPopulatorRuntimeException( final String message )
    {
        super( message );
    }

    protected PropertiesPopulatorRuntimeException( final Throwable cause )
    {
        super( cause );
    }

    protected PropertiesPopulatorRuntimeException( final String message, final Throwable cause )
    {
        super( message, cause );
    }
}
