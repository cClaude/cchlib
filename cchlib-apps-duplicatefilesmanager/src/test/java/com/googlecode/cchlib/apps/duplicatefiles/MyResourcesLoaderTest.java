package com.googlecode.cchlib.apps.duplicatefiles;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import org.apache.log4j.Logger;
import org.fest.assertions.Assertions;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.googlecode.cchlib.lang.Objects;
import com.googlecode.cchlib.lang.reflect.Methods;

public class MyResourcesLoaderTest
{
    private static final Logger LOGGER = Logger.getLogger( MyResourcesLoaderTest.class );

    private List<Method> methodList;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {}

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {}

    @Before
    public void setUp() throws Exception
    {
        methodList = Methods.getStaticMethods( MyResourcesLoader.class );
    }

    @After
    public void tearDown() throws Exception
    {}

    @Test
    public void testGetResources()
        throws IllegalAccessException,
               IllegalArgumentException,
               InvocationTargetException
    {
        final Resources resources = MyResourcesLoader.getResources();
        final Method[]  methods   = Resources.class.getDeclaredMethods();

        for( final Method m : methods ) {
            final Object result = m.invoke( resources, (Object[])null );

            LOGGER.info( "getResources() - m: " + m + " => " + result );

            // add additional test code here
            Assertions.assertThat( result ).isNotNull();
            }

        LOGGER.info( "All resources found" );
    }

    @Test
    public void test_AllStatic() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        for( final Method m : methodList ) {
            final Object result = m.invoke( null, Objects.emptyArray() );

            LOGGER.info( "m: " + m + " => " + result );

            // add additional test code here
            Assertions.assertThat( result ).isNotNull();
            }
    }

}
