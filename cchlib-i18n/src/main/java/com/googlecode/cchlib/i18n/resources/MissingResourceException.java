package com.googlecode.cchlib.i18n.resources;

import com.googlecode.cchlib.NeedDoc;

/**
 * TODOC
 */
@NeedDoc
public class MissingResourceException extends Exception 
{
    private static final long serialVersionUID = 1L;

    protected MissingResourceException( java.util.MissingResourceException cause )
    {
        super( cause );
    }
}
