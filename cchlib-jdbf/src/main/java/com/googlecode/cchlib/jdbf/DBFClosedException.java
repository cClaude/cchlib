package com.googlecode.cchlib.jdbf;

/**
 * {@link DBFException} created when trying to use a closed {@link DBFReader}
 */
public class DBFClosedException extends DBFException
{
    private static final long serialVersionUID = 1L;

    public DBFClosedException( String msg )
    {
        super( msg );
    }

}
