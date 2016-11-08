package com.googlecode.cchlib.net;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.cchlib.test.TestConfigurationHelper;

public class WakeOnLanTest
{
    private static final Logger LOGGER = Logger.getLogger( WakeOnLanTest.class );

    private TestConfigurationHelper.Config config;

    @Before
    public void setup() throws IOException
    {
        TestConfigurationHelper testLocal = new TestConfigurationHelper();

        try {
            testLocal.load();
            }
        catch( FileNotFoundException e ) { // $codepro.audit.disable logExceptions
            LOGGER.warn( "No config to load" );
            }
        config = testLocal.getConfig();

        LOGGER.warn( "setup() done" );
    }

    @Test
    public void testWakeOnLan() throws UnknownHostException, SocketException, IllegalArgumentException, IOException
    {
        final WakeOnLan          wol                  = new WakeOnLan();
        final Collection<String> macAddressCollection = config.getExistingMACAddressCollection();

        LOGGER.warn( "macAddressCollection = " + macAddressCollection.size() );

        for( String macAddress : macAddressCollection ) {
            LOGGER.warn( "notify macAddress = " + macAddress );
            wol.notify( macAddress );
            }
    }

//    public static void main(String...args) throws IOException
//    {
//        TestConfigurationHelper        testLocal = new TestConfigurationHelper();
//        TestConfigurationHelper.Config config    = testLocal.getConfig();
//
//        config.setExistingMACAddressCollection( "XX-XX-XX-XX-XX-XX" );
//        testLocal.save();
//    }
}
