package com.googlecode.cchlib.jdbf;

import java.io.IOException;

/**
 * Exception happen in the jdbf module
 */
public class DBFException extends IOException
{
    private static final long serialVersionUID = 1L;

    protected DBFException()
    {
    }

    public DBFException( final Throwable cause )
    {
        super( cause );
    }

    protected DBFException( final String msg )
    {
        super( msg );
    }

    public DBFException( final String msg, final Throwable cause )
    {
        super( msg, cause );
    }
}
