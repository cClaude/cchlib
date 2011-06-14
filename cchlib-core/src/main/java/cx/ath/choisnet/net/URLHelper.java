package cx.ath.choisnet.net;

import cx.ath.choisnet.io.InputStreamHelper;
import cx.ath.choisnet.io.ReaderHelper;
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

/**
 * Extra tools for {@link URL}
 */
public class URLHelper
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
        final Reader    reader = getBufferedReader( url );
        String          content;

        try {
            content = ReaderHelper.toString( reader );
            }
        finally {
            reader.close();
            }

        return content;
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
        final InputStream input = url.openStream();

        try {
            InputStreamHelper.copy(input, output);
            }
        catch( IOException e ) {
            throw e;
            }
        finally {
            input.close();
            }
    }

    /**
     * Store URL content in a file
     *
     * @param url   URL to read
     * @param file  File destination
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void copy( final URL url, final File file )
        throws FileNotFoundException, IOException
    {
        final OutputStream output = new BufferedOutputStream(
                                        new FileOutputStream( file )
                                        );

        try {
            URLHelper.copy( url, output );
            }
        catch( IOException e ) {
            throw e;
            }
        finally {
            output.close();
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
        final Reader input = getBufferedReader( url );

        try {
            ReaderHelper.copy(input, output);
            }
        catch( IOException e ) {
            throw e;
            }
        finally {
            input.close();
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
        final Reader input = getBufferedReader( url, charsetName );

        try {
            ReaderHelper.copy(input, output);
            }
        catch( IOException e ) {
            throw e;
            }
        finally {
            input.close();
            }
    }
}
