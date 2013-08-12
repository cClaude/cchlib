package com.googlecode.cchlib.swing.batchrunner;

/**
 * 
 * @since 4.1.8
 */
public class BRExecutionException extends Exception 
{
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public BRExecutionException()
    {
    }

    /**
     * @param message
     */
    public BRExecutionException( String message )
    {
        super( message );
    }

    /**
     * @param cause
     */
    public BRExecutionException( Throwable cause )
    {
        super( cause );
    }

    /**
     * @param message
     * @param cause
     */
    public BRExecutionException( String message, Throwable cause )
    {
        super( message, cause );
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public BRExecutionException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace )
    {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}
