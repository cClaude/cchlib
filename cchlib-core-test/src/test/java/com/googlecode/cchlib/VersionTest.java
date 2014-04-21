package com.googlecode.cchlib;

import java.io.IOException;
import java.text.ParseException;
import org.junit.Assert;
import org.junit.Test;

public class VersionTest {

    @Test
    public void testVersion() throws IOException, ParseException
    {
        final Version version = Version.getInstance();

        Assert.assertNotNull( "new Version()", version );
        Assert.assertNotNull( "version.getName()", version.getName() );
        Assert.assertNotNull( "version.getVersion()", version.getVersion() );
        Assert.assertNotNull( "version", version.getDate() );
        Assert.assertNotNull( "version.toString()", version.toString() );
    }
}
