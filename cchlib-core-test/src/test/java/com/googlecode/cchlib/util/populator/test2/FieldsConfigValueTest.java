package com.googlecode.cchlib.util.populator.test2;

import static com.googlecode.cchlib.util.populator.FieldsConfigValue.ALL_DECLARED_FIELDS;
import static com.googlecode.cchlib.util.populator.FieldsConfigValue.DECLARED_FIELDS;
import static com.googlecode.cchlib.util.populator.FieldsConfigValue.FIELDS;
import static com.googlecode.cchlib.util.populator.FieldsConfigValue.NONE;
import static org.fest.assertions.api.Assertions.assertThat;
import java.lang.reflect.Field;
import java.util.Arrays;
import org.apache.log4j.Logger;
import org.junit.Test;

public class FieldsConfigValueTest
{
    private static final Logger LOGGER = Logger.getLogger( FieldsConfigValueTest.class );

    @Test
    public void test_on_interface_FieldsConfig_NONE()
    {
        final Field[] actual = NONE.getFields( MyInterface.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 0 );
    }

    @Test
    public void test_on_interface_FieldsConfig_ALL_DECLARED_FIELDS()
    {
        final Field[] actual = ALL_DECLARED_FIELDS.getFields( MyInterface.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 0 );
    }

    @Test
    public void test_on_interface_FieldsConfig_DECLARED_FIELDS()
    {
        final Field[] actual = DECLARED_FIELDS.getFields( MyInterface.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 0 );
    }

    @Test
    public void test_on_interface_FieldsConfig_FIELDS()
    {
        final Field[] actual = FIELDS.getFields( MyInterface.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 0 );
    }

    @Test
    public void test_on_impl_FieldsConfig_NONE()
    {
        final Field[] actual = NONE.getFields( MyInterfaceImpl.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 0 );
    }

    @Test
    public void test_on_impl_FieldsConfig_ALL_DECLARED_FIELDS()
    {
        final Field[] actual = ALL_DECLARED_FIELDS.getFields( MyInterfaceImpl.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 5 );
    }

    @Test
    public void test_on_impl_FieldsConfig_DECLARED_FIELDS()
    {
        final Field[] actual = DECLARED_FIELDS.getFields( MyInterfaceImpl.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 5 );
    }

    @Test
    public void test_on_impl_FieldsConfig_FIELDS()
    {
        final Field[] actual = FIELDS.getFields( MyInterfaceImpl.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 0 );
    }
}
