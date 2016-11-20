package com.googlecode.cchlib.sql;

/**
 * Allow to hide mostly IOException for SQL
 */
public class SQLCloseRuntimeException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * Create a SQLCloseRuntimeException
     *
     * @param cause Cause exception
     */
    public SQLCloseRuntimeException( final Exception cause )
    {
        super( cause );
    }
}
