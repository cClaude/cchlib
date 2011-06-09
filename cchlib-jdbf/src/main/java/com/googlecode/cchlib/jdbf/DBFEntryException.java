package com.googlecode.cchlib.jdbf;

/**
 *
 *
 */
public class DBFEntryException extends DBFException
{
    private static final long serialVersionUID = 1L;

    public DBFEntryException( String msg, Throwable cause )
    {
        super( msg, cause );
    }

    public DBFEntryException( String msg)
    {
        super( msg );
    }

}
