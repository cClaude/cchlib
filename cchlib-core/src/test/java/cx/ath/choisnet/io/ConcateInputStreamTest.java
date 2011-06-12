package cx.ath.choisnet.io;

import java.io.InputStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Claude
 */
public class ConcateInputStreamTest
{

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    public void test( InputStream is ) throws Exception
    {
        is.available();
        is.close();
        //is.equals( obj );
        //is.hashCode();
        /*
        int readlimit;
        is.markSupported();
        is.mark( readlimit );
        is.reset();
        */

        /*
        is.read();
        byte[] b;
        is.read( b );
        int off;
        int len;
        is.read( b, off, len );
        long skiplen;
        is.skip( skiplen );
        */
    }

    @Test
    public void testTODO()
    {
        int todo; // TODO ConcateInputStreamTest
    }

}
