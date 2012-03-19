package com.googlecode.cchlib.util;

/**
 * Use by {@link Wrappable}
 *
 * 'cause' must be always valid
 * @since 4.1.7
 */
public class WrappeException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public WrappeException( Throwable cause )
    {
        super( cause );
    }
    
    public WrappeException( String message, Throwable cause )
    {
        super( message, cause );
    }

}
