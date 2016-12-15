package cx.ath.choisnet.net;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assume.assumeTrue;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import com.googlecode.cchlib.io.IOHelper;

public class URLHelperTest
{
    private static final Logger LOGGER = Logger.getLogger( URLHelperTest.class );

    private URL testURL;

    @Before
    public void setUp() throws MalformedURLException
    {
        this.testURL = new URL( "https://github.com/cClaude/cchlib/" );
    }

    /**
     * Checking Internet connection using : testURL
     * @return Is Internet access allowed ?
     */
    private boolean isInternetAccessAllowed()
    {
        LOGGER.warn( "Checking Internet connection using: " + this.testURL );

        try( final InputStream is = this.testURL.openStream()) {
            return true;
            }
        catch( final IOException e ) {
            LOGGER.warn( "NO INTERNET: " + e.getMessage() );
            return false;
            }
    }

    @Test
    public void test_if_not_internet_access()
    {
        // at least one test is required (if no Internet access)

        assertThat( this.testURL ).isNotNull();
    }

    @Test
    public void testToStringURL() throws IOException
    {
        // Is Internet access allowed ?
        assumeTrue( isInternetAccessAllowed() );

        final String actual = URLHelper.toString( this.testURL );

        assertThat( actual ).isNotNull();
    }

    @Test
    public void testCopyURLOutputStream() throws IOException
    {
        // Is Internet access allowed ?
        assumeTrue( isInternetAccessAllowed() );

        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        URLHelper.copy( this.testURL, os );

        final String actual = os.toString();

        assertThat( actual ).isNotNull();
    }

    @Test
    public void testCopyURLFile() throws IOException
    {
        // Is Internet access allowed ?
        assumeTrue( isInternetAccessAllowed() );

        final File file = File.createTempFile( "testCopyURLFile", "tmp" );
        file.deleteOnExit();

        URLHelper.copy( this.testURL, file );

        final String actual = IOHelper.toString( file );

        assertThat( actual ).isNotNull();
    }

    @Test
    public void testCopyURLWriter() throws IOException
    {
        // Is Internet access allowed ?
        assumeTrue( isInternetAccessAllowed() );

        final CharArrayWriter writer = new CharArrayWriter();

        URLHelper.copy( this.testURL, writer );

        final String actual = writer.toString();

        assertThat( actual ).isNotNull();
    }

    /**
     * Test method for {@link URLHelper#copy(URL,Writer,String)}.
     * @throws IOException if any
     * @throws UnsupportedEncodingException if any
     */
    @Test
    public void testCopyURLWriterString() throws UnsupportedEncodingException, IOException
    {
        // Is Internet access allowed ?
        assumeTrue( isInternetAccessAllowed() );

        final CharArrayWriter writer = new CharArrayWriter();

        URLHelper.copy( this.testURL, writer, Charset.defaultCharset().displayName() );

        final String actual = writer.toString();

        assertThat( actual ).isNotNull();
    }
}
