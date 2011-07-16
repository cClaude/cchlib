package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.Reader;

/**
 * ConcateReader is {@link Reader} based on content of one or
 * more {@link Reader} objects. Object are consume in sequence.
 *
 * @since 4.1.5
 */
public class ConcateReader extends Reader
{
    private Reader[] readers;
    private int index;

    /**
     * Create a ConcateReader
     * @param readers {@link Reader} list to be use in giving order.
     */
    public ConcateReader( final Reader[] readers )
    {
        this.readers = readers;
    }

    @Override
    public void close() throws IOException
    {
        IOException anIOE = null;

        for( int i = 0; i < readers.length; i++ ) {
            try {
                readers[i].close();
                }
            catch(IOException e) {
                anIOE = e;
                }
            }

        if( anIOE != null ) {
            throw anIOE;
            }
        else {
            return;
            }
    }

    @Override
    public boolean markSupported()
    {
        return false;
    }

    @Override
    public int read() throws IOException
    {
        for(; index < readers.length; index++) {
            int r = readers[index].read();

            if( r != -1 ) {
                return r;
                }
            }

        return -1;
    }

    @Override
    public int read(char[] cbuf, int off, int len)
        throws IOException
    {
        for(; index < readers.length; index++) {
            int rlen = readers[index].read(cbuf, off, len);

            if( rlen != -1 ) {
                return rlen;
                }
            }

        return -1;
    }
}
