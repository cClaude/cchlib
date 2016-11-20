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

/**
 *
 */
public class NTFSStream
{
    private static final String EMPTY = "";
    private final StringBuilder sb;
    private final CharBuffer cb;
    private final char[] cbuf;

    public NTFSStream()
    {
        this.sb = new StringBuilder();
        this.cb = CharBuffer.wrap(this.sb);
        this.cbuf = new char[1024];
    }

    public void read( final Reader reader )
        throws IOException
    {
//        cb.clear();
//        reader.read( cb );
//        cb.flip();
        this.sb.setLength( 0 );
        final int len = reader.read( this.cbuf, 0, this.cbuf.length );

        if( len > 0 ) {
            this.sb.append( this.cbuf, 0, len );
        }
    }

    public String getBegin( final Reader reader )
        throws IOException
    {
        read( reader );

        return this.cb.toString();
    }

    public String getBegin(final File file) throws IOException
    {
        try( final FileReader reader = new FileReader( file ) ) {
            read( reader );
        }

        return this.sb.toString();
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main( final String[] args ) throws IOException
    {
        final File   path       = new File("C:/");
        final String filename   = "support.txt";
        final String streamname = "test";

        final File testFile   = new File(path,filename);
        final File testStream = new File(path,filename + ':' + streamname);

        System.out.printf( "Create %s\n", testFile );

        try( final Writer wf = new FileWriter( testFile ) ) {
            wf.write( "File content !" );
        }

        System.out.printf( "Create %s\n", testStream );

        try( final Writer ws = new FileWriter( testStream ) ) {
            ws.write( "Stream content" );
        }

        final NTFSStream ntfsStream = new NTFSStream();
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

    private static String fastStreamCopy1( final File filename )
    {
        String s = EMPTY;

        try( final FileInputStream fis = new FileInputStream( filename ) ) {
             try( final FileChannel fc = fis.getChannel() ) {

                 // int length = (int)fc.size();

                 final MappedByteBuffer byteBuffer = fc.map(
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
                 final int size = byteBuffer.capacity();

                 if( size > 0 ) {
                     // Retrieve all bytes in the buffer
                     byteBuffer.clear();
                     final byte[] bytes = new byte[size];
                     byteBuffer.get( bytes, 0, bytes.length );
                     s = new String( bytes );
                     }
                }
            }
        catch( final FileNotFoundException fnfx ) {
            System.err.printf( "File not found: %s\n", fnfx );
            }
        catch( final IOException iox ) {
            System.err.printf( "I/O problems: %s\n", iox );
            }

        return s;
    }

    private static String fastStreamCopy2( final File filename ) throws IOException
    {
        final String s;

        try( final FileInputStream fis_ = new FileInputStream( filename ) ) {
             try( final FileChannel fc = fis_.getChannel() ) {
                // int length = (int)fc.size();

                final MappedByteBuffer byteBuffer = fc.map(
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
                final int size = byteBuffer.capacity();

                if( size > 0 ) {
                    // Retrieve all bytes in the buffer
                    byteBuffer.clear();
                    final byte[] bytes = new byte[size];
                    byteBuffer.get( bytes, 0, bytes.length );
                    s = new String( bytes );
                    }
                else {
                    s = EMPTY;
                    }
                }
            }

        return s;
    }
}
