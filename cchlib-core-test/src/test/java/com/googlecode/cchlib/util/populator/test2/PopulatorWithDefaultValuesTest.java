package com.googlecode.cchlib.util.populator.test2;

import static com.googlecode.cchlib.lang.annotation.AnnotationLookupOrder.INTERFACES_FIRST;
import static com.googlecode.cchlib.util.populator.FieldsConfigValue.NONE;
import static com.googlecode.cchlib.util.populator.MethodsConfigValue.METHODS;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.entry;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.junit.Test;
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
        final MyInterfaceImpl                      bean   = newBean();
        final PopulatorConfig                      config = newPopulatorConfig();
        final PropertiesPopulator<MyInterfaceImpl> pp     = new PropertiesPopulator<>( MyInterfaceImpl.class, config );
        final Map<String, String>                  map    = new HashMap<>();

        pp.populateMap( bean, map );

        LOGGER.info( "map     = " + map );
        LOGGER.info( "METHODS = " + Arrays.toString( METHODS.getMethods( MyInterfaceImpl.class ) ) );

        assertThat( NONE.getFields( MyInterfaceImpl.class ) ).hasSize( 0 );
        assertThat( METHODS.getMethods( MyInterfaceImpl.class ) ).hasSize( 19 );

        assertThat( map ).hasSize( 5 );

    }

    private static PopulatorConfig newPopulatorConfig()
    {
        return new PopulatorConfig( NONE, METHODS, INTERFACES_FIRST );
    }

    @Test
    public void test_on_implementation_with_config_newMapForBean() throws InstantiationException, IllegalAccessException
    {
        final PopulatorConfig                      config = newPopulatorConfig();
        final PropertiesPopulator<MyInterfaceImpl> pp     = new PropertiesPopulator<>( MyInterfaceImpl.class, config );
        final Map<String, String>                  map    = pp.newMapForBean();

        LOGGER.info( "map     = " + map );
        LOGGER.info( "METHODS = " + Arrays.toString( METHODS.getMethods( MyInterfaceImpl.class ) ) );

        assertThat( NONE.getFields( MyInterfaceImpl.class ) ).hasSize( 0 );
        assertThat( METHODS.getMethods( MyInterfaceImpl.class ) ).hasSize( 19 );

        assertThat( map ).hasSize( 5 );

    }

}
