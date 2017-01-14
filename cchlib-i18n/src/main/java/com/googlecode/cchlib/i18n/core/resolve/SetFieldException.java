package com.googlecode.cchlib.i18n.core.resolve;

public class SetFieldException extends I18nFieldException
{
    private static final long serialVersionUID = 1L;

    public SetFieldException( final String message, final Throwable cause )
    {
        super( message, cause );
    }

    public SetFieldException( final Throwable cause )
    {
        super( cause );
    }
}
