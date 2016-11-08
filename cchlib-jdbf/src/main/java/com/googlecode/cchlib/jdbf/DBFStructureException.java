package com.googlecode.cchlib.jdbf;

/**
 * {@link DBFException} for problems with data structure according to fields declaration.
 */
public class DBFStructureException extends DBFException
{
    private static final long serialVersionUID = 1L;

    public DBFStructureException()
    {
    }

    public DBFStructureException( String msg )
    {
        super( msg );
    }

}
