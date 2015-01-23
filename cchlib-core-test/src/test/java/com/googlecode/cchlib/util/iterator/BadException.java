package com.googlecode.cchlib.util.iterator;

final class BadException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BadException( final Exception cause )
    {
        super( cause );
    }
}