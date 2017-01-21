package com.googlecode.cchlib.util.populator.test2;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.entry;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.googlecode.cchlib.util.populator.FieldsConfig;
import com.googlecode.cchlib.util.populator.MethodsConfig;
import com.googlecode.cchlib.util.populator.PopulatorConfig;
import com.googlecode.cchlib.util.populator.PropertiesPopulator;

public class PopulatorWithDefaultValuesTest
{
    private static final Logger LOGGER = Logger.getLogger( PopulatorWithDefaultValuesTest.class );

    private static final String USERNAME    = "username";
    private static final String SCHEMA_NAME = "schemaName";
    private static final String PASSWORD    = "password";
    private static final String HOSTNAME    = "hostname";
    private static final int    PORT_INT    = 1234;
    private static final String PORT        = Integer.toString( PORT_INT );

    private static final MyInterfaceImpl newBean()
    {
        final MyInterfaceImpl bean = new MyInterfaceImpl();

        bean.setHostname( HOSTNAME );
        bean.setPassword( PASSWORD );
        bean.setPort( PORT_INT );
        bean.setSchemaName( SCHEMA_NAME );
        bean.setUsername( USERNAME );

        return bean;
    }

    @Test
    public void test_on_interface_FieldsConfig_NONE()
    {
        final Field[] actual = FieldsConfig.NONE.getFields( MyInterface.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 0 );
    }

    @Test
    public void test_on_interface_FieldsConfig_ALL_DECLARED_FIELDS()
    {
        final Field[] actual = FieldsConfig.ALL_DECLARED_FIELDS.getFields( MyInterface.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 0 );
    }

    @Test
    public void test_on_interface_FieldsConfig_DECLARED_FIELDS()
    {
        final Field[] actual = FieldsConfig.DECLARED_FIELDS.getFields( MyInterface.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 0 );
    }

    @Test
    public void test_on_interface_FieldsConfig_FIELDS()
    {
        final Field[] actual = FieldsConfig.FIELDS.getFields( MyInterface.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 0 );
    }

    @Test
    public void test_on_impl_FieldsConfig_NONE()
    {
        final Field[] actual = FieldsConfig.NONE.getFields( MyInterfaceImpl.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 0 );
    }

    @Test
    public void test_on_impl_FieldsConfig_ALL_DECLARED_FIELDS()
    {
        final Field[] actual = FieldsConfig.ALL_DECLARED_FIELDS.getFields( MyInterfaceImpl.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 5 );
    }

    @Test
    public void test_on_impl_FieldsConfig_DECLARED_FIELDS()
    {
        final Field[] actual = FieldsConfig.DECLARED_FIELDS.getFields( MyInterfaceImpl.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 5 );
    }

    @Test
    public void test_on_impl_FieldsConfig_FIELDS()
    {
        final Field[] actual = FieldsConfig.FIELDS.getFields( MyInterfaceImpl.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 0 );
    }

    @Test
    public void test_on_interface_MethodsConfig_NONE()
    {
        final Method[] actual = MethodsConfig.NONE.getMethods( MyInterface.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 0 );
    }

    @Test
    public void test_on_interface_MethodsConfig_ALL_DECLARED_METHODS()
    {
        final Method[] actual = MethodsConfig.ALL_DECLARED_METHODS.getMethods( MyInterface.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 5 );
    }

    @Test
    public void test_on_interface_MethodsConfig_DECLARED_METHODS()
    {
        final Method[] actual = MethodsConfig.DECLARED_METHODS.getMethods( MyInterface.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 5 );
    }

    @Test
    public void test_on_interface_MethodsConfig_METHODS()
    {
        final Method[] actual = MethodsConfig.METHODS.getMethods( MyInterface.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 5 );
    }

    @Test
    public void test_on_impl_MethodsConfig_NONE()
    {
        final Method[] actual = MethodsConfig.NONE.getMethods( MyInterfaceImpl.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 0 );
    }

    @Test
    public void test_on_impl_MethodsConfig_ALL_DECLARED_METHODS()
    {
        final Method[] actual = MethodsConfig.ALL_DECLARED_METHODS.getMethods( MyInterfaceImpl.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 22 );
    }

