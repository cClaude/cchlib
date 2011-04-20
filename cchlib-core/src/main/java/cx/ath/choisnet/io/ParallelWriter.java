package cx.ath.choisnet.io;

import java.io.Writer;

/**
 *
 * @author Claude CHOISNET
 *
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
        writers = new Writer[writerList.length];

        int i = 0;
        java.io.Writer arr$[] = writerList;
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; i$++)

        {
            java.io.Writer w = arr$[i$];
            writers[i++] = w;
        }
    }

    public void close()
        throws java.io.IOException
    {
        java.io.IOException lastException = null;
        for(int i = 0; i < writers.length; i++)

        {
            try
            {
                writers[i].close();

            }

            catch(java.io.IOException e)

            {
                lastException = e;
            }
        }

        if(lastException != null)
        {
            throw lastException;
        } else
        {
            return;

        }
    }

    public void flush()
        throws java.io.IOException
    {
        for(int i = 0; i < writers.length; i++)

        {
            writers[i].flush();

        }
    }

    public void write(char cbuf[], int off, int len)
        throws java.io.IOException
    {
        for(int i = 0; i < writers.length; i++)

        {
            writers[i].write(cbuf, off, len);
        }
    }
}
