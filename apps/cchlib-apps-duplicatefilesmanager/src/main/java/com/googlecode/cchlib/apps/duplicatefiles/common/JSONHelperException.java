package com.googlecode.cchlib.apps.duplicatefiles.common;

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
     * @param cause Cause exception.
     */
    public JSONHelperException( final IOException cause )
    {
        super( cause );
    }
}
