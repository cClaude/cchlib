package com.googlecode.cchlib.sql;

/**
 * Error while creating a {@link SimpleDataSource}
 */
public class SimpleDataSourceException  extends Exception
{
    private static final long serialVersionUID = 1L;

    public SimpleDataSourceException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    public SimpleDataSourceException(final String message)
    {
        super(message);
    }
}
