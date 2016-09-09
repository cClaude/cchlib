package com.googlecode.cchlib.util.mappable;

import java.io.IOException;

public class StringBuilderIOException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public StringBuilderIOException( final IOException ioe )
    {
        super( ioe );
    }
}