    @Test
    public void test_on_impl_MethodsConfig_DECLARED_METHODS()
    {
        final Method[] actual = MethodsConfig.DECLARED_METHODS.getMethods( MyInterfaceImpl.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
        assertThat( actual ).hasSize( 10 );
    }

    @Test
    public void test_on_impl_MethodsConfig_METHODS()
    {
        final Method[] actual = MethodsConfig.METHODS.getMethods( MyInterfaceImpl.class );

        LOGGER.debug( "size = " + actual.length + " - actual = " + Arrays.toString( actual ) );
    }

    @Test
    public void test_on_interface() throws InstantiationException, IllegalAccessException
    {
        final PropertiesPopulator<MyInterface> pp  = new PropertiesPopulator<>( MyInterface.class );
        final Map<String, String>              map = pp.newMapForBean( MyInterfaceImpl.class );

        LOGGER.info( "map = " + map );

        // setter are not define on interface, so can not be handle
        assertThat( map ).hasSize( 0 );
    }

    @Test
    public void test_on_implementation_populateMap()
    {
        final MyInterfaceImpl                      bean = newBean();
        final PropertiesPopulator<MyInterfaceImpl> pp   = new PropertiesPopulator<>( MyInterfaceImpl.class );
        final Map<String, String>                  map  = new HashMap<>();

        pp.populateMap( bean, map );

        LOGGER.info( "map = " + map );

        assertThat( map ).hasSize( 5 );
        assertThat( map ).contains(
                entry( "Username"   , USERNAME    ),
                entry( "Port"       , PORT        ),
                entry( "Hostname"   , HOSTNAME    ),
                entry( "SchemaName" , SCHEMA_NAME ),
                entry( "Password"   , PASSWORD    )
                );
    }

    @Test
    public void test_on_implementation_newMapForBean() throws InstantiationException, IllegalAccessException
    {
        final PropertiesPopulator<MyInterfaceImpl> pp  = new PropertiesPopulator<>( MyInterfaceImpl.class );
        final Map<String, String>                  map = pp.newMapForBean();

        LOGGER.info( "map = " + map );

        assertThat( map ).hasSize( 5 );
        assertThat( map ).contains(
                entry( "Username"   , Const.DEFAULT_USERNAME    ),
                entry( "Port"       , Const.DEFAULT_PORT        ),
                entry( "Hostname"   , Const.DEFAULT_HOSTNAME    ),
                entry( "SchemaName" , Const.DEFAULT_SCHEMA_NAME ),
                entry( "Password"   , Const.DEFAULT_PASSWORD    )
                );
    }

    @Test
    public void test_on_implementation_with_config_populateMap() throws InstantiationException, IllegalAccessException
    {
        final MyInterfaceImpl                      bean = newBean();
        final PopulatorConfig                      config = new PopulatorConfig( FieldsConfig.NONE, MethodsConfig.METHODS );
        final PropertiesPopulator<MyInterfaceImpl> pp   = new PropertiesPopulator<>( MyInterfaceImpl.class, config );
        final Map<String, String>                  map  = new HashMap<>();

        pp.populateMap( bean, map );

        LOGGER.info( "map     = " + map );
        LOGGER.info( "METHODS = " + Arrays.toString( MethodsConfig.METHODS.getMethods( MyInterfaceImpl.class ) ) );

        assertThat( FieldsConfig.NONE.getFields( MyInterfaceImpl.class ) ).hasSize( 0 );
        //assertThat( MethodsConfig.METHODS.getMethods( MyInterfaceImpl.class ) ).hasSize( 10 );

        assertThat( map ).hasSize( 5 );

    }

    @Test
    public void test_on_implementation_with_config_newMapForBean() throws InstantiationException, IllegalAccessException
    {
        final PopulatorConfig                      config = new PopulatorConfig( FieldsConfig.NONE, MethodsConfig.METHODS );
        final PropertiesPopulator<MyInterfaceImpl> pp     = new PropertiesPopulator<>( MyInterfaceImpl.class, config );
        final Map<String, String>                  map = pp.newMapForBean();

        LOGGER.info( "map     = " + map );
        LOGGER.info( "METHODS = " + Arrays.toString( MethodsConfig.METHODS.getMethods( MyInterfaceImpl.class ) ) );

        assertThat( FieldsConfig.NONE.getFields( MyInterfaceImpl.class ) ).hasSize( 0 );
        //assertThat( MethodsConfig.METHODS.getMethods( MyInterfaceImpl.class ) ).hasSize( 10 );

        assertThat( map ).hasSize( 5 );

    }

}
