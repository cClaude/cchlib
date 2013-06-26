package com.googlecode.cchlib.lang.reflect;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.fest.assertions.Assertions;
/**
 * The class <code>InvokerByNameTest</code> contains tests for the class <code>{@link InvokerByName}</code>.
 * 
 * @version $Revision: 1.0 $
 */
public class InvokerByNameTest 
{
    /**
     * Run the InvokerByName(Class<? extends T>,String) constructor test.
     */
    @Test
    public void testInvokerByName_1()
        throws Exception
    {
        Class<? extends Object> clazz      = Object.class;
        String                  methodName = "getClass";
        Object                  instance   = new Object();
        Object[]                params     = null;

        InvokerByName<Object> test = new InvokerByName<Object>(clazz, methodName);

        assertNotNull( test ); 

//        Matcher<? super Object> matcher = new BaseMatcher<Object>() {
//
//        };
//        MatcherAssert.assertThat( actual, matcher  );
//        Object result = test.invoke( instance, params );
//        assertThat( result ).isEqualTo( "object" );
    }

    /**
     * Run the InvokerByName<Object> forName(String,String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 23/06/13 12:18
     */
    @Test(expected = java.lang.ClassNotFoundException.class)
    public void testForName_1()
        throws Exception
    {
        String className = "";
        String methodName = "";

        InvokerByName<?> result = InvokerByName.forName(className, methodName);

        // add additional test code here
        assertNotNull(result);
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
    public void testForName_2() throws ClassNotFoundException
    {
        InvokerByName<?> result = InvokerByName.forName( InvokerByNameFactory.className, InvokerByNameFactory.methodName);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the String formatMethodNameForException(String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 23/06/13 12:18
     */
    @Test
    public void testFormatMethodNameForException_1()
        throws Exception
    {
        InvokerByName<?> fixture = InvokerByNameFactory.createInvokerByName();
        String           format  = "%s";

        String result = fixture.formatMethodNameForException(format);

        // add additional test code here
        Assertions.assertThat( result ).isEqualTo( "com.googlecode.cchlib.lang.reflect.MyTest.myTest" );
    }

    /**
     * Run the Object invoke(T,Object[]) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 23/06/13 12:18
     */
    @Ignore
    @Test(expected = java.lang.NoSuchMethodException.class)
    public void testInvoke_1()
        throws Exception
    {
        InvokerByName fixture = InvokerByNameFactory.createInvokerByName2();
        Object[] params = new Object[] {};

        Object result = fixture.invoke(null, params);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the Object invoke(T,Object[]) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 23/06/13 12:18
     */
    @Ignore
    @Test(expected = java.lang.NoSuchMethodException.class)
    public void testInvoke_2()
        throws Exception
    {
        InvokerByName fixture = InvokerByNameFactory.createInvokerByName3();
        Object[] params = new Object[] {};

        Object result = fixture.invoke(null, params);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the Object invoke(T,Object[]) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 23/06/13 12:18
     */
    @Ignore
    @Test(expected = java.lang.NoSuchMethodException.class)
    public void testInvoke_3()
        throws Exception
    {
        InvokerByName fixture = InvokerByNameFactory.createInvokerByName();
        Object[] params = new Object[] {};

        Object result = fixture.invoke(null, params);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the Object invoke(T,Object[]) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 23/06/13 12:18
     */
    @Ignore
    @Test(expected = java.lang.NoSuchMethodException.class)
    public void testInvoke_4()
        throws Exception
    {
        InvokerByName fixture = InvokerByNameFactory.createInvokerByName2();
        Object[] params = new Object[] {};

        Object result = fixture.invoke(null, params);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the Object invoke(T,Object[]) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 23/06/13 12:18
     */
    @Ignore
    @Test(expected = java.lang.NoSuchMethodException.class)
    public void testInvoke_5()
        throws Exception
    {
        InvokerByName fixture = InvokerByNameFactory.createInvokerByName3();
        Object[] params = new Object[] {};

        Object result = fixture.invoke(null, params);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the Object invoke(T,Object[]) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 23/06/13 12:18
     */
    @Ignore
    @Test(expected = java.lang.NoSuchMethodException.class)
    public void testInvoke_6()
        throws Exception
    {
        InvokerByName fixture = InvokerByNameFactory.createInvokerByName();
        Object[] params = new Object[] {};

        Object result = fixture.invoke(null, params);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the Object invoke(T,Object[]) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 23/06/13 12:18
     */
    @Ignore
    @Test(expected = java.lang.NoSuchMethodException.class)
    public void testInvoke_7()
        throws Exception
    {
        InvokerByName fixture = InvokerByNameFactory.createInvokerByName2();
        Object[] params = new Object[] {};

        Object result = fixture.invoke(null, params);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the Object invoke(T,Object[]) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 23/06/13 12:18
     */
    @Ignore
    @Test(expected = java.lang.NoSuchMethodException.class)
    public void testInvoke_8()
        throws Exception
    {
        InvokerByName fixture = InvokerByNameFactory.createInvokerByName3();
        Object[] params = new Object[] {null};

        Object result = fixture.invoke(null, params);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the Object invoke(T,Object[]) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 23/06/13 12:18
     */
    @Ignore
    @Test(expected = java.lang.NoSuchMethodException.class)
    public void testInvoke_9()
        throws Exception
    {
        InvokerByName fixture = InvokerByNameFactory.createInvokerByName();
        Object[] params = new Object[] {null};

        Object result = fixture.invoke(null, params);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the Object invoke(T,Object[]) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 23/06/13 12:18
     */
    @Ignore
    @Test(expected = java.lang.NoSuchMethodException.class)
    public void testInvoke_10()
        throws Exception
    {
        InvokerByName fixture = InvokerByNameFactory.createInvokerByName2();
        Object[] params = new Object[] {null};

        Object result = fixture.invoke(null, params);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the Object invoke(T,Object[]) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 23/06/13 12:18
     */
    @Ignore
    @Test(expected = java.lang.NoSuchMethodException.class)
    public void testInvoke_11()
        throws Exception
    {
        InvokerByName fixture = InvokerByNameFactory.createInvokerByName3();
        Object[] params = new Object[] {null};

        Object result = fixture.invoke(null, params);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the Object invoke(T,Object[]) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 23/06/13 12:18
     */
    @Ignore
    @Test(expected = java.lang.NoSuchMethodException.class)
    public void testInvoke_12()
        throws Exception
    {
        InvokerByName fixture = InvokerByNameFactory.createInvokerByName();
        Object[] params = new Object[] {null};

        Object result = fixture.invoke(null, params);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the Object invoke(T,Object[]) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 23/06/13 12:18
     */
    @Ignore
    @Test(expected = java.lang.NoSuchMethodException.class)
    public void testInvoke_13()
        throws Exception
    {
        InvokerByName fixture = InvokerByNameFactory.createInvokerByName2();
        Object[] params = new Object[] {null};

        Object result = fixture.invoke(null, params);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the Object invoke(T,Object[]) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 23/06/13 12:18
     */
    @Ignore
    @Test(expected = java.lang.NoSuchMethodException.class)
    public void testInvoke_14()
        throws Exception
    {
        InvokerByName fixture = InvokerByNameFactory.createInvokerByName3();
        Object[] params = new Object[] {null};

        Object result = fixture.invoke(null, params);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Perform pre-test initialization.
     *
     * @throws Exception
     *         if the initialization fails for some reason
     *
     * @generatedBy CodePro at 23/06/13 12:18
     */
    @Before
    public void setUp()
        throws Exception
    {
        // add additional set up code here
    }

    /**
     * Perform post-test clean-up.
     *
     * @throws Exception
     *         if the clean-up fails for some reason
     *
     * @generatedBy CodePro at 23/06/13 12:18
     */
    @After
    public void tearDown()
        throws Exception
    {
        // Add additional tear down code here
    }

    /**
     * Launch the test.
     *
     * @param args the command line arguments
     *
     * @generatedBy CodePro at 23/06/13 12:18
     */
    public static void main(String[] args)
    {
        new org.junit.runner.JUnitCore().run(InvokerByNameTest.class);
    }
}