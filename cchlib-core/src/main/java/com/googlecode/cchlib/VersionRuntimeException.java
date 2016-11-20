package com.googlecode.cchlib;

//not public
class VersionRuntimeException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public VersionRuntimeException( final String message, final Exception cause )
    {
        super( message, cause );
    }
}
