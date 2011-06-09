package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * Tools for {@link Reader} operations
 *
 * @author Claude CHOISNET
 */
public final class ReaderHelper
{
    private final static int DEFAULT_BUFFER_SIZE = 4096;

    /** All static */
    private ReaderHelper()
    {
    }

    /**
     * Copy all remaining data in Reader to a String
     * and close the Reader.
     *
     * @param input Reader to read
     * @return content of Reader
     * @throws IOException
     */
   public static String toString( Reader input )
       throws IOException
   {
       StringBuilder sb     = new StringBuilder();
       char[]        buffer = new char[DEFAULT_BUFFER_SIZE];

       try {
           int len;

           while( (len = input.read(buffer)) != -1 ) {
               sb.append(buffer, 0, len);
               }
           }
       finally {
           input.close();
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
    */
   public static void copy(
           final Reader input,
           final Writer output,
           final char[] buffer
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
    * Copy input content to output.
    *
    * @param input  {@link Reader} to read from
    * @param output {@link Writer} to write to
    * @param bufferSize Buffer size to use for copy
    * @throws IOException if any
    */
   public static void copy(
           final Reader   input,
           final Writer   output,
           final int      bufferSize
           )
       throws IOException
   {
       copy( input, output, new char[bufferSize] );
   }

   /**
    * Copy input content to output.
    *
    * @param input  {@link Reader} to read from
    * @param output {@link Writer} to write to
    * @throws IOException if any
    */
   public static void copy( final Reader input, final Writer output )
       throws IOException
   {
       copy(input, output, DEFAULT_BUFFER_SIZE);
   }

   /**
    * TODO: doc!
    *
    * @param reader1
    * @param reader2
    * @return
    */
   public static Reader concat( Reader reader1, Reader reader2 )
    {
        Reader[] readers = { reader1, reader2 };

        return concat(readers);
    }

    /**
     * TODO: doc!
     *
     * @param readers
     * @return
     */
    public static Reader concat(
            final Reader...readers
            )
    {
        return new Reader() {
            int index;

            @Override
            public void close() throws IOException
            {
                IOException anIOE = null;

                for( int i = 0; i < readers.length; i++ ) {
                    try {
                        readers[i].close();
                        }
                    catch(IOException e) {
                        anIOE = e;
                        }
                    }

                if( anIOE != null ) {
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
                for(; index < readers.length; index++) {
                    int r = readers[index].read();

                    if(r != -1)  {
                        return r;
                    }
                }

                return -1;
            }

            @Override
            public int read(char[] cbuf, int off, int len)
                throws IOException
            {
                for(; index < readers.length; index++) {
                    int rlen = readers[index].read(cbuf, off, len);

                    if(rlen != -1) {
                        return rlen;
                    }
                }

                return -1;
            }
        };
    }
}
