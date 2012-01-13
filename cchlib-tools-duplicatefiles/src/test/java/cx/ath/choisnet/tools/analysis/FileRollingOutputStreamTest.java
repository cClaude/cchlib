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

    public void testFileRollingOutputStream(
        final int maxsize
        ) throws IOException
    {
        final File file = File.createTempFile(
        		"FileRollingOutputStreamTest", 
        		""
        		);

        logger.info( "work with " + file );
        logger.info( "maxsize " + maxsize );

        FileRollingOutputStream os = new FileRollingOutputStream( file, maxsize );

        os.write( BUFFER[ 0 ] );
        int l = 1;
        Assert.assertEquals( l, os.length() );
        Assert.assertEquals( l, os.currentLength() );

        os.write( BUFFER );
        os.flush();
        l += BUFFER.length;
        Assert.assertEquals( l, os.length() );
        
        logger.info( "os.length() = " + os.length() );
        logger.info( "os.currentLength() = " + os.currentLength() );
        Assert.assertEquals( l % maxsize, os.currentLength() );

        os.write( BUFFER, BUFFER_OFFSET, LEN );
        l += LEN;
        os.flush();
        Assert.assertEquals( l, os.length() );
        Assert.assertEquals( l, os.currentLength() );

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
