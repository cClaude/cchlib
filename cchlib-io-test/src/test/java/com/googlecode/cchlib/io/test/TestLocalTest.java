package com.googlecode.cchlib.io.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.googlecode.cchlib.test.TestConfigurationHelper;

/**
 * Allow test case to use specific configuration according
 * to current computer
 * 
 * @since 4.1.7
 */
public class TestLocalTest
{
    private final static Logger logger = Logger.getLogger( TestLocalTest.class );

    @Test
    public void testTestLocal() throws IOException
    {
        TestConfigurationHelper local = new TestConfigurationHelper();
        
        try {
            local.load();
            }
        catch( FileNotFoundException e ) { // $codepro.audit.disable logExceptions
            logger.warn( "No local config" );
            }
        
        TestConfigurationHelper.Config config = local.getConfig();
        
        logger.info( "-- TestLocal.Config --" );
        for( String entry : config.getExistingMACAddressCollection() ) {
            logger.info( "MAC Addr: [" + entry + "]" );
            }
        logger.info( "----------------------" );
    }
}
