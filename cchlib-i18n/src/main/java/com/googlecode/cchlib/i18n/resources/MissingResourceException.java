package com.googlecode.cchlib.i18n.resources;

/**
 * Exception throws when a translation is not available for an item
 */
public class MissingResourceException extends Exception
{
    private static final long serialVersionUID = 1L;

    protected MissingResourceException( final java.util.MissingResourceException cause )
    {
        super( cause );
    }

    protected MissingResourceException(
        final String                             message,
        final java.util.MissingResourceException cause
        )
    {
        super( message, cause );
    }
}
