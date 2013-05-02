package com.googlecode.cchlib.apps.emptydirectories;

public class ScanIOException extends Exception 
{
    private static final long serialVersionUID = 1L;

    public ScanIOException()
    {
        super();
    }

    public ScanIOException( String message, Throwable cause )
    {
        super( message, cause );
    }

    public ScanIOException( String message )
    {
        super( message );
    }

    public ScanIOException( Throwable cause )
    {
        super( cause );
    }

    protected ScanIOException(
        String message,
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace 
        )
    {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}
