package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Nonnull;
import com.googlecode.cchlib.io.ConcateInputStream;
import com.googlecode.cchlib.io.IOHelper;

/**
 * Classe offrant des methodes autour de {@link InputStream}, en particulier la
 * copie de flux (ou de fichier), ainsi que la concatenation de flux.
 *
 * @since 2.01
 *
 * @see ConcateInputStream
 * @see InputStream
 * @see ReaderHelper
 * @see IOHelper
 */
public class InputStreamHelper
{
    private static final class ConcatInputStream extends InputStream
    {
        private final InputStream[] is;

        private int index = 0;

        private ConcatInputStream( @Nonnull final InputStream[] is )
        {
            this.is = is;
        }

        @Override
        public int available() throws IOException
        {
            return this.is[ this.index ].available();
        }

        @Override
        public void close() throws IOException
        {
            IOException anIOE = null;

            for( int i = 0; i < this.is.length; i++ ) {
                try {
                    this.is[ i ].close();
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
            for( ; this.index < this.is.length; this.index++ ) {
                final int r = this.is[ this.index ].read();

                if( r != -1 ) {
                    return r;
                }
            }

            return -1;
        }
    }

    private InputStreamHelper()
    {
        // All static
    }

    public static InputStream concat(
        @Nonnull final InputStream is1,
        @Nonnull final InputStream is2
        )
    {
        return new ConcatInputStream( new InputStream[] { is1, is2 } );
    }

    public static InputStream concat( @Nonnull final InputStream[] is )
    {
        return new ConcatInputStream( is );
    }
}
