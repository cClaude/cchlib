package com.googlecode.cchlib.util.base64;

import java.io.IOException;

/**
 * Identify a Base64 encoding/decoding error
 */
public class Base64FormatException extends IOException
{
    private static final long serialVersionUID = 1L;

    /**
     * Create a Base64FormatException
     * 
     * @param msg Message for this exception
     */
    public Base64FormatException( final String msg )
    {
        super( msg );
    }

    /**
     * Create a Base64FormatException
     * 
     * @param msg   Message for this exception
     * @param cause Cause for this exception
     */
    public Base64FormatException(
        final String msg, 
        final Throwable cause 
        )
    {
        super( msg );
        
        initCause( cause );
    }
}
