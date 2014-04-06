package com.googlecode.cchlib.xutil.google.googlecontact;

import com.googlecode.cchlib.xutil.google.googlecontact.analyser.GoogleContacAnalyserException;

/**
 * Raise when {@link GoogleContactFactory} identify an error
 */
public class GoogleContactFactoryException extends GoogleContacAnalyserException {

    private static final long serialVersionUID = 1L;

    public GoogleContactFactoryException( final String message, final Throwable cause )
    {
        super( message, cause );
    }

    public GoogleContactFactoryException( final String message )
    {
        super( message );
    }
}
