/**
 *
 */
package cx.ath.choisnet.tools.phone;

import java.io.File;

/**
 *
 *
 */
public class BadFileFormatException extends Exception
{
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public BadFileFormatException() {}

    /**
     * @param message
     */
    public BadFileFormatException(String message)
    {
        super(message);
    }

    /**
     * @param cause
     */
    public BadFileFormatException(Throwable cause)
    {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public BadFileFormatException(
        final String message,
        final Throwable cause
        )
    {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public BadFileFormatException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace
            )
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Create a BadFileFormatException for a specified file
     * @param file File that cause this exception
     */
    public BadFileFormatException( final File file )
    {
        super( file.getPath() );
    }

    /**
     *
     * @param file
     * @param lineNumber
     */
    public BadFileFormatException( final File file, final int lineNumber )
    {
        super( file.getPath() + " at line: " + lineNumber );
    }

}
