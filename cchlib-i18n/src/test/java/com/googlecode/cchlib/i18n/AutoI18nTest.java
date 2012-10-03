package com.googlecode.cchlib.i18n;

import org.junit.Assert;
import org.junit.Test;

public class AutoI18nTest 
{
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
    public void testCONSTANTS()
    {
        final String actual   = AutoI18n.DISABLE_PROPERTIES;
        final String expected = AutoI18n.class.getName() + ".disabled";

        Assert.assertEquals( "Bad AutoI18n.DISABLE_PROPERTIES", expected, actual );
    }

}
