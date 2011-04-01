package cx.ath.choisnet.net;

import cx.ath.choisnet.ToDo;
import cx.ath.choisnet.io.InputStreamHelper;
import cx.ath.choisnet.io.ReaderHelper;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;

/**
 * <p style="border:groove;">
 * <b>Warning:</b>
 * Insofar the code of this class comes from decompiling
 * my own code following the loss of source code, the use
 * of this class must do so under protest until I have
 * check its stability, it could be subject to significant
 * change.
 * <br/>
 * <br/>
 * <b>Attention:</b>
 * Dans la mesure où le code de cette classe est issue de
 * la décompilation de mon propre code, suite à la perte
 * du code source, l'utilisation de cette classe doit ce
 * faire sous toute réserve tant que je n'ai pas vérifier
 * sa stabilité, elle est donc sujette à des changements 
 * importants.
 * </p>
 *
 * @author Claude CHOISNET
 *
 */
@ToDo(action=ToDo.Action.TEST_CASE)
public class URLHelper
{

    private URLHelper()
    { // All static !
    }

    /**
     * Store URL content in a String
     *
     * @param url to load
     * @return content of the URL
     * @throws java.io.IOException
     */
    public static String toString(URL url)
        throws java.io.IOException
    {
        final InputStream   is = url.openStream();
        String              content;

        try {
            content = InputStreamHelper.toString(is);
        }
        finally {
            is.close();
        }

        return content;
    }

    /**
     * Send URL content to an OutputStrean
     *
     *
     * @param url
     * @param output
     * @throws java.io.IOException
     */
    public static void copy(URL url, OutputStream output)
        throws java.io.IOException
    {
        InputStream input = url.openStream();

        try {
            InputStreamHelper.copy(input, output);
        }
        catch( IOException e ) {
            input.close();
            throw e;
        }

        input.close();
    }

    /**
     * Send URL content to a Writer
     *
     * @param url
     * @param output
     * @throws java.io.IOException
     */
    public static void copy(URL url, Writer output)
        throws java.io.IOException
    {
        Reader input = new BufferedReader(new InputStreamReader(url.openStream()));

        try {
            ReaderHelper.copy(input, output);
        }
        catch( IOException e ) {
            input.close();
            throw e;
        }

        input.close();
    }

    /**
     * Send URL content to a Writer
     *
     * @param url
     * @param output
     * @param charsetName
     * @throws java.io.UnsupportedEncodingException
     * @throws java.io.IOException
     */
    public static void copy(
            URL     url,
            Writer  output,
            String  charsetName
            )
        throws java.io.UnsupportedEncodingException, java.io.IOException
    {
         Reader input = new BufferedReader(new InputStreamReader(url.openStream(), charsetName));

        try {
            ReaderHelper.copy(input, output);
        }
        catch( IOException e ) {
            input.close();
            throw e;
        }

        input.close();
    }

    /**
     * Store URL content in a file
     *
     * @param url   URL to read
     * @param file  File destination
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static void copy(URL url, File file)
        throws java.io.FileNotFoundException,
               java.io.IOException
    {
        OutputStream output = new BufferedOutputStream(
                new FileOutputStream(file)
                );

        try {
            URLHelper.copy(url, output);
        }
        catch( IOException e ) {
            output.close();
            throw e;
        }

        output.close();
    }
}
