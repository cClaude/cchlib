package cx.ath.choisnet.io;

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
        IOException lastException = null;

        for(int i = 0; i < writers.length; i++) {
            try {
                writers[i].flush();
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
    public void write( char[] cbuf, int off, int len ) throws IOException
    {
        for(int i = 0; i < writers.length; i++) {
            writers[i].write( cbuf, off, len );
            }
    }

    @Override
    public void write( char[] cbuf ) throws IOException
    {
        for(int i = 0; i < writers.length; i++) {
            writers[i].write( cbuf );
            }
    }

    @Override
    public void write( String str ) throws IOException
    {
        for(int i = 0; i < writers.length; i++) {
            writers[i].write( str );
            }
    }
    
    @Override
    public void write( String str, int off, int len ) throws IOException
    {
        for(int i = 0; i < writers.length; i++) {
            writers[i].write( str, off, len );
            }
    }
    
    @Override
    public void write( int c ) throws IOException
    {
        for(int i = 0; i < writers.length; i++) {
            writers[i].write( c );
            }
    }
}
