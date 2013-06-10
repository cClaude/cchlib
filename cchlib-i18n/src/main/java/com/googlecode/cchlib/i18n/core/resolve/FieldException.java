package com.googlecode.cchlib.i18n.core.resolve;

/**
 *
 */
public class FieldException extends Exception
{
    private static final long serialVersionUID = 1L;

    public FieldException( String message, Throwable cause )
    {
        super( message, cause );
    }
    
    public FieldException( Throwable cause )
    {
        super( cause );
    }
}
