package cx.ath.choisnet.tools.analysis;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 *
 */
public class DefaultDirectoryFilterTest
{
    private static final Logger LOGGER = Logger.getLogger( DefaultDirectoryFilterTest.class );
    private static final String[] FULL_PATH_TO_TEST = {
        "C:\\System Volume Information",
        "C:\\Recycled",
        "C:\\Program Files",
        "C:\\WINDOWS",
        };
    private final List<File> fullPathToTestList = new ArrayList<File>();

    private DefaultDirectoryFilter defaultDirectoryFilter;
    private Writer outputWriter;

    @Before
    public void setUp()
    {
        this.outputWriter = new WriterWrapper( System.out );
        this.defaultDirectoryFilter = new DefaultDirectoryFilter( this.outputWriter );

        this.fullPathToTestList.clear();

        for( final String fp : FULL_PATH_TO_TEST ) {
            final File f = new File( fp );

            if( f.isDirectory() ) {
                this.fullPathToTestList.add( new File( fp ) );
                }
            else {
                LOGGER.warn( "Directory File not found: " + f );
                }
            }
    }

    @After
    public void tearDown()
    {
        this.outputWriter = null;
        this.defaultDirectoryFilter = null;
    }

    @Test
    public void testIgnore()
    {
        for( final File dirFile : this.fullPathToTestList ) {
            final boolean accept = this.defaultDirectoryFilter.accept( dirFile );

            LOGGER.info( "Res:" + accept + " File: " + dirFile );

            Assert.assertFalse(
                    "File not matching (should be ignored): " + dirFile,
                    accept
                    );
            }

        LOGGER.info( "####" );
    }


    class WriterWrapper extends Writer
    {
        private final PrintStream stream;
        private final Charset charset;

        public WriterWrapper( final PrintStream ps )
        {
            this( ps, Charset.defaultCharset() );
        }

        public WriterWrapper( final PrintStream ps, final Charset charset )
        {
            this.stream  = ps;
            this.charset = charset;
        }

        @Override
        public void close() throws IOException
        {
            this.stream.close();
        }

        @Override
        public void flush() throws IOException
        {
            this.stream.flush();
        }

        @Override
        public void write(final char[] charbuf, final int off, final int len) throws IOException
        {
            final ByteBuffer bb  = this.charset.encode( new String( charbuf ) );
            final byte[]     buf = bb.array();

            this.stream.write(buf, off, len);
        }

    }
}
