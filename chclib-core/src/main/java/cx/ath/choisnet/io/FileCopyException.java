package cx.ath.choisnet.io;

import java.io.IOException;
import cx.ath.choisnet.ToDo;

/**
 *
 * @author Claude CHOISNET
 *
 */
@ToDo(action=ToDo.Action.DOCUMENTATION)
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
