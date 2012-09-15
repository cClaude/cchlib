package com.googlecode.cchlib.sql;

/**
 * TODOC
 */
public class SimpleDataSourceException 
    extends Exception
{
    private static final long serialVersionUID = 1L;

    public SimpleDataSourceException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public SimpleDataSourceException(String message)
    {
        super(message);
    }
}
