package com.googlecode.cchlib.apps.duplicatefiles;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.swing.Icon;
import org.apache.log4j.Logger;
import org.fest.assertions.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.googlecode.cchlib.lang.Objects;
import com.googlecode.cchlib.lang.reflect.Methods;

/**
 * The class <code>MyStaticResourcesTest</code> contains tests for the class <code>{@link MyStaticResources}</code>.
 */
public class MyStaticResourcesTest
{
    private static final Logger LOGGER = Logger.getLogger( MyStaticResourcesTest.class );
    private List<Method> methodList;

    @Test
    public void test_AllStatic() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        for( Method m : methodList ) {
            LOGGER.info( "m: " + m );
            Object o = m.invoke( null, Objects.emptyArray() );

            LOGGER.info( "m: " + m + " => " + o );
            Icon result = (Icon)o;

            // add additional test code here
            Assertions.assertThat( result ).isNotNull();
            Assertions.assertThat( result.getIconWidth() ).isEqualTo( 16 );
            Assertions.assertThat( result.getIconHeight() ).isEqualTo( 16 );
            }
    }

    /**
     * Perform pre-test initialization.
     */
    @Before
    public void setUp() throws Exception
    {
        methodList = Methods.getStaticMethods( MyStaticResources.class );
    }

    /**
     * Perform post-test clean-up
     */
    @After
    public void tearDown() throws Exception
    {
        // Add additional tear down code here
    }

    /**
     * Launch the test.
     */
    public static void main(String[] args)
    {
        new org.junit.runner.JUnitCore().run(MyStaticResourcesTest.class);
    }
}
