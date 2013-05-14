package com.googlecode.cchlib.net;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.Before;
import com.googlecode.cchlib.test.TestConfigurationHelper;

public class WakeOnLanTest
{
    private static final Logger logger = Logger.getLogger( WakeOnLanTest.class );
    private TestConfigurationHelper.Config config;
    
    @Before
    public void setup() throws IOException
    {
        TestConfigurationHelper testLocal = new TestConfigurationHelper();
        
        try {
            testLocal.load();
            }
        catch( FileNotFoundException e ) { // $codepro.audit.disable logExceptions
            logger.warn( "No config to load" );
            }
        config = testLocal.getConfig();
    }
    
    @Test
    public void testWakeOnLan() throws UnknownHostException, SocketException, IllegalArgumentException, IOException
    {
        final WakeOnLan wol = new WakeOnLan();
        
        for( String macAddress : config.getExistingMACAddressCollection() ) {
            wol.notify( macAddress );
            }
    }
}
