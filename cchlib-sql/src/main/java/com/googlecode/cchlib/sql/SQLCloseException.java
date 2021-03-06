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

    /**
     * Constructs an IOException with the specified cause.
     *
     * @param cause The cause (which is saved for later retrieval by the getCause() method)
     */
    public SQLCloseException( final SQLException cause )
    {
        super( cause );
    }

    /**
     * Constructs an IOException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method)
     * @param cause   The cause (which is saved for later retrieval by the getCause() method)
     */
    public SQLCloseException( final String message, final SQLException cause )
    {
        super( message, cause );
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
