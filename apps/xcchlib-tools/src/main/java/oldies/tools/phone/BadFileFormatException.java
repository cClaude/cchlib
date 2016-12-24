package oldies.tools.phone;

import java.io.File;

public class BadFileFormatException extends Exception
{
    private static final long serialVersionUID = 1L;

    public BadFileFormatException( final String message )
    {
        super( message );
    }

    public BadFileFormatException( final Throwable cause )
    {
        super( cause );
    }

    public BadFileFormatException(
        final String    message,
        final Throwable cause
        )
    {
        super( message, cause );
    }

    /**
     * Create a BadFileFormatException for a specified {@code file}
     *
     * @param file
     *            File that cause this exception
     */
    public BadFileFormatException( final File file )
    {
        super( file.getPath() );
    }

    /**
     * Create a BadFileFormatException for a specified {@code file}
     * and {@code lineNumber}
     *
     * @param file
     *            File that cause this exception
     * @param lineNumber
     *            Related line
     */
    public BadFileFormatException( final File file, final int lineNumber )
    {
        super( file.getPath() + " at line: " + lineNumber );
    }
}
