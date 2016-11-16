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

    public ExternalAppException(final String msg)
    {
        super(msg);
    }

    public ExternalAppException(final String msg, final Throwable cause)
    {
        super(msg);
        initCause(cause);
    }

    public static String getStackTraceString(final Throwable e)
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
