package com.googlecode.cchlib.sql.queries;

import com.googlecode.cchlib.sql.SimpleSQL;


/**
 * Exception wrapper for {@link QueryHandler}
 *
 * @see QueryHandler#handle(java.sql.ResultSet)
 * @see SimpleSQL#executeQuery(String, QueryHandler)
 *
 * @since 4.2
 */
public class QueryHandlerException extends Exception
{
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a QueryHandlerTargetException.
     */
    public QueryHandlerException()
    {
        // Empty
    }

    /**
     * Constructs a QueryHandlerTargetException with a target exception.
     *
     * @param cause
     *      the cause (which is saved for later retrieval by the getCause() method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public QueryHandlerException( final Throwable cause )
    {
        super( cause );
    }

    /**
     * Constructs a QueryHandlerTargetException with a target exception
     * and a detail message.
     *
     * @param message
     *      the detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause
     *      the cause (which is saved for later retrieval by the getCause() method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public QueryHandlerException( final String message, final Throwable cause )
    {
        super( message, cause );
    }

    /**
     * Constructs a QueryHandlerTargetException with a detail message.
     *
     * @param message
     *      the detail message (which is saved for later retrieval by the getMessage() method).
     */
    public QueryHandlerException( final String message )
    {
        super( message );
    }
}
