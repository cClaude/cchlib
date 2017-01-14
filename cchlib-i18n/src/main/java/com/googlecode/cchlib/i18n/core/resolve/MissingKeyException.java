package com.googlecode.cchlib.i18n.core.resolve;

public class MissingKeyException extends I18nFieldException
{
    private static final long serialVersionUID = 1L;

    public MissingKeyException( final String message, final Throwable cause )
    {
        super( message, cause );
    }

    public MissingKeyException( final Throwable cause )
    {
        super( cause );
    }
}
