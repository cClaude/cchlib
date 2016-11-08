package cx.ath.choisnet.io;

import com.googlecode.cchlib.io.exceptions.MultiIOException;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

/**
 * {@link Writer} able to write data into one or more stream writer at once.
 */
public final class ParallelWriter extends Writer
{
    private Writer[] writers;

    /**
     * Create a ParallelWriter
     *
     * @param writers array of {@link Writer}
     */
    public ParallelWriter( final Writer...writers )
    {
        int i        = 0;
        this.writers = new Writer[writers.length];

        for( Writer w : writers ) {
            this.writers[i++] = w;
            }
    }

    /**
     * Create a ParallelWriter
     *
     * @param writers {@link Collection} of {@link Writer}
     */
    public ParallelWriter( final Collection<Writer> writers )
    {
        int i        = 0;
        this.writers = new Writer[writers.size()];

        for( Writer w : writers ) {
            this.writers[i++] = w;
        }
    }

    @Override
    public void close() throws MultiIOException
    {
        MultiIOException exceptions = new MultiIOException();

        for (Writer writer : writers) {
            try {
                writer.close();
            }catch( IOException ioe ) {
                exceptions.addIOException( ioe );
            }
        }

        if( ! exceptions.isEmpty() ) {
            throw exceptions;
            }
    }

    @Override
    public void flush() throws MultiIOException
    {
        MultiIOException exceptions = new MultiIOException();

        for (Writer writer : writers) {
            try {
                writer.flush();
            }catch( IOException ioe ) {
                exceptions.addIOException( ioe );
            }
        }

        if( ! exceptions.isEmpty() ) {
            throw exceptions;
            }
    }

    @Override
    public void write( char[] cbuf, int off, int len ) throws IOException
    {
        for (Writer writer : writers) {
            writer.write(cbuf, off, len);
        }
    }

    @Override
    public void write( char[] cbuf ) throws IOException
    {
        for (Writer writer : writers) {
            writer.write(cbuf);
        }
    }

    @Override
    public void write( String str ) throws IOException
    {
        for (Writer writer : writers) {
            writer.write(str);
        }
    }
    
    @Override
    public void write( String str, int off, int len ) throws IOException
    {
        for (Writer writer : writers) {
            writer.write(str, off, len);
        }
    }
    
    @Override
    public void write( int c ) throws IOException
    {
        for (Writer writer : writers) {
            writer.write(c);
        }
    }
}
