package com.googlecode.cchlib.jdbf;

/**
 * {@link DBFException} create when an error occur while access to a {@link DBFEntry}
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
