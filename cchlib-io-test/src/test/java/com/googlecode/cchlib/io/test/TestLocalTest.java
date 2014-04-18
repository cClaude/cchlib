package com.googlecode.cchlib.io.test;

import com.googlecode.cchlib.test.TestConfigurationHelper;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * Allow test case to use specific configuration according
 * to current computer
 * 
 * @since 4.1.7
 */
public class TestLocalTest
{
    private static final Logger LOGGER = Logger.getLogger( TestLocalTest.class );

    @Test
    public void testTestLocal() throws IOException
    {
        TestConfigurationHelper local = new TestConfigurationHelper();
        
        try {
            local.load();
            }
        catch( FileNotFoundException e ) { // $codepro.audit.disable logExceptions
            LOGGER.warn( "No local config" );
            }
        
        TestConfigurationHelper.Config config = local.getConfig();
        
        LOGGER.info( "-- TestLocal.Config --" );
        for( String entry : config.getExistingMACAddressCollection() ) {
            LOGGER.info( "MAC Addr: [" + entry + "]" );
            }
        LOGGER.info( "----------------------" );
    }
}
