package cx.ath.choisnet.io;

import java.io.Reader;
import java.io.Writer;

/**
 * 
 * @author Claude CHOISNET
 *
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
     * @param input
     * @return content of Reader
     * @throws java.io.IOException
     */
   public static String toString(Reader input)
       throws java.io.IOException
   {
       StringBuilder sb     = new StringBuilder();
       char[]        buffer = new char[DEFAULT_BUFFER_SIZE];

       try {
           int len;
           
           while((len = input.read(buffer)) != -1) {
               sb.append(buffer, 0, len);
           }
       }
       finally {
           input.close();
       }

       return sb.toString();
   }

   /**
    * 
    * @param input
    * @param output
    * @param buffer
    * @throws java.io.IOException
    */
   public static void copy(
           Reader input, 
           Writer output, 
           char[] buffer
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
   public static void copy(
           Reader input,
           Writer output, 
           int bufferSize
           )
       throws java.io.IOException
   {
       copy( input, output, new char[bufferSize] );
   }
   
   /**
    * 
    * @param input
    * @param output
    * @throws java.io.IOException
    */
   public static void copy(Reader input, Writer output)
       throws java.io.IOException
   {
       copy(input, output, DEFAULT_BUFFER_SIZE);
   }

    public static Reader concat(Reader reader1,Reader reader2)
    {
        Reader[] readers = { reader1, reader2 };

        return concat(readers);
    }

    public static Reader concat(
            final Reader...readers
            )
    {
        return new java.io.Reader() {
            int index;

            public void close()
                throws java.io.IOException
            {
                java.io.IOException anIOE = null;
                
                for(int i = 0; i < readers.length; i++)  {
                    try {
                        readers[i].close();
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
                for(; index < readers.length; index++) {
                    int r = readers[index].read();

                    if(r != -1)  {
                        return r;
                    }
                }
                
                return -1;
            }

            public int read(char[] cbuf, int off, int len)
                throws java.io.IOException
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
