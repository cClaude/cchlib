package com.googlecode.cchlib.util;

/**
 * Exception use to identify a process stopped by
 * a cancel request.
 */
public class CancelRequestException extends Exception
{
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new CancelRequestException with null as 
     * its detail message
     */
    public CancelRequestException()
    {
        super();
    }

    /**
     * Constructs a new CancelRequestException with the specified
     * detail message
     * @param message the detail message
     */
    public CancelRequestException( final String message )
    {
        super( message );
    }

    /**
     * Constructs a new exception with the specified cause
     * @param cause the cause 
     */
    public CancelRequestException( final Throwable cause )
    {
        super( cause );
    }

    /**
     * Constructs a new exception with the specified detail 
     * message and cause. 
     * @param message the detail message
     * @param cause the cause 
     */
    public CancelRequestException( final String message, final Throwable cause )
    {
        super( message, cause );
    }
}
