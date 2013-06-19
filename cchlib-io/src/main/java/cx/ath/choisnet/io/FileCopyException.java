package cx.ath.choisnet.io;

import java.io.IOException;

/**
 * Exception invoke when an I/O error occur during copy
 */
public class FileCopyException extends IOException
{
    private static final long serialVersionUID = 1L;

    public FileCopyException(String message)
    {
        super(message);
    }

    public FileCopyException(String message, Throwable cause)
    {
        super(message);
        super.initCause(cause);
    }
}
