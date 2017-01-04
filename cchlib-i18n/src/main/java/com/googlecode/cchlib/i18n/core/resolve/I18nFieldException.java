package com.googlecode.cchlib.i18n.core.resolve;

public class I18nFieldException extends Exception
{
    private static final long serialVersionUID = 1L;

    I18nFieldException( final String message, final Throwable cause )
    {
        super( message, cause );
    }

    I18nFieldException( final Throwable cause )
    {
        super( cause );
    }
}
