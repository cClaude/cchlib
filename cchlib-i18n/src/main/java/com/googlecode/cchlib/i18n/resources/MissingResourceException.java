package com.googlecode.cchlib.i18n.resources;

import com.googlecode.cchlib.NeedDoc;

/**
 * Exception throws when a translation is not available for an item
 */
@NeedDoc
public class MissingResourceException extends Exception
{
    private static final long serialVersionUID = 1L;

    protected MissingResourceException( final java.util.MissingResourceException cause )
    {
        super( cause );
    }
}
