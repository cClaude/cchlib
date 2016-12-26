package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.Reader;
import com.googlecode.cchlib.io.IOHelper;

/**
 *
 * @since 2.01
 *
 * @see InputStreamHelper
 * @see IOHelper
 */
public class ReaderHelper
{
    private static final class ConcatReader extends Reader
    {
        private final Reader[] readers;

        private int index = 0;

        private ConcatReader( final Reader[] readers )
        {
            this.readers = readers;
        }

        @Override
        public void close() throws IOException
        {
            IOException anIOE = null;

            for( int i = 0; i < this.readers.length; i++ ) {
                try {
                    this.readers[ i ].close();
                }
                catch( final IOException e ) {
                    anIOE = e;
                }
            }

            if( anIOE != null ) {
                throw anIOE;
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
            for( ; this.index < this.readers.length; this.index++ ) {
                final int r = this.readers[ this.index ].read();

                if( r != -1 ) {
                    return r;
                }
            }

            return -1;
        }

        @Override
        public int read( final char[] cbuf, final int off, final int len )
            throws IOException
        {
            for( ; this.index < this.readers.length; this.index++ ) {
                final int rlen = this.readers[ this.index ].read( cbuf, off, len );

                if( rlen != -1 ) {
                    return rlen;
                }
            }

            return -1;
        }
    }

    private ReaderHelper()
    {
        // All static
    }

    public static Reader concat( final Reader reader1, final Reader reader2 )
    {
        return new ConcatReader( new Reader[] { reader1, reader2 } );
    }

    public static Reader concat( final Reader[] readers )
    {
        return new ConcatReader( readers );
    }
}
