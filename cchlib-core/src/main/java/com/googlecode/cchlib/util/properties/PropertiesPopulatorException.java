package com.googlecode.cchlib.util.properties;

/**
 *
 */
public class PropertiesPopulatorException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    protected PropertiesPopulatorException( final String message )
    {
        super( message );
    }

    protected PropertiesPopulatorException( final Throwable cause )
    {
        super( cause );
    }

    protected PropertiesPopulatorException( final String message, final Throwable cause )
    {
        super( message, cause );
    }

}
