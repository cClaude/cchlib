package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * TODO: Doc!
 *
 * @author Claude CHOISNET
 */
public final class ParallelOutputStream extends OutputStream
{
    private OutputStream[] outputStreams;

    /**
     * TODO: Doc!
     *
     * @param stream1
     * @param stream2
     */
    public ParallelOutputStream(OutputStream stream1, OutputStream stream2)
    {
        outputStreams = new OutputStream[2];
        outputStreams[0] = stream1;

        outputStreams[1] = stream2;
    }

    /**
     * TODO: Doc!
     *
     * @param streams
     */
    public ParallelOutputStream(OutputStream...streams)
    {
        int i               = 0;
        this.outputStreams  = new OutputStream[streams.length];

        for( OutputStream os: streams) {
            outputStreams[i++] = os;
        }
    }

    @Override
    public void write(int b)
        throws java.io.IOException
    {
        for(int i = 0; i < outputStreams.length; i++)

        {
            outputStreams[i].write(b);
        }
    }

    @Override
    public void close() throws IOException
    {
        IOException lastException = null;

        for(int i = 0; i < outputStreams.length; i++) {
            try {
                outputStreams[i].close();
                }
            catch( IOException e) {
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
        for(int i = 0; i < outputStreams.length; i++) {
            outputStreams[i].flush();
            }
    }
}
