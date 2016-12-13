package com.googlecode.cchlib.swing.batchrunner;

/**
 * @since 4.1.8
 */
public class BRExecutionException extends Exception
{
    private static final long serialVersionUID = 1L;

    public BRExecutionException( final String message )
    {
        super( message );
    }

    public BRExecutionException( final Throwable cause )
    {
        super( cause );
    }

    public BRExecutionException( final String message, final Throwable cause )
    {
        super( message, cause );
    }

    public BRExecutionException(
        final String    message,
        final Throwable cause,
        final boolean   enableSuppression,
        final boolean   writableStackTrace
        )
    {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}
