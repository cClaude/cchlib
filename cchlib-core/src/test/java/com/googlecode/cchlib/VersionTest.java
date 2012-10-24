package com.googlecode.cchlib;

import java.io.IOException;
import java.text.ParseException;
import org.junit.Assert;
import org.junit.Test;

public class VersionTest {

//    @BeforeClass
//    public static void setUpBeforeClass() throws Exception
//    {}
//
//    @AfterClass
//    public static void tearDownAfterClass() throws Exception
//    {}
//
//    @Before
//    public void setUp() throws Exception
//    {}
//
//    @After
//    public void tearDown() throws Exception
//    {}

    @Test
    public void testVersion() throws IOException, ParseException
    {
        Version version = new Version();
        
        Assert.assertNotNull( "new Version()", version );
        Assert.assertNotNull( "version.getName()", version.getName() );
        Assert.assertNotNull( "version.getVersion()", version.getVersion() );
        Assert.assertNotNull( "version", version.getDate() );
        Assert.assertNotNull( "version.toString()", version.toString() );
    }
}
