/**
 * 
 */
package alpha.cx.ath.choisnet.net.testcase;

import java.io.IOException;
import java.net.UnknownHostException;
import org.apache.log4j.Logger;
import alpha.cx.ath.choisnet.net.Pinger;
import alpha.cx.ath.choisnet.net.PingerException;
import junit.framework.TestCase;

/**
 * @author Claude CHOISNET
 */
public class PingerTest extends TestCase 
{
    private final static transient Logger slogger = Logger.getLogger( PingerTest.class );

    private static String localhostIp = "127.0.0.1";
    private static String localhost = "localhost";
    private static String externhost = "google.com";

    // ------------ helloPing ----------------
    private void tst_helloPing( String host ) 
        throws PingerException
    {
        slogger.info( "try helloPing on [" + host + "]" );
        boolean result = Pinger.helloPing( host );

        slogger.info( "helloPing on [" + host + "] :" + result );
        assertTrue("Can't ping " + host, result);
    }
/*
    public void test_helloPing_LocalHostIp() 
        throws PingerException
    {
        tst_helloPing( localhostIp );
    }

    public void test_helloPing_LocalHost()
        throws PingerException
    {
        tst_helloPing( localhost );
    }

    public void test_helloPing_externHost()
        throws PingerException
    {
        tst_helloPing( externhost );
    }
*/
    // ------------ ping (static) ----------------
    private void tst_ping(String host) 
        throws UnknownHostException, IOException
    {
        slogger.info( "try ping on [" + host + "]" );
        boolean result = Pinger.ping( host, Pinger.DEFAULT_TIMEOUT );

        slogger.info( "ping on [" + host + "] :" + result );
        assertTrue("Can't ping " + host, result);
    }

    public void test_ping_LocalHostIP() 
        throws UnknownHostException, IOException
    {
        tst_ping( localhostIp );
    }

    public void test_ping_LocalHost()
        throws UnknownHostException, IOException
    {
        tst_ping( localhost );
    }

    public void test_ping_externHost()
        throws UnknownHostException, IOException
    {
        tst_ping( externhost );
    }

}
