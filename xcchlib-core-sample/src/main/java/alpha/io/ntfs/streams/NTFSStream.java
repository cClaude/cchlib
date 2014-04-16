package alpha.io.ntfs.streams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import com.googlecode.cchlib.lang.StringHelper;

/**
 *
 */
public class NTFSStream
{

    private StringBuilder sb;
    private CharBuffer cb;
    private char[] cbuf;

    public NTFSStream()
    {
        sb = new StringBuilder();
        cb = CharBuffer.wrap(sb);
        cbuf = new char[1024];
    }

    public void read( final Reader reader )
        throws IOException
    {
//        cb.clear();
//        reader.read( cb );
//        cb.flip();
        sb.setLength( 0 );
        int len = reader.read( cbuf, 0, cbuf.length );

        if( len > 0 ) {
            sb.append( cbuf, 0, len );
        }
    }

    public String getBegin( final Reader reader )
        throws IOException
    {
        read( reader );

        return cb.toString();
    }

    public String getBegin(File file) throws IOException
    {
        FileReader reader = new FileReader( file );
        read( reader );
        reader.close();

        return sb.toString();
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main( String[] args ) throws IOException
    {
        File   path       = new File("C:/");
        String filename   = "support.txt";
        String streamname = "test";

        File testFile   = new File(path,filename);
        File testStream = new File(path,filename + ':' + streamname);

        System.out.printf( "Create %s\n", testFile );

        Writer wf = new FileWriter( testFile );
        wf.write( "File content !" );
        wf.close();

        System.out.printf( "Create %s\n", testStream );

        Writer ws = new FileWriter( testStream );
        ws.write( "Stream content" );
        ws.close();

        NTFSStream ntfsStream = new NTFSStream();
        System.out.printf( "Content of %s is [%s]\n",
                    testFile,
                    ntfsStream.getBegin(testFile)
                    );
        System.out.printf( "Content of %s is [%s]\n",
                testStream,
                ntfsStream.getBegin(testStream)
                );

        System.out.printf( "1> Content of %s is [%s]\n",
                testStream,
                fastStreamCopy1(testStream)
                );
        System.out.printf( "2> Content of %s is [%s]\n",
                testStream,
                fastStreamCopy2(testStream)
                );
    }

    private static String fastStreamCopy1( File filename )
    {
        FileChannel fc  = null;
        String      s   = StringHelper.EMPTY;
        
        try {
            FileInputStream fis = new FileInputStream( filename );
            
            try {
                fc = fis.getChannel();

                try {
                    // int length = (int)fc.size();

                    MappedByteBuffer byteBuffer = fc.map(
                            FileChannel.MapMode.READ_ONLY, 0, fc.size() );
                    // CharBuffer charBuffer =
                    // Charset.forName("ISO-8859-1").newDecoder().decode(byteBuffer);

                    // ByteBuffer byteBuffer = ByteBuffer.allocate(length);
                    // ByteBuffer byteBuffer = ByteBuffer.allocateDirect(length);
                    // CharBuffer charBuffer = byteBuffer.asCharBuffer();

                    // CharBuffer charBuffer =
                    // ByteBuffer.allocateDirect(length).asCharBuffer();
                    /*int size = charBuffer.length(); if (size > 0) { StringBuffer sb =
                     * new StringBuffer(size); for (int count=0; count<size; count++)
                     * sb.append(charBuffer.get()); s = sb.toString(); }
                     *
                     * if (length > 0) { StringBuffer sb = new StringBuffer(length); for
                     * (int count=0; count<length; count++) {
                     * sb.append(byteBuffer.get()); } s = sb.toString(); } */
                    int size = byteBuffer.capacity();
                    if( size > 0 ) {
                        // Retrieve all bytes in the buffer
                        byteBuffer.clear();
                        byte[] bytes = new byte[size];
                        byteBuffer.get( bytes, 0, bytes.length );
                        s = new String( bytes );
                        }
                    }
                finally {
                    fc.close();
                    fc = null;
                    }
                }
            finally {
                fis.close();
                }
            }
        catch( FileNotFoundException fnfx ) {
            System.err.printf( "File not found: %s\n", fnfx );
            }
        catch( IOException iox ) {
            System.err.printf( "I/O problems: %s\n", iox );
            }

        return s;
    }

    private static String fastStreamCopy2( File filename ) throws IOException
    {
        String      s;

        FileInputStream fis_ = new FileInputStream( filename );
        try {
            FileChannel fc = fis_.getChannel();
            
            try {
                // int length = (int)fc.size();

                MappedByteBuffer byteBuffer = fc.map(
                        FileChannel.MapMode.READ_ONLY, 0, fc.size() );
                // CharBuffer charBuffer =
                // Charset.forName("ISO-8859-1").newDecoder().decode(byteBuffer);

                // ByteBuffer byteBuffer = ByteBuffer.allocate(length);
                // ByteBuffer byteBuffer = ByteBuffer.allocateDirect(length);
                // CharBuffer charBuffer = byteBuffer.asCharBuffer();

                // CharBuffer charBuffer =
                // ByteBuffer.allocateDirect(length).asCharBuffer();
                /*int size = charBuffer.length(); if (size > 0) { StringBuffer sb =
                 * new StringBuffer(size); for (int count=0; count<size; count++)
                 * sb.append(charBuffer.get()); s = sb.toString(); }
                 *
                 * if (length > 0) { StringBuffer sb = new StringBuffer(length); for
                 * (int count=0; count<length; count++) {
                 * sb.append(byteBuffer.get()); } s = sb.toString(); } */
                int size = byteBuffer.capacity();
                if( size > 0 ) {
                    // Retrieve all bytes in the buffer
                    byteBuffer.clear();
                    byte[] bytes = new byte[size];
                    byteBuffer.get( bytes, 0, bytes.length );
                    s = new String( bytes );
                    }
                else {
                    s = StringHelper.EMPTY;
                    }
                }
            finally {
                fc.close();
                }
            }
        finally {
            fis_.close();
            }

        return s;
    }
}
