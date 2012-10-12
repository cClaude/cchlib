package cx.ath.choisnet.net;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import com.googlecode.cchlib.io.IOHelper;

/**
 *
 */
public class URLHelperTest
{
    private final static Logger logger = Logger.getLogger( URLHelperTest.class );
    private URL testURL;

    @Before
    public void setUp() throws Exception
    {
        testURL = new URL( "http://www.google.com/" );
    }

    @After
    public void tearDown() throws Exception
    {}

    /**
     * Checking Internet connection using : testURL
     * @return Is Internet access allowed ?
     */
    private boolean isInternetAccessAllowed()
    {
        logger.warn( "Checking Internet connection using: " + testURL );

        try {
            InputStream is = testURL.openStream();

            is.close();
            return true;
            }
        catch( IOException e ) {
            logger.warn( "NO INTERNET: " + e.getMessage() );
            return false;
            }
    }

    /**
     * Test method for {@link cx.ath.choisnet.net.URLHelper#toString(java.net.URL)}.
     * @throws IOException
     */
    @Test
    public void testToStringURL() throws IOException
    {
        // Is Internet access allowed ?
        Assume.assumeTrue( isInternetAccessAllowed() );

        String s = URLHelper.toString( testURL );

        assertNotNull( s );
    }

    /**
     * Test method for {@link cx.ath.choisnet.net.URLHelper#copy(java.net.URL, java.io.OutputStream)}.
     * @throws IOException
     */
    @Test
    public void testCopyURLOutputStream() throws IOException
    {
        // Is Internet access allowed ?
        Assume.assumeTrue( isInternetAccessAllowed() );

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        URLHelper.copy( testURL, os );

        String s = os.toString();
        //System.out.println( "testURL = " + testURL );
        //System.out.println( "s       = " + s );
        assertNotNull( s );
    }

    /**
     * Test method for {@link cx.ath.choisnet.net.URLHelper#copy(java.net.URL, java.io.File)}.
     * @throws IOException
     */
    @Test
    public void testCopyURLFile() throws IOException
    {
        // Is Internet access allowed ?
        Assume.assumeTrue( isInternetAccessAllowed() );

        File file = File.createTempFile( "testCopyURLFile", "tmp" );
        URLHelper.copy( testURL, file );

        String s = IOHelper.toString( file );
//        System.out.println( "testURL = " + testURL );
//        System.out.println( "s       = " + s );
        assertNotNull( s );
    }

    /**
     * Test method for {@link cx.ath.choisnet.net.URLHelper#copy(java.net.URL, java.io.Writer)}.
     * @throws IOException
     */
    @Test
    public void testCopyURLWriter() throws IOException
    {
        // Is Internet access allowed ?
        Assume.assumeTrue( isInternetAccessAllowed() );

        CharArrayWriter w = new CharArrayWriter();
        URLHelper.copy( testURL, w );

        String s = w.toString();
//        System.out.println( "testURL = " + testURL );
//        System.out.println( "s       = " + s );
        assertNotNull( s );
    }

    /**
     * Test method for {@link cx.ath.choisnet.net.URLHelper#copy(java.net.URL, java.io.Writer, java.lang.String)}.
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    @Test
    public void testCopyURLWriterString() throws UnsupportedEncodingException, IOException
    {
        // Is Internet access allowed ?
        Assume.assumeTrue( isInternetAccessAllowed() );

        CharArrayWriter w = new CharArrayWriter();
        URLHelper.copy( testURL, w, Charset.defaultCharset().displayName() );

        String s = w.toString();
//        System.out.println( "testURL = " + testURL );
//        System.out.println( "s       = " + s );
        assertNotNull( s );
    }

    @Test
    public void testNeverFailTest()
    {
        // empty
    }
}
