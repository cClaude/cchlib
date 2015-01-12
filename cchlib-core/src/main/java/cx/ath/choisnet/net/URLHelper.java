package cx.ath.choisnet.net;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import com.googlecode.cchlib.io.IOHelper;

/**
 * Extra tools for {@link URL}
 */
public final class URLHelper
{

    private URLHelper()
    { // All static !
    }

    private static BufferedReader getBufferedReader( final URL url ) throws IOException
    {
        return new BufferedReader( new InputStreamReader( url.openStream() ) );
    }

    private static BufferedReader getBufferedReader( final URL url, final String charsetName ) throws IOException
    {
        return new BufferedReader( new InputStreamReader( url.openStream(), charsetName ) );
    }

    /**
     * Store URL content in a String
     *
     * @param url to load
     * @return content of the URL
     * @throws IOException
     */
    public static String toString( final URL url ) throws IOException
    {
        try( final Reader reader = getBufferedReader( url ) ) {
            return IOHelper.toString( reader );
            }
    }

    /**
     * Send URL content to an OutputStrean
     *
     * @param url
     * @param output
     * @throws IOException
     */
    public static void copy( final URL url, final OutputStream output )
        throws IOException
    {
        try (InputStream input = url.openStream()) {
            IOHelper.copy(input, output);
            }
    }

    /**
     * Store URL content in a file
     * <BR>
     * File is not created if URL content can't be read
     * (Previous File is not also deleted in this case)
     *
     * @param url   URL to read
     * @param file  File destination
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void copy( final URL url, final File file )
        throws FileNotFoundException, IOException
    {
        try (InputStream input = url.openStream(); OutputStream output = new BufferedOutputStream(
                new FileOutputStream( file )
        )) {
            IOHelper.copy( input, output );
            }
    }

    /**
     * Send URL content to a Writer
     *
     * @param url
     * @param output
     * @throws IOException
     */
    public static void copy( final URL url, final Writer output )
        throws IOException
    {
        try (Reader input = getBufferedReader( url )) {
            IOHelper.copy(input, output);
            }
    }

    /**
     * Send URL content to a Writer
     *
     * @param url
     * @param output
     * @param charsetName
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static void copy(
            final URL       url,
            final Writer    output,
            final String    charsetName
            )
        throws UnsupportedEncodingException, IOException
    {
        try (Reader input = getBufferedReader( url, charsetName )) {
            IOHelper.copy(input, output);
            }
    }
}
