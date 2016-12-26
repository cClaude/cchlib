package com.googlecode.cchlib.swing;

import static org.fest.assertions.api.Assertions.assertThat;
import org.apache.log4j.Logger;
import org.junit.Test;

public class SafeSwingUtilitiesTest
{
    private static final Logger LOGGER = Logger.getLogger( SafeSwingUtilitiesTest.class );

    // Headless configuration is not valid, you must run the
    // JVM using -Djava.awt.headless=true or configure your
    // environment to support Graphics objects
    @Test
    public void test_isHeadlessCorrect()
    {
        final boolean headlessCorrect = SafeSwingUtilities.isHeadlessCorrect();

        LOGGER.info( "SafeSwingUtilities.isHeadless()        = " + SafeSwingUtilities.isHeadless() );
        LOGGER.info( "SafeSwingUtilities.isSwingAvailable()  = " + SafeSwingUtilities.isSwingAvailable() );
        LOGGER.info( "SafeSwingUtilities.isHeadlessCorrect() = " + headlessCorrect );

        System.getProperties().forEach(
            ( key, value ) -> LOGGER.info( "System.getProperty( " + key + " ) = " + value )
            );

        assertThat( headlessCorrect )
            .as( "Headless configuration is not valid, you must run the\n"
                    + "JVM using -Djava.awt.headless=true or configure your\n"
                    + "environment to support Graphics objects" )
            .isTrue();
    }
}
