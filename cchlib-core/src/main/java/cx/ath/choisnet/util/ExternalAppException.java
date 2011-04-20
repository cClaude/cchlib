package cx.ath.choisnet.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class ExternalAppException extends IOException
{
    private static final long serialVersionUID = 1L;

    public ExternalAppException(String msg)
    {
        super(msg);
    }

    public ExternalAppException(String msg, Throwable cause)
    {
        super(msg);
        initCause(cause);
    }

    public static String getStackTraceString(Throwable e)
    {
        StringWriter sw = new StringWriter();

        e.printStackTrace(new PrintWriter(sw));

        return sw.toString();
    }

    public String getStackTraceString()
    {
        return ExternalAppException.getStackTraceString( this );
    }
}
