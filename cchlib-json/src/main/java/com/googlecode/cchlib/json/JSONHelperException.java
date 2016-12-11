package com.googlecode.cchlib.json;

import java.io.IOException;

/**
 * Exception related to {@link JSONHelper}
 */
public class JSONHelperException extends Exception
{
    private static final long serialVersionUID = 2L;

    /**
     * Create a JSONHelperException
     *
     * @param cause
     *            Cause exception.
     */
    public JSONHelperException( final IOException cause )
    {
        super( cause );
    }

    /**
     * Create a JSONHelperException
     *
     * @param message
     *            the detail message
     * @param cause
     *            Cause exception.
     */
    public JSONHelperException( final String message, final IOException cause )
    {
        super( message, cause );
    }
}
