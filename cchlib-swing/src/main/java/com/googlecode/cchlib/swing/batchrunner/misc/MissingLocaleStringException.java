package com.googlecode.cchlib.swing.batchrunner.misc;

public class MissingLocaleStringException extends MissingResourceValueException
{
    private static final long serialVersionUID = 1L;

    public MissingLocaleStringException( final String message, final Throwable cause )
    {
        super( message, cause );
    }
}
