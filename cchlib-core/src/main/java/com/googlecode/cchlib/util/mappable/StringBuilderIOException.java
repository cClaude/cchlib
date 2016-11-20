package com.googlecode.cchlib.util.mappable;

import java.io.IOException;

/**
 * Hide {@link IOException}
 */
public class StringBuilderIOException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new StringBuilderIOException
     * @param ioe the cause
     */
    public StringBuilderIOException( final IOException ioe )
    {
        super( ioe );
    }
}
