package com.googlecode.cchlib.resources;

/**
 * Exceptions while error occur using {@link ResourcesLoader}
 * @since 4.1.6
 */
public class ResourcesLoaderException extends Exception
{
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new exception with the specified detail
     * message. The cause is not initialized, and may
     * subsequently be initialized by a call to initCause.
     *
     * @param message the detail message. The detail message
     *                is saved for later retrieval by the
     *                etMessage() method.
     */
    public ResourcesLoaderException( String message )
    {
        super( message );
    }

    /**
     * Constructs a new exception with the specified cause
     * and a detail message of (cause==null ? null : cause.toString())
     * (which typically contains the class and detail message of cause).
     * This constructor is useful for exceptions that are little
     * more than wrappers for other throwables (for example,
     * java.security.PrivilegedActionException).
     *
     * @param cause the cause (which is saved for later retrieval by
     *              the getCause() method). (A null value is permitted,
     *              and indicates that the cause is nonexistent or unknown.)
     */
    public ResourcesLoaderException( Throwable cause )
    {
        super( cause );
    }

    /**
     * Constructs a new exception with the specified detail
     * message and cause.
     * <br/>
     * Note that the detail message associated with cause is
     * not automatically incorporated in this exception's detail
     * message.
     *
     * @param message the detail message.
     * @param cause the cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public ResourcesLoaderException( String message, Throwable cause )
    {
        super( message, cause );
    }
}
