package cx.ath.choisnet.io;

import java.io.InputStream;
import cx.ath.choisnet.ToDo;

/**
 *
 * @author Claude CHOISNET
 *
 */
@ToDo(action=ToDo.Action.DOCUMENTATION)
public class EmptyInputStream extends InputStream
{
    public EmptyInputStream()
    {
    }

    public int read()
        throws java.io.IOException
    {
        return -1;
    }
}
