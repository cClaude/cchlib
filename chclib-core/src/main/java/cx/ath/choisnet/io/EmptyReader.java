package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.Reader;
import cx.ath.choisnet.ToDo;

/**
 *
 * @author Claude CHOISNET
 *
 */
@ToDo(action=ToDo.Action.DOCUMENTATION)
public class EmptyReader extends Reader
{
    private boolean open;

    public EmptyReader()
    {
        open = true;
    }

    public void close() throws IOException
    {
        if(!open) {
            throw new IOException("aleary close");
            }
        else {
            open = false;
        }
    }

    public int read(char[] cbuf, int off, int len)
        throws java.io.IOException
    {
        return -1;
    }
}
