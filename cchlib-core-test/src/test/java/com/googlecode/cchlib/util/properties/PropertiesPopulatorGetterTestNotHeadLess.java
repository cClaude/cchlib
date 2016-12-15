package com.googlecode.cchlib.util.properties;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class PropertiesPopulatorGetterTestNotHeadLess
{
    private static final Logger LOGGER = Logger.getLogger( PropertiesPopulatorGetterTestNotHeadLess.class );

    @Test
    public void test_PropertiesPopulator_without_prefix_populateProperties() throws PopulatorException
    {
        // Stop if GUI usage is not allowed
        //assumeFalse( SafeSwingUtilities.isHeadless() );

        final PropertiesPopulator<BeanAnnotationOnGettersNotHeadLess> pp
            = new PropertiesPopulator<>( BeanAnnotationOnGettersNotHeadLess.class );
        final BeanAnnotationOnGettersNotHeadLess bean
            = new BeanAnnotationOnGettersNotHeadLess(
                    "MyTestString",
                    1,
                    1.5F,
                    new boolean[]{true,false,false,false,false},
                    "MyTestString for JTextField",
                    true,
                    3 // selected index in model
                    );

        final Properties properties = new Properties();
        pp.populateProperties(bean, properties);

        Tools.logProperties( LOGGER, properties );

        Assert.assertEquals( "MyTestString", properties.getProperty( "aString" ) );
        Assert.assertEquals( "1", properties.getProperty( "aInt" ) );
        Assert.assertEquals( "1.5", properties.getProperty( "aFloat" ) );
        Assert.assertEquals( "true", properties.getProperty( "SomeBooleans.0" ) );
        Assert.assertEquals( "false", properties.getProperty( "SomeBooleans.1" ) );
        Assert.assertEquals( "false", properties.getProperty( "SomeBooleans.2" ) );
        Assert.assertEquals( "false", properties.getProperty( "SomeBooleans.3" ) );
        Assert.assertEquals( "false", properties.getProperty( "SomeBooleans.4" ) );

        Assert.assertEquals( "MyTestString for JTextField", properties.getProperty( "aJTextField" ) );
        Assert.assertEquals( "true", properties.getProperty( "aJCheckBox" ) );
        Assert.assertEquals( "3", properties.getProperty( "aJComboBox" ) );

        Assert.assertEquals( 11, properties.size() );
    }

    @Test
    public void test_PropertiesPopulator_without_prefix_populateBean() throws PopulatorException
    {
        // Stop if GUI usage is not allowed
        //assumeFalse( SafeSwingUtilities.isHeadless() );

        final PropertiesPopulator<BeanAnnotationOnGettersNotHeadLess> pp
            = new PropertiesPopulator<>( BeanAnnotationOnGettersNotHeadLess.class );
        final BeanAnnotationOnGettersNotHeadLess bean
        = new BeanAnnotationOnGettersNotHeadLess(
                "MyTestString",
                1,
                1.5F,
                new boolean[]{true,false,false,false,false},
                "MyTestString for JTextField",
                true,
                3 // selected index in model
                );

        final Properties properties = new Properties();

        properties.setProperty( "aString", "MyTestString" );
        properties.setProperty( "aInt", "1" );
        properties.setProperty( "aFloat", "1.5" );
        properties.setProperty( "SomeBooleans.0", "true" );
        properties.setProperty( "SomeBooleans.1", "false" );
        properties.setProperty( "SomeBooleans.2", "false" );
        properties.setProperty( "SomeBooleans.3", "false" );
        properties.setProperty( "SomeBooleans.4", "false" );

        properties.setProperty( "aJTextField", "MyTestString for JTextField" );
        properties.setProperty( "aJCheckBox", "true" );
        properties.setProperty( "aJComboBox", "3" );

        Tools.logProperties( LOGGER, properties );

        final BeanAnnotationOnGettersNotHeadLess copy = new BeanAnnotationOnGettersNotHeadLess();
        pp.populateBean(properties, copy);

        LOGGER.info( "copy : " + copy );

        assertEquals("Must be equal", bean.aString, copy.aString);
        //assertTrue("Must be equal", bean.compareTo( copy ) == 0);
        final int cmp = bean.compareTo( copy );
        assertEquals("Must be equal", 0, cmp);
    }

    @Test
    public void test_PropertiesPopulator_without_prefix_full_test() throws PopulatorException
    {
        // Stop if GUI usage is not allowed
        //assumeFalse( SafeSwingUtilities.isHeadless() );

        final PropertiesPopulator<BeanAnnotationOnGettersNotHeadLess> pp
            = new PropertiesPopulator<>( BeanAnnotationOnGettersNotHeadLess.class );
        final BeanAnnotationOnGettersNotHeadLess bean
            = new BeanAnnotationOnGettersNotHeadLess(
                    "MyTestString",
                    1,
                    1.5F,
                    new boolean[]{true,false,false,false,false},
                    "MyTestString for JTextField",
                    true,
                    3 // selected index in model
                    );

        final Properties properties = new Properties();
        pp.populateProperties(bean, properties);

        Tools.logProperties( LOGGER, properties );

        final BeanAnnotationOnGettersNotHeadLess copy = new BeanAnnotationOnGettersNotHeadLess();
        pp.populateBean(properties, copy);

        LOGGER.info( "expected : [" + bean + "]" );
        LOGGER.info( "actual   : [" + copy + "]" );

        assertEquals("Must be equal", bean.aString, copy.aString);
        final int cmp = bean.compareTo( copy );
        assertEquals("Must be equal", 0, cmp);

    }

    @Test
    public void test_PropertiesPopulator_with_prefix() throws PopulatorException
    {
        // Stop if GUI usage is not allowed
        //assumeFalse( SafeSwingUtilities.isHeadless() );

        final PropertiesPopulator<BeanAnnotationOnGettersNotHeadLess> pp
            = new PropertiesPopulator<>( BeanAnnotationOnGettersNotHeadLess.class );
        final BeanAnnotationOnGettersNotHeadLess bean
            = new BeanAnnotationOnGettersNotHeadLess(
                    "MyTestString",
                    1,
                    1.5F,
                    new boolean[]{true,false,false,false,false},
                    "MyTestString for JTextField",
                    true,
                    3 // selected index in model
                    );

        final String prefix = "with_a_prefix.";
        final Properties properties = new Properties();
        pp.populateProperties(prefix, bean, properties);

        Tools.logProperties( LOGGER, properties );

        final BeanAnnotationOnGettersNotHeadLess copy = new BeanAnnotationOnGettersNotHeadLess();
        pp.populateBean( prefix, properties, copy );

        LOGGER.info( "expected : [" + bean + "]" );
        LOGGER.info( "actual   : [" + copy + "]" );

        final int cmp = bean.compareTo( copy );
        assertEquals("Must be equal", 0, cmp);
    }

    @Test
    public void test_PropertiesPopulator2() throws PopulatorException
    {
        // Stop if GUI usage is not allowed
        //assumeFalse( SafeSwingUtilities.isHeadless() );

        final PropertiesPopulator<BeanAnnotationOnGettersNotHeadLess> pp
            = new PropertiesPopulator<>( BeanAnnotationOnGettersNotHeadLess.class );
        final BeanAnnotationOnGettersNotHeadLess bean
            = new BeanAnnotationOnGettersNotHeadLess(
                    "MyTestString",
                    1,
                    1.5F,
                    new boolean[]{true,false,false,false,false},
                    "MyTestString for JTextField",
                    true,
                    3 // selected index in model
                    );

        final String prefix = "with_a_prefix.";
        final Properties properties = new Properties();
        pp.populateProperties( prefix, bean, properties );

        Tools.logProperties( LOGGER, properties );

        final BeanAnnotationOnGettersNotHeadLess copy = new BeanAnnotationOnGettersNotHeadLess();
        pp.populateBean( prefix, properties, copy );

        LOGGER.info( "expected : [" + bean + "]" );
        LOGGER.info( "actual   : [" + copy + "]" );

        final int cmp = bean.compareTo( copy );
        assertEquals("Must be equal", 0, cmp);
    }

    @Test
    public void test_loadsave() throws IOException
    {
        // Stop if GUI usage is not allowed
        //assumeFalse( SafeSwingUtilities.isHeadless() );

        final File file = File.createTempFile(
                this.getClass().getName(),
                ".properties"
                );
        final BeanAnnotationOnGettersNotHeadLess bean = new BeanAnnotationOnGettersNotHeadLess(
            "This is my test String",
            1024,
            2.65F,
            new boolean[]{true,false,true},
            "MyTestString for JTextField",
            true,
            3 // selected index in model
            );
        PropertiesPopulator.saveProperties( file, bean, BeanAnnotationOnGettersNotHeadLess.class );

        final BeanAnnotationOnGettersNotHeadLess copy = PropertiesPopulator.loadProperties( file, new BeanAnnotationOnGettersNotHeadLess(), BeanAnnotationOnGettersNotHeadLess.class );

        LOGGER.info( "File = [" + file + "]" );
        LOGGER.info( "expected : [" + bean + "]" );
        LOGGER.info( "actual   : [" + copy + "]" );

        final int cmp = bean.compareTo( copy );
        assertEquals("Must be equal", 0, cmp);

        file.delete();
    }
}
