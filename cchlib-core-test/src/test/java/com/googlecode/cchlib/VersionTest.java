package com.googlecode.cchlib;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.junit.Test;

public class VersionTest
{
    private static final Logger LOGGER = Logger.getLogger( VersionTest.class );

    @Test
    public void testVersion() throws IOException, ParseException
    {
        final Version version = Version.getInstance();

        LOGGER.info( "Version is :" + version );

        assertThat( version ).as( "new Version()" ).isNotNull();
        assertThat( version.getName() )
            .as( "version.getName()" )
            .isNotNull()
            .doesNotContain( "${project.name}" )
            .isEqualTo( "cchlib-core" );
        assertThat( version.getVersion() )
            .as( "version.getVersion()" )
            .isNotNull()
            .doesNotContain( "${project.version}" );

        final Date now                 = new Date();
        final long deltaInMilliseconds = TimeUnit.DAYS.toMillis( 1 ); // One day

        assertThat( version.getDate() )
            .as( "version.getDate()" )
            .isNotNull().isCloseTo( now, deltaInMilliseconds );

        assertThat( version.toString() ).as( "version.toString()" ).isNotNull();
    }

    @Test
    public void test_main()
    {
        final String[] args = null;

        // Just test if this call work (entry point valid)
        Version.main( args );
    }
}
