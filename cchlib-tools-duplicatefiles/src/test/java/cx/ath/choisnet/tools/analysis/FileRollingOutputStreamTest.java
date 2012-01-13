package cx.ath.choisnet.tools.analysis;

import java.io.File;
import java.io.IOException;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 *
 *
 */
public class FileRollingOutputStreamTest
{
    private final static Logger logger = Logger.getLogger( FileRollingOutputStreamTest.class );

    final static byte[]	BUFFER 			= new byte[ 30 ];
    final static int    BUFFER_OFFSET	= BUFFER.length / 3;
    final static int    LEN    			= BUFFER.length / 6;

    static {
        for( int i = 0; i<BUFFER.length; i++ ) {
            BUFFER[ i ] = (byte)(0x30 + i);
            }
        }

    @Test
    public void test1FileRollingOutputStream() throws IOException
    {
        testFileRollingOutputStream( 4096 );
    }

    @Test
    public void test2FileRollingOutputStream() throws IOException
    {
        testFileRollingOutputStream( 5 ); // 5 bytes only :)
    }

    @Test
    public void test3FileRollingOutputStream() throws IOException
    {
        testFileRollingOutputStream( 35 );
        testFileRollingOutputStream( 36 );
        testFileRollingOutputStream( 37 );
    }

    public void testFileRollingOutputStream(
        final int maxsize
        ) throws IOException
    {
        final String assertMsgPrefix = "max:" + maxsize + " ->";
        final File file = File.createTempFile(
                "FileRollingOutputStreamTest",
                ""
                );
        final FileRoller fileRoller = new DefaultFileRoller( file );

        logger.info( "work with " + file );
        logger.info( "maxsize " + maxsize );

        FileRollingOutputStream os = new FileRollingOutputStream( fileRoller, maxsize );

        os.write( BUFFER[ 0 ] );
        int l = 1;
        Assert.assertEquals( assertMsgPrefix, l, os.length() );
        //Assert.assertEquals( assertMsgPrefix, l, os.currentLength() );

        os.write( BUFFER );
        l += BUFFER.length;
        Assert.assertEquals( assertMsgPrefix, l, os.length() );

        //logger.info( "os.currentLength() = " + os.currentLength() );
        //Assert.assertEquals( assertMsgPrefix, l % maxsize, os.currentLength() );

        os.write( BUFFER, BUFFER_OFFSET, LEN );
        l += LEN;
        Assert.assertEquals( assertMsgPrefix, l, os.length() );
        //Assert.assertEquals( assertMsgPrefix, l, os.currentLength() );

        os.flush();
        //Assert.assertNotNull( os.getCurrentFile() );

        logger.info(
            assertMsgPrefix
                + "os.length() after flush = "
                + os.length()
            );

        os.close();
        //Assert.assertNull( os.getCurrentFile() );

        //Assert.assertEquals( assertMsgPrefix, 0, os.currentLength() );
        Assert.assertEquals( assertMsgPrefix, l, os.length() );

        int filelen = 0;

        for( File f : os.getFileList() ) {
            logger.info(
                assertMsgPrefix
                    + "result in " + f
                    + " (" + f.length() + ")"
                );
            filelen += f.length();

            if( f.length() > maxsize ) {
                logger.warn(
                    assertMsgPrefix
                        + " length found is " + f.length()
                        );
                }

            f.delete(); // cleanup
            }

        Assert.assertEquals( assertMsgPrefix, filelen, os.length() );
        Assert.assertEquals( assertMsgPrefix, os.getMaxLength(), maxsize );
    }
}
