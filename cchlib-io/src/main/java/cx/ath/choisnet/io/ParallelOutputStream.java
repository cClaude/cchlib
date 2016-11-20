package cx.ath.choisnet.io;

import com.googlecode.cchlib.io.exceptions.MultiIOException;
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
    public void close() throws MultiIOException
    {
        MultiIOException exceptions = new MultiIOException();

        for (OutputStream outputStream : outputStreams) {
            try {
                outputStream.close();
            }catch( IOException ioe ) {
                exceptions.addIOException( ioe );
            }
        }

        if( ! exceptions.isEmpty() ) {
            throw exceptions;
            }
    }

    @Override
    public void flush() throws IOException
    {
        MultiIOException exceptions = new MultiIOException();

        for (OutputStream outputStream : outputStreams) {
            try {
                outputStream.flush();
            }catch( IOException ioe ) {
                exceptions.addIOException( ioe );
            }
        }

        if( ! exceptions.isEmpty() ) {
            throw exceptions;
            }
    }

    @Override
    public void write( int b ) throws IOException
    {
        for (OutputStream outputStream : outputStreams) {
            outputStream.write(b);
        }
    }

    @Override
    public void write( byte[] b, int offset, int length ) throws IOException
    {
        for (OutputStream outputStream : outputStreams) {
            outputStream.write(b, offset, length);
        }
    }

    @Override
    public void write( byte[] b ) throws IOException
    {
        for (OutputStream outputStream : outputStreams) {
            outputStream.write(b);
        }
    }
}
