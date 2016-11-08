package com.googlecode.cchlib.jdbf;

/**
 * {@link DBFEntryException} create when trying to resolve an unknown field name
 */
public class DBFUnknownFieldNameException extends DBFEntryException
{
    private static final long serialVersionUID = 1L;

    public DBFUnknownFieldNameException( final String columnName )
    {
        super( columnName );
    }
}
