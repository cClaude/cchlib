package com.googlecode.cchlib.lang;

/**
 * Exception for debugging/trace propose
 *
 * @since 4.2
 */
public class DebugException extends Exception {
    private static final long serialVersionUID = 1L;

    public DebugException()
    {
        super();
    }

    protected DebugException( final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace )
    {
        super( message, cause, enableSuppression, writableStackTrace );
    }

    public DebugException( final String message, final Throwable cause )
    {
        super( message, cause );
    }

    public DebugException( final String message )
    {
        super( message );
    }

    public DebugException( final Throwable cause )
    {
        super( cause );
    }

}
