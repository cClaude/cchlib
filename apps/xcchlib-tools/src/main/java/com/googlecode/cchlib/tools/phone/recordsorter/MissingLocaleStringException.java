package com.googlecode.cchlib.tools.phone.recordsorter;

public class MissingLocaleStringException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MissingLocaleStringException( String message, Throwable cause )
    {
        super( message, cause );
    }
}
