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

public class NTFSStream
{
    private final StringBuilder sb;
    private final CharBuffer    cb;
    private final char[]        cbuf;

    public NTFSStream()
    {
        this.sb = new StringBuilder();
        this.cb = CharBuffer.wrap(this.sb);
        this.cbuf = new char[1024];
    }

    @SuppressWarnings("squid:S106")
    private static void printf( final String format, final Object...args )
    {
        System.out.printf( format, args );
    }

    @SuppressWarnings("squid:S106")
    private static void printfErr( final String format, final Object...args )
    {
        System.err.printf( format, args );
    }

    public void read( final Reader reader ) throws IOException
    {
        this.sb.setLength( 0 );

        final int len = reader.read( this.cbuf, 0, this.cbuf.length );

        if( len > 0 ) {
            this.sb.append( this.cbuf, 0, len );
        }
    }

    public String getBegin( final Reader reader ) throws IOException
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

        printf( "Create %s%n", testFile );

        try( final Writer wf = new FileWriter( testFile ) ) {
            wf.write( "File content !" );
        }

        printf( "Create %s%n", testStream );

        try( final Writer ws = new FileWriter( testStream ) ) {
            ws.write( "Stream content" );
        }

        final NTFSStream ntfsStream = new NTFSStream();
        printf( "Content of %s is [%s]\n",
                    testFile,
                    ntfsStream.getBegin(testFile)
                    );
        printf( "Content of %s is [%s]\n",
                testStream,
                ntfsStream.getBegin(testStream)
                );

        printf( "1> Content of %s is [%s]\n",
                testStream,
                fastStreamCopy1(testStream)
                );
        printf( "2> Content of %s is [%s]\n",
                testStream,
                fastStreamCopy2(testStream)
                );
    }

    private static String fastStreamCopy1( final File filename )
    {
        String s = StringHelper.EMPTY;

        try( final FileInputStream fis = new FileInputStream( filename ) ) {
             try( final FileChannel fc = fis.getChannel() ) {

                 final MappedByteBuffer byteBuffer = fc.map(
                         FileChannel.MapMode.READ_ONLY, 0, fc.size() );

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
            printfErr( "File not found: %s\n", fnfx );
            }
        catch( final IOException iox ) {
            printfErr( "I/O problems: %s\n", iox );
            }

        return s;
    }

    private static String fastStreamCopy2( final File filename ) throws IOException
    {
        final String s;

        try( final FileInputStream fis = new FileInputStream( filename ) ) {
             try( final FileChannel fc = fis.getChannel() ) {
                final MappedByteBuffer byteBuffer = fc.map(
                        FileChannel.MapMode.READ_ONLY,
                        0,
                        fc.size()
                        );
                final int size = byteBuffer.capacity();

                if( size > 0 ) {
                    // Retrieve all bytes in the buffer
                    byteBuffer.clear();
                    final byte[] bytes = new byte[size];
                    byteBuffer.get( bytes, 0, bytes.length );
                    s = new String( bytes );
                    }
                else {
                    s = StringHelper.EMPTY;
                    }
                }
            }

        return s;
    }
}
