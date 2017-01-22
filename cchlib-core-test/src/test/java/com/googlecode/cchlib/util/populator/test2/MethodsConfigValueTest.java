package com.googlecode.cchlib.util.populator.test2;

import static com.googlecode.cchlib.util.populator.MethodsConfigValue.ALL_DECLARED_METHODS;
import static com.googlecode.cchlib.util.populator.MethodsConfigValue.DECLARED_METHODS;
import static com.googlecode.cchlib.util.populator.MethodsConfigValue.METHODS;
import static com.googlecode.cchlib.util.populator.MethodsConfigValue.NONE;
import static org.fest.assertions.api.Assertions.assertThat;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.apache.log4j.Logger;
import org.junit.Test;

public class MethodsConfigValueTest
{
    private static final Logger LOGGER = Logger.getLogger( MethodsConfigValueTest.class );

    @Test
    public void test_on_interface_MethodsConfig_NONE()
    {
        final Method[] actual = NONE.getMethods( MyInterface.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 0 );
    }

    @Test
    public void test_on_interface_MethodsConfig_ALL_DECLARED_METHODS()
    {
        final Method[] actual = ALL_DECLARED_METHODS.getMethods( MyInterface.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 5 );
    }

    @Test
    public void test_on_interface_MethodsConfig_DECLARED_METHODS()
    {
        final Method[] actual = DECLARED_METHODS.getMethods( MyInterface.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 5 );
    }

    @Test
    public void test_on_interface_MethodsConfig_METHODS()
    {
        final Method[] actual = METHODS.getMethods( MyInterface.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 5 );
    }

    @Test
    public void test_on_impl_MethodsConfig_NONE()
    {
        final Method[] actual = NONE.getMethods( MyInterfaceImpl.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 0 );
    }

    @Test
    public void test_on_impl_MethodsConfig_ALL_DECLARED_METHODS()
    {
        final Method[] actual = ALL_DECLARED_METHODS.getMethods( MyInterfaceImpl.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 22 );
    }

    @Test
    public void test_on_impl_MethodsConfig_DECLARED_METHODS()
    {
        final Method[] actual = DECLARED_METHODS.getMethods( MyInterfaceImpl.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 10 );
    }

    @Test
    public void test_on_impl_MethodsConfig_METHODS()
    {
        final Method[] actual = METHODS.getMethods( MyInterfaceImpl.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
    }
}
