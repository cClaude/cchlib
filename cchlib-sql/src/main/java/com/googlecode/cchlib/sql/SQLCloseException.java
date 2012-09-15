package com.googlecode.cchlib.sql;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Convert {@link SQLException} to {@link IOException} to
 * be able create method close()
 * matching with {@link java.io.Closeable#close()}
 */
public class SQLCloseException extends IOException
{
    private static final long serialVersionUID = 1L;

    protected SQLCloseException()
    {
    }

    public SQLCloseException( SQLException sqlException )
    {
        super( sqlException );
    }

    public SQLCloseException( String message, SQLException sqlException )
    {
        super( message, sqlException );
    }

    /**
     * Returns the cause of this SQLCloseException as a SQLException
     * @return original SQLException
     * @see #getCause()
     * @throws ClassCastException if the cause is not assignable 
     * to the type SQLException.
     * @since 4.1.7
     */
    public SQLException getSQLException()
    {
        return SQLException.class.cast( super.getCause() );
    }
}
