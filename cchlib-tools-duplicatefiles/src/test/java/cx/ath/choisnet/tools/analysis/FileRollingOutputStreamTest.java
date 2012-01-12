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

    @Test
    public void test1FileRollingOutputStream() throws IOException
    {
        testFileRollingOutputStream( 4096 );
        testFileRollingOutputStream( 5 ); // 5 bytes only :)
    }

    public void testFileRollingOutputStream(
        final int maxsize
        ) throws IOException
    {
        final File         file    = File.createTempFile("FileRollingOutputStreamTest", null);

        logger.info( "work with " + file );
        logger.info( "maxsize " + maxsize );

        final byte[]     b     = new byte[ 30 ];
        final int         off    = 10;
        final int         len    = 5;

        for( int i = 0; i<b.length; i++ ) {
            b[ i ] = (byte)(0x30 + i);
            }

        FileRollingOutputStream os = new FileRollingOutputStream( file, maxsize );

        os.write( b[ 0 ] );
        int l = 1;
        Assert.assertEquals( l, os.length() );
        Assert.assertEquals( l, os.currentLength() );

        os.write( b );
        os.flush();
        l += b.length;
        Assert.assertEquals( l, os.length() );
        //Assert.assertEquals( l % maxsize, os.currentLength() );

        os.write( b, off, len );
        l += len;
        os.flush();
        Assert.assertEquals( l, os.length() );
        //Assert.assertEquals( l, os.currentLength() );

        os.flush();
        os.close();

        Assert.assertEquals( 0, os.currentLength() );
        Assert.assertEquals( l, os.length() );

        int filelen = 0;
        
        for( File f : os.getFileList() ) {
            logger.info( "result in " + f );
            filelen += f.length();
            }
        Assert.assertEquals( filelen, os.length() );
    }
}
