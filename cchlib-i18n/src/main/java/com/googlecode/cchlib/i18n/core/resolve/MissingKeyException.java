package com.googlecode.cchlib.i18n.core.resolve;

/**
 *
 */
public class MissingKeyException extends FieldException 
{
    private static final long serialVersionUID = 1L;

    public MissingKeyException( String message, Throwable cause )
    {
        super( message, cause );
    }

    public MissingKeyException( Throwable cause )
    {
        super( cause );
    }
}