package com.googlecode.cchlib.lang;

/**
 * Invoke when current platform is not supported
 * 
 * @since 4.1.7
 */
public class UnsupportedSystemException extends Exception 
{
    private static final long serialVersionUID = 1L;

    protected UnsupportedSystemException()
    {
        super();
    }
    
    public UnsupportedSystemException( String message )
    {
        super( message );
    }

    public UnsupportedSystemException( Throwable cause )
    {
        super( cause );
    }

    public UnsupportedSystemException( String message, Throwable cause )
    {
        super( message, cause );
    }
}
