package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author Claude CHOISNET
 *
 */
public final class ParallelOutputStream extends OutputStream
{
    private OutputStream[] outputStreams;

    public ParallelOutputStream(OutputStream stream1, OutputStream stream2)
    {
        outputStreams = new OutputStream[2];
        outputStreams[0] = stream1;

        outputStreams[1] = stream2;
    }

    public ParallelOutputStream(OutputStream...streams)
    {
        outputStreams = new OutputStream[streams.length];

        int i = 0;
        java.io.OutputStream arr$[] = streams;
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; i$++)

        {
            OutputStream os = arr$[i$];
            outputStreams[i++] = os;
        }
    }

    public void write(int b)
        throws java.io.IOException
    {
        for(int i = 0; i < outputStreams.length; i++)

        {
            outputStreams[i].write(b);
        }
    }

    public void close()
        throws java.io.IOException
    {
        IOException lastException = null;
        for(int i = 0; i < outputStreams.length; i++)

        {
            try
            {
                outputStreams[i].close();

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
        for(int i = 0; i < outputStreams.length; i++)

        {
            outputStreams[i].flush();

        }
    }
}
