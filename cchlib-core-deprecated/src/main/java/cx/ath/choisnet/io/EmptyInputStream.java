package cx.ath.choisnet.io;

import java.io.InputStream;
import java.io.IOException;

/**
 * An always empty {@link InputStream}
 * @deprecated use {@link com.googlecode.cchlib.io.EmptyInputStream} instead
 */
@Deprecated
public class EmptyInputStream extends InputStream
{
    private boolean open;

    /**
     * Create an {@link EmptyInputStream}
     */
    public EmptyInputStream()
    {
        open = true;
    }

    @Override
    public int read() throws IOException
    {
        return -1;
    }

    @Override
    public void close() throws IOException
    {
        if(!open) {
            throw new IOException("aleary close");
            }
        else {
            open = false;
        }
    }
}
