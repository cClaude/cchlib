package com.googlecode.cchlib.sql;

import java.io.IOException;

/**
 * Allow to hide IOException for SQL
 */
public class SQLCloseRuntimeException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * Create a SQLCloseIOException from {@link IOException}
     *
     * @param cause Cause exception
     */
    public SQLCloseRuntimeException( final IOException cause )
    {
        super( cause );
    }
}
