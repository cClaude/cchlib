package com.googlecode.cchlib.apps.duplicatefiles;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.Assert;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ResourcesLoaderTest
{
    private final static Logger logger = Logger.getLogger( ResourcesLoaderTest.class );

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {}

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {}

    @Before
    public void setUp() throws Exception
    {}

    @After
    public void tearDown() throws Exception
    {}

    @Test
    public void testGetResources() 
        throws IllegalAccessException,
               IllegalArgumentException, 
               InvocationTargetException
    {
        final Resources resources = ResourcesLoader.getResources();
        final Method[]  methods   = Resources.class.getDeclaredMethods();
        
        for( Method m : methods ) {
            Object result = m.invoke( resources, (Object[])null );
            
            Assert.assertNotNull( m.getName(), result );
            }
        
        logger.info( "All resources found" );
    }
}
