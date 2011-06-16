package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

/**
 * {@link OutputStream} able to write data into one or more stream ate once.
 */
public final class ParallelOutputStream extends OutputStream
{
    private OutputStream[] outputStreams;

    /**
     * Create a ParallelOutputStream
     *
     * @param streams array of {@link OutputStream}
     */
    public ParallelOutputStream( final OutputStream...streams )
    {
        int i               = 0;
        this.outputStreams  = new OutputStream[streams.length];

        for( OutputStream os: streams ) {
            this.outputStreams[i++] = os;
            }
    }

    /**
     * Create a ParallelOutputStream
     *
     * @param streams {@link Collection} of {@link OutputStream}
     */
    public ParallelOutputStream( final Collection<OutputStream> streams )
    {
        int i               = 0;
        this.outputStreams  = new OutputStream[streams.size()];

        for( OutputStream os: streams ) {
            this.outputStreams[i++] = os;
            }
    }

    @Override
    public void close() throws IOException
    {
        IOException lastException = null;

        for( int i = 0; i < outputStreams.length; i++ ) {
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
        IOException lastException = null;

        for( int i = 0; i < outputStreams.length; i++ ) {
            try {
                outputStreams[i].flush();
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
    public void write( int b ) throws IOException
    {
        for(int i = 0; i < outputStreams.length; i++) {
            outputStreams[i].write(b);
            }
    }

    @Override
    public void write( byte[] b, int offset, int length ) throws IOException
    {
        for(int i = 0; i < outputStreams.length; i++) {
            outputStreams[i].write( b, offset, length );
            }
    }

    @Override
    public void write( byte[] b ) throws IOException
    {
        for(int i = 0; i < outputStreams.length; i++ ) {
            outputStreams[i].write( b );
            }
    }
}
