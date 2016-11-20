package com.googlecode.cchlib.util.mappable;

class MappableRuntimeException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    MappableRuntimeException( final String message, final ClassCastException cause )
    {
        super( message, cause );
    }
}
