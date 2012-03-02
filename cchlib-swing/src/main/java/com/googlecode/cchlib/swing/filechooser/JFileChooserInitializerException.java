/**
 *
 */
package com.googlecode.cchlib.swing.filechooser;

/**
 *
 *
 * @since 4.1.6
 */
public class JFileChooserInitializerException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public JFileChooserInitializerException()
    {
    }

    /**
     * @param message
     */
    public JFileChooserInitializerException( String message )
    {
        super( message );
    }

    /**
     * @param cause
     */
    public JFileChooserInitializerException( Throwable cause )
    {
        super( cause );
    }

    /**
     * @param message
     * @param cause
     */
    public JFileChooserInitializerException( String message, Throwable cause )
    {
        super( message, cause );
    }
}
