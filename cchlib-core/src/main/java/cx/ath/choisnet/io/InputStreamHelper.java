package cx.ath.choisnet.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 * @author Claude CHOISNET
 *
 */
public final class InputStreamHelper
{

    private InputStreamHelper()
    {
    }

    public static String toString(InputStream is)
        throws java.io.IOException
    {
        StringBuilder   sb      = new StringBuilder();
        byte[]          buffer  = new byte[2048];

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
     * 
     * @param input
     * @param output
     * @param bufferSize
     * @throws IOException
     */
    public static void copy(InputStream input, OutputStream output, int bufferSize)
        throws IOException
    {
        byte[]  buffer = new byte[bufferSize];
        int     len;

        while((len = input.read(buffer)) != -1) {
            output.write(buffer, 0, len);
        }

        output.flush();
    }

    public static void copy(InputStream input, OutputStream output)
        throws java.io.IOException
    {
        InputStreamHelper.copy(input, output, 2048);
    }

    public static void copy(File inputFile, File outputFile)
        throws java.io.IOException
    {
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

    public static InputStream concat(InputStream is1,InputStream is2)
    {
        InputStream is[] = { is1, is2 };

        return InputStreamHelper.concat(is);

    }

    public static InputStream concat(final InputStream...is)
    {
        return new InputStream()
        {
            int index;

            @Override
            public int available() throws IOException
            {
                return is[index].available();
            }

            @Override
            public void close() throws IOException
            {
                IOException anIOE = null;

                for(int i = 0; i < is.length; i++) {
                    try {
                        is[i].close();
                    }
                    catch( IOException e ) {
                        anIOE = e;
                    }
                }

                if(anIOE != null) {
                    throw anIOE;
                }
                else {
                    return;
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
                for(; index < is.length; index++) {
                    int r = is[index].read();

                    if(r != -1) {
                        return r;
                    }
                }

                return -1;
            }
        };
    }
}
