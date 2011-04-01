package cx.ath.choisnet.io;

import java.io.File;
import java.io.IOException;
import cx.ath.choisnet.ToDo;

/**
 *
 * @author Claude CHOISNET
 *
 */
@ToDo(action=ToDo.Action.DOCUMENTATION)
public class FileDeleteException extends IOException
{
    private static final long serialVersionUID = 2L;

    public FileDeleteException(File file)
    {
        super(file.getPath());
    }

    public FileDeleteException(String message)
    {
        super(message);
    }

    public FileDeleteException(String message, Throwable cause)
    {
        super(message);
        initCause(cause); // old JDK
    }
}
