package com.googlecode.cchlib.xutil.google.googlecontact.analyser;

public class GoogleContactCSVException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public GoogleContactCSVException( final String message, final Throwable cause )
    {
        super( message, cause );
    }

    public GoogleContactCSVException( final String message )
    {
        super( message );
    }
}
