// $codepro.audit.disable avoidAutoBoxing, unnecessaryExceptions, importOrder
package com.googlecode.cchlib.lang.reflect;

import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Logger;
import org.fest.assertions.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The class <code>InvokerByNameTest</code> contains tests for the class <code>{@link InvokerByName}</code>.
 *
 * @version $Revision: 1.0 $
 */
public class InvokerByNameTest
{
    private static final Logger logger = Logger.getLogger( InvokerByNameTest.class );

    /**
     * Run the InvokerByName(Class<? extends T>,String) constructor test.
     */
    @Test
    public void testInvokerByName_getClass()
        throws Exception
    {
        Class<? extends Object> clazz      = Object.class;
        String                  methodName = "getClass";
        Object                  instance   = new Object();
        Object[]                params     = null;

        InvokerByName<Object> test = new InvokerByName<Object>(clazz, methodName);

        assertNotNull( test );

        Object result = test.invoke( instance, params );
        Assertions.assertThat( result ).isEqualTo( Object.class );
    }


    /**
     * Run the InvokerByName<Object> forName(String,String) method test.
     */
    @Test(expected = java.lang.ClassNotFoundException.class)
    public void testForName_ClassNotFoundException()
        throws Exception
    {
        InvokerByName<?> result = InvokerByName.forName( "does.not.Exist", "does.not.Exist" );

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the InvokerByName<Object> forName(String,String) method test.
     * @throws ClassNotFoundException
     */
    @Test
    public void testForName_OK_init() throws ClassNotFoundException
    {
        InvokerByName<?> result = InvokerByName.forName( InvokerByNameFactory.className, InvokerByNameFactory.methodName);

        logger.info( "resullt = " + result );

        // add additional test code here
        assertNotNull(result);

        logger.info( "result.getClass() = " + result.getClass() );
    }

    /**
     * Run the String formatMethodNameForException(String) method test.
     */
    @Test
    public void testFormatMethodNameForException_1()
        throws Exception
    {
        InvokerByName<?> fixture = InvokerByNameFactory.createInvokerByName();
        String           format  = "%s";

        String result = fixture.formatMethodNameForException(format);

        // add additional test code here
        Assertions.assertThat( result ).isEqualTo( "com.googlecode.cchlib.lang.reflect.MyTestByName.myTest" );
    }

    /**
     * Run the Object invoke(T,Object[]) method test.
     */
    @Test(expected = java.lang.NoSuchMethodException.class)
    public void testInvoke_NoSuchMethodException1()
        throws Exception
    {
        InvokerByName<MyTestByName> fixture = InvokerByNameFactory.createInvokerByName_NoSuchMethodException();

        MyTestByName instance = new MyTestByName();
        Object result = fixture.invoke(instance, null);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the Object invoke(T,Object[]) method test.
     */
    @Test(expected = java.lang.NoSuchMethodException.class)
    public void testInvoke_NoSuchMethodException2()
        throws Exception
    {
        InvokerByName<MyTestByName> fixture = InvokerByNameFactory.createInvokerByName_NoSuchMethodException();

        MyTestByName   instance = new MyTestByName();
        Object[] params   = new Object[] {};

        Object result = fixture.invoke(instance, params);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the Object invoke(T,Object[]) method test.
     */
    @Test(expected = java.lang.NoSuchMethodException.class)
    public void testInvoke_NoSuchMethodException3()
        throws Exception
    {
        InvokerByName<MyTestByName> fixture = InvokerByNameFactory.createInvokerByName_NoSuchMethodException();

        MyTestByName   instance = new MyTestByName();
        Object[] params   = new Object[] { "A", 1 };

        Object result = fixture.invoke(instance, params);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the Object invoke(T,Object[]) method test.
     */
    @Test(expected = java.lang.NoSuchMethodException.class)
    public void testInvoke_static_NoSuchMethodException1()
        throws Exception
    {
        InvokerByName<MyTestByName> fixture = InvokerByNameFactory.createInvokerByName_NoSuchMethodException();
        Object[] params = new Object[] {};

        @SuppressWarnings("unused")
        Object result = fixture.invoke(null, params); // No method like these
    }

    /**
     * Run the Object invoke(T,Object[]) method test.
     */
    @Test(expected = java.lang.NullPointerException.class)
    public void testInvoke_static_NullPointerException1()
        throws Exception
    {
        InvokerByName<?> fixture = InvokerByNameFactory.createInvokerByName();
        Object[] params = new Object[] {}; // No params

        @SuppressWarnings("unused")
        Object result = fixture.invoke(null, params); // No 'static' method like these
        // -> class.invoke returns NullPointerException
    }

    /**
     * Run the Object invoke(T,Object[]) method test.
     */
    @Test(expected = java.lang.NullPointerException.class)
    public void testInvoke_static_NullPointerException2()
        throws Exception
    {
        InvokerByName<?> fixture = InvokerByNameFactory.createInvokerByName();
        Object[] params = new Object[] { "A" };

        @SuppressWarnings("unused")
        Object result = fixture.invoke(null, params); // No 'static' method like these
        // -> class.invoke returns NullPointerException
    }

    /**
     * Run the Object invoke(T,Object[]) method test.
     */
    @Test
    public void testInvoke_NoParam1()
        throws Exception
    {
        InvokerByName<MyTestByName> fixture = InvokerByNameFactory.createInvokerByClass();

        MyTestByName   instance = new MyTestByName();

        Object result = fixture.invoke(instance, null);

        // add additional test code here
        assertNotNull(result);
        Assertions.assertThat( result ).isNotNull();
        Assertions.assertThat( result.getClass() ).isEqualTo( Integer.class );
        Assertions.assertThat( result ).isEqualTo( 0 );
   }

    /**
     * Run the Object invoke(T,Object[]) method test.
     */
    @Test
    public void testInvoke_NoParam2()
        throws Exception
    {
        InvokerByName<MyTestByName> fixture = InvokerByNameFactory.createInvokerByClass();

        MyTestByName   instance = new MyTestByName();
        Object[] params   = new Object[] {};

        Object result = fixture.invoke(instance, params);

        // add additional test code here
        assertNotNull(result);
        Assertions.assertThat( result ).isNotNull();
        Assertions.assertThat( result.getClass() ).isEqualTo( Integer.class );
        Assertions.assertThat( result ).isEqualTo( 0 );
   }

    /**
     * Run the Object invoke(T,Object[]) method test.
     */
    @Test
    public void testInvoke_OK1()
        throws Exception
    {
        InvokerByName<MyTestByName> fixture = InvokerByNameFactory.createInvokerByClass();

        final int value = 128;

        MyTestByName   instance = new MyTestByName();
        Object[] params   = new Object[] { value };

        Object result = fixture.invoke(instance, params);

        // add additional test code here
        Assertions.assertThat( result ).isNotNull();
        Assertions.assertThat( result.getClass() ).isEqualTo( String.class );
        Assertions.assertThat( result ).isEqualTo( Integer.toString( value ) );
    }

    /**
     * Run the Object invoke(T,Object[]) method test.
     */
    @Test
    public void testInvoke_OK2()
        throws Exception
    {
        InvokerByName<MyTestByName> fixture = InvokerByNameFactory.createInvokerByClass();

        final int value1 = -1;
        final int value2 = 57;

        MyTestByName   instance = new MyTestByName();
        Object[] params   = new Object[] { value1, value2 };

        Object result = fixture.invoke(instance, params);

        // add additional test code here
        Assertions.assertThat( result ).isNotNull();
        Assertions.assertThat( result.getClass() ).isEqualTo( String.class );
        Assertions.assertThat( result ).isEqualTo( Integer.toString( value1 + value2 ) );
    }

    /**
     * Run the Object invoke(T,Object[]) method test.
     */
    @Test
    public void testInvoke_OK3()
        throws Exception
    {
        InvokerByName<MyTestByName> fixture = InvokerByNameFactory.createInvokerByClass();

        final int value1 = 876;
        final int value2 = 5;
        final int value3 = 258;
        final int[] values = { value1, value2, value3 };

        MyTestByName   instance = new MyTestByName();
        Object[] params   = new Object[] { values };

        Object result = fixture.invoke(instance, params);

        // add additional test code here
        Assertions.assertThat( result ).isNotNull();
        Assertions.assertThat( result.getClass() ).isEqualTo( String.class );
        Assertions.assertThat( result ).isEqualTo( instance.myTest( value1, value2, value3 ) );
    }

    /**
     * Perform pre-test initialization.
     */
    @Before
    public void setUp()
        throws Exception
    {
        // add additional set up code here
    }

    /**
     * Perform post-test clean-up.
     */
    @After
    public void tearDown()
        throws Exception
    {
        // Add additional tear down code here
    }

    /**
     * Launch the test.
     */
    public static void main(String[] args)
    {
        new org.junit.runner.JUnitCore().run(InvokerByNameTest.class);
    }
}
