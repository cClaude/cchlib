package cx.ath.choisnet.io;

import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class StreamHelper
{
    private final static int DEFAULT_BUFFER_SIZE = 4096;

    private StreamHelper()
    {
        //All static
    }

    /**
     * Copy all remaining data in InputStream to a String
     * and close the Stream.
     * @param is
     * @return content of InputStream
     * @throws java.io.IOException
     */
     public static String toString(InputStream is)
        throws java.io.IOException
    {
        StringBuilder sb     = new StringBuilder();
        byte[]        buffer = new byte[DEFAULT_BUFFER_SIZE];

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
     * Copy all data found in InputStream to OutputStream using buffer
     * @param input
     * @param output
     * @param buffer
     * @throws java.io.IOException
     *
     */
    public static void copy(
            InputStream  input,
            OutputStream output,
            byte[]       buffer
            )
        throws java.io.IOException
    {
        int len;

        while((len = input.read(buffer)) != -1) {
            output.write(buffer, 0, len);
        }
    }

    /**
     *
     * @param input
     * @param output
     * @param bufferSize
     * @throws java.io.IOException
     */
    public static void copy(InputStream input, OutputStream output, int bufferSize)
        throws java.io.IOException
    {
        copy(input, output, new byte[bufferSize]);
    }

    /**
     *
     * @param input
     * @param output
     * @throws java.io.IOException
     */
    public static void copy(InputStream input, OutputStream output)
        throws java.io.IOException
    {
        copy(input, output, DEFAULT_BUFFER_SIZE);
    }

    public static InputStream concat(
            InputStream is1,
            InputStream is2
            )
    {
        InputStream is[] = { is1, is2 };

        return concat(is);
    }

    public static InputStream concat(final InputStream...is)
    {
        return new InputStream() {
            int index;

            public int available()
                throws java.io.IOException
            {
                return is[index].available();
            }

            public void close()
                throws java.io.IOException
            {
                java.io.IOException anIOE = null;

                for(int i = 0; i < is.length; i++) {
                    try {
                        is[i].close();
                    }
                    catch(java.io.IOException e) {
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

            public boolean markSupported()
            {
                return false;
            }

            public int read()
                throws java.io.IOException
            {
                for(; index < is.length; index++)
                {
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
