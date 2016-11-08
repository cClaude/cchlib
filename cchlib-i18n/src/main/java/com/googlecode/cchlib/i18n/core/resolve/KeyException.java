package com.googlecode.cchlib.i18n.core.resolve;

public class KeyException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public KeyException( String message, Throwable cause )
    {
        super( message, cause );
    }

    public KeyException( Throwable cause )
    {
        super( cause );
    }
}
