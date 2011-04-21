/**
 *
 */
package com.googlecode.cchlib.services;

/**
 *
 * @author Claude CHOISNET
 */
public class SystemUnavailableException extends Exception
{
    private static final long serialVersionUID = 1L;

    public SystemUnavailableException()
    {
    }

    public SystemUnavailableException( String message )
    {
        super( message );
    }

    public SystemUnavailableException( Throwable cause )
    {
        super( cause );
    }

    public SystemUnavailableException( String message, Throwable cause )
    {
        super( message, cause );
    }

}
