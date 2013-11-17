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
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 *
 */
public class DefaultDirectoryFilterTest
{
    private final static Logger LOGGER = Logger.getLogger( DefaultDirectoryFilterTest.class );
    private final static String[] FULL_PATH_TO_TEST = {
        "C:\\System Volume Information",
        "C:\\Recycled",
        "C:\\Program Files",
        "C:\\WINDOWS",
        };
    private final List<File> fullPathToTestList = new ArrayList<File>();

    private DefaultDirectoryFilter defaultDirectoryFilter;
    private Writer outputWriter;


    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp()
    {
        this.outputWriter = new WriterWrapper( System.out );
        this.defaultDirectoryFilter = new DefaultDirectoryFilter( outputWriter );

        this.fullPathToTestList.clear();

        for( String fp : FULL_PATH_TO_TEST ) {
            File f = new File( fp );

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
        for( File dirFile : this.fullPathToTestList ) {
            boolean accept = defaultDirectoryFilter.accept( dirFile );

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
        private PrintStream stream;
        private Charset charset;

        public WriterWrapper( PrintStream ps )
        {
            this( ps, Charset.defaultCharset() );
        }

        public WriterWrapper( PrintStream ps, Charset charset )
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
        public void write(char[] charbuf, int off, int len) throws IOException
        {
            ByteBuffer bb  = charset.encode( new String( charbuf ) );
            byte[]     buf = bb.array();

            this.stream.write(buf, off, len);
        }

    }
}
