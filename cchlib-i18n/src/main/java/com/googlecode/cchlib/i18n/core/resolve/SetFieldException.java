package com.googlecode.cchlib.i18n.core.resolve;

public class SetFieldException extends FieldException 
{
    private static final long serialVersionUID = 1L;

    public SetFieldException( String message, Throwable cause )
    {
        super( message, cause );
    }
    
    public SetFieldException( Throwable cause )
    {
        super( cause );
    }
}
