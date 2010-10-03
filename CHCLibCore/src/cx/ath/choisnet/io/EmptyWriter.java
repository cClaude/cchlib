package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.Writer;
import cx.ath.choisnet.ToDo;

/**
 *
 * @author Claude CHOISNET
 *
 */
@ToDo(action=ToDo.Action.DOCUMENTATION)
public class EmptyWriter extends Writer
{

    private boolean open;

    public EmptyWriter()
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

    public void flush()
        throws java.io.IOException
    {
        if(!open) {
            throw new IOException("is close");
            }
    }

    public void write(char[] cbuf, int off, int len)
        throws java.io.IOException
    {
        if(!open) {
            throw new IOException("is close");
            }
    }
}
