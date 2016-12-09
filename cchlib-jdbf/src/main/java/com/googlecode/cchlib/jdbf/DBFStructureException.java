package com.googlecode.cchlib.jdbf;

/**
 * {@link DBFException} for problems with data structure according to fields declaration.
 */
public class DBFStructureException extends DBFException
{
    private static final long serialVersionUID = 1L;

    public DBFStructureException( final String message )
    {
        super( message );
    }

    public DBFStructureException( final String message, final Exception cause )
    {
        super( message, cause );
    }

}
