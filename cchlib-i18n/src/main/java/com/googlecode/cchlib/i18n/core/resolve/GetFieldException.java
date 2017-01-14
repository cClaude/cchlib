package com.googlecode.cchlib.i18n.core.resolve;

public class GetFieldException extends I18nFieldException
{
    private static final long serialVersionUID = 1L;

    public GetFieldException( final String message, final Throwable cause )
    {
        super( message, cause );
    }

    public GetFieldException( final Throwable cause )
    {
        super( cause );
    }
}
