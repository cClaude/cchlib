package com.googlecode.cchlib.swing.filechooser;

/**
 * RuntimeException generate by {@link JFileChooserInitializer}
 *
 * @since 4.1.6
 */
public class JFileChooserInitializerException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new exception
     */
    public JFileChooserInitializerException()
    {
        // Empty
    }

    /**
     * Constructs a new exception with the specified detail message
     *
     * @param message the specified detail message
     */
    public JFileChooserInitializerException( final String message )
    {
        super( message );
    }

    /**
     * Constructs a new exception with the specified cause
     *
     * @param cause the cause
     */
    public JFileChooserInitializerException( final Throwable cause )
    {
        super( cause );
    }

    /**
     * Constructs a new exception
     *
     * @param message the specified detail message
     * @param cause the cause
     */
    public JFileChooserInitializerException( final String message, final Throwable cause )
    {
        super( message, cause );
    }
}
