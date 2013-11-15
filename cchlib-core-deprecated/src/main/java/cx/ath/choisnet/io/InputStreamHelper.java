package cx.ath.choisnet.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * Provide some extra tools for {@link InputStream} operations
 *
 * @see FileHelper
 * @see ReaderHelper
 * @deprecated use {@link com.googlecode.cchlib.io.IOHelper} instead
 */
@Deprecated
public final class InputStreamHelper
{
    private static final int DEFAULT_BUFFER_SIZE = 4096;

    private InputStreamHelper()
    {
    }

    /**
     * Copy all remaining data in {@link InputStream} to an byte array
     * and close the {@link InputStream}.
     *
     * @param input {@link InputStream} to read
     * @return content of {@link InputStream}
     * @throws IOException if any
     * @since 4.1.5
     */
    public static byte[] toByteArray( final InputStream input )
        throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        copy( input, out );
        input.close();

        return out.toByteArray();
    }

    /**
     * @deprecated use {@link ReaderHelper#toString(java.io.Reader)} instead
     */
    @Deprecated
    public static String toString( final InputStream is )
        throws IOException
    {
        StringBuilder   sb      = new StringBuilder();
        byte[]          buffer  = new byte[DEFAULT_BUFFER_SIZE];

        try {
            int len;

            while((len = is.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, len));
            }
        }
        finally {
            is.close();
        }

        return sb.toString();
    }

    /**
     * Copy input content to output.
     *
     * @param input  {@link Reader} to read from
     * @param output {@link Writer} to write to
     * @param buffer Buffer to use for copy
     * @throws IOException if any
     * @since 4.1.5
     */
    public static void copy(
            final InputStream   input,
            final OutputStream  output,
            byte[]              buffer
            )
        throws IOException
    {
        int len;

        while((len = input.read(buffer)) != -1) {
            output.write(buffer, 0, len);
            }

        output.flush();
    }

    /**
     *
     * Copy input content to output.
     *
     * @param input  {@link Reader} to read from
     * @param output {@link Writer} to write to
     * @param bufferSize Buffer size to use for copy
     * @throws IOException if any
     */
    public static void copy(
            final InputStream   input,
            final OutputStream  output,
            final int           bufferSize
            )
        throws IOException
    {
        InputStreamHelper.copy(input, output, new byte[bufferSize] );
    }

    /**
    *
    * Copy input content to output.
    *
    * @param input  {@link Reader} to read from
    * @param output {@link Writer} to write to
    * @throws IOException if any
    */
    public static void copy(
            final InputStream   input,
            final OutputStream  output
            )
        throws IOException
    {
        InputStreamHelper.copy(input, output, DEFAULT_BUFFER_SIZE);
    }

    /**
     * @deprecated use {@link FileHelper#copy(File, File)} instead
     */
    @Deprecated
    public static void copy(File inputFile, File outputFile)
        throws IOException
    {
        @SuppressWarnings("resource")
        InputStream     input  = new BufferedInputStream(new FileInputStream(inputFile));
        OutputStream    output = new BufferedOutputStream(new FileOutputStream(outputFile));

        try {
            InputStreamHelper.copy(input, output, 4096);
            }
        finally {
            try { input.close(); } catch(Exception ignore) {}
            output.close();
            }
     }

    /**
     * @deprecated use {@link ConcateInputStream} instead
     */
    @Deprecated
    public static InputStream concat(InputStream is1, InputStream is2)
    {
        InputStream[] is = { is1, is2 };

        return InputStreamHelper.concat(is);
    }

    /**
     * Compare content of two {@link InputStream}. {@link InputStream} are consumed but
     * are not closed after this call.
     *
     * @param is1   an {@link InputStream}
     * @param is2   an other {@link InputStream}
     * @return true if content (and size) of {@link InputStream} are equals.
     * @throws IOException if any IO error occur.
     * @since 4.1.5
     */
    public static final boolean isEquals( InputStream is1, InputStream is2 )
        throws IOException
    {
        for(;;) {
            int c1 = is1.read();
            int c2 = is2.read();

            if( c1 != c2 ) {
                return false;
                }
            if( c1 == -1 ) { // and c2 == -1 since c1 == c2
                return true;
                }
        }
    }

    /**
     * Compare content of an {@link InputStream} with an array of bytes. {@link InputStream} is consumed but
     * are not closed after this call.
     *
     * @param is    an {@link InputStream}
     * @param bytes an array of bytes
     * @return true if content (and size) of {@link InputStream} is equals to array content.
     * @throws IOException if any IO error occur.
     * @since 4.1.5
     */
    public static boolean isEquals( final InputStream is, final byte[] bytes )
        throws IOException
    {
        int index = 0;

        for(;;) {
            int c1 = is.read();
            int c2 = (index < bytes.length) ? (0x00FF & bytes[ index++ ]) : -1;

            if( c1 != c2 ) {
                return false;
                }
            if( c1 == -1 ) { // and c2 == -1 since c1 == c2
                return true;
                }
        }
    }

    /**
     * @deprecated use {@link ConcateInputStream} instead
     */
    @Deprecated
    public static InputStream concat(final InputStream...is)
    {
        return new ConcateInputStream( is );
//        return new InputStream()
//        {
//            int index;
//
//            @Override
//            public int available() throws IOException
//            {
//                return is[index].available();
//            }
//
//            @Override
//            public void close() throws IOException
//            {
//                IOException anIOE = null;
//
//                for(int i = 0; i < is.length; i++) {
//                    try {
//                        is[i].close();
//                    }
//                    catch( IOException e ) {
//                        anIOE = e;
//                    }
//                }
//
//                if(anIOE != null) {
//                    throw anIOE;
//                }
//                else {
//                    return;
//                }
//            }
//
//            @Override
//            public boolean markSupported()
//            {
//                return false;
//            }
//
//            @Override
//            public int read() throws IOException
//            {
//                for(; index < is.length; index++) {
//                    int r = is[index].read();
//
//                    if(r != -1) {
//                        return r;
//                    }
//                }
//
//                return -1;
//            }
//        };
    }

}
