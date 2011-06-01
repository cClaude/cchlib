package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.Writer;

/**
 * TODO: Doc!
 *
 * @author Claude CHOISNET
 */
public final class ParallelWriter extends Writer
{
    private Writer[] writers;

    public ParallelWriter(Writer writer1, Writer writer2)
    {
        writers = new Writer[2];
        writers[0] = writer1;
        writers[1] = writer2;
    }

    public ParallelWriter(Writer...writerList)
    {
        int i        = 0;
        this.writers = new Writer[writerList.length];

        for( Writer w : writerList ) {
            writers[i++] = w;
        }
    }

    @Override
    public void close() throws IOException
    {
        IOException lastException = null;

        for(int i = 0; i < writers.length; i++) {
            try {
                writers[i].close();
                }
            catch( IOException e ) {
                lastException = e;
                }
            }

        if(lastException != null) {
            throw lastException;
            }
        else {
            return;
            }
    }

    @Override
    public void flush() throws IOException
    {
        for(int i = 0; i < writers.length; i++) {
            writers[i].flush();
            }
    }

    @Override
    public void write(char cbuf[], int off, int len)
        throws IOException
    {
        for(int i = 0; i < writers.length; i++) {
            writers[i].write(cbuf, off, len);
            }
    }
}
