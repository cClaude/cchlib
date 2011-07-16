package cx.ath.choisnet.io;

import java.io.File;

/**
 * @deprecated use {@link com.googlecode.cchlib.io.FileDeleteException} instead
 */
public class FileDeleteException extends com.googlecode.cchlib.io.FileDeleteException//extends IOException
{
    private static final long serialVersionUID = 2L;

    public FileDeleteException(File file)
    {
        super(file);
    }

    public FileDeleteException(String message)
    {
        super(null,message,null);
    }

    public FileDeleteException(String message, Throwable cause)
    {
        super(null,message,cause);
    }
}
