package cx.ath.choisnet.net;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import com.googlecode.cchlib.io.IOHelper;

/**
 *
 */
public class URLHelperTest
{
    private static final Logger LOGGER = Logger.getLogger( URLHelperTest.class );

    private URL testURL;

    @Before
    public void setUp() throws MalformedURLException
    {
        testURL = new URL( "https://code.google.com/p/cchlib/" );
    }

    @After
    public void tearDown()
    {}

    /**
     * Checking Internet connection using : testURL
     * @return Is Internet access allowed ?
     */
    private boolean isInternetAccessAllowed()
    {
        LOGGER.warn( "Checking Internet connection using: " + testURL );

        try {
            InputStream is = testURL.openStream();

            is.close();
            return true;
            }
        catch( IOException e ) {
            LOGGER.warn( "NO INTERNET: " + e.getMessage() );
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

        Assert.assertNotNull( s );
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

        Assert.assertNotNull( s );
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

        File file = File.createTempFile( "testCopyURLFile", "tmp" ); // $codepro.audit.disable deleteTemporaryFiles
        URLHelper.copy( testURL, file );

        String s = IOHelper.toString( file );

        Assert.assertNotNull( s );
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

        final CharArrayWriter writer = new CharArrayWriter();

        URLHelper.copy( testURL, writer );

        final String s = writer.toString();

        Assert.assertNotNull( s );
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

        CharArrayWriter writer = new CharArrayWriter();

        URLHelper.copy( testURL, writer, Charset.defaultCharset().displayName() );

        final String str = writer.toString();

        Assert.assertNotNull( str );
    }

    @Test
    public void testNeverFailTest()
    {
        // empty
    }
}
