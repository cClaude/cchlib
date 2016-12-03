package cx.ath.choisnet.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * NEEDDOC
 *
 */
public class ExternalAppException extends IOException
{
    private static final long serialVersionUID = 1L;

    public ExternalAppException( final String message )
    {
        super( message );
    }

    public ExternalAppException(
            final String    message,
            final Throwable cause
            )
    {
        super( message, cause );
    }

    public ExternalAppException( final Throwable cause )
    {
        super( cause );
    }

    public static String getStackTraceString( final Throwable e )
    {
        final StringWriter sw = new StringWriter();

        e.printStackTrace(new PrintWriter(sw));

        return sw.toString();
    }

    public String getStackTraceString()
    {
        return ExternalAppException.getStackTraceString( this );
    }
}
