package com.googlecode.cchlib.util.properties;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class PropertiesPopulatorGetterTestHeadLess
{
    private static final Logger LOGGER = Logger.getLogger( PropertiesPopulatorGetterTestHeadLess.class );

    @Test
    public void test_PropertiesPopulator_without_prefix_populateProperties() throws PopulatorException
    {
        final PropertiesPopulator<BeanAnnotationOnGettersHeadLess> pp
            = new PropertiesPopulator<>( BeanAnnotationOnGettersHeadLess.class );
        final BeanAnnotationOnGettersHeadLess bean
            = new BeanAnnotationOnGettersHeadLess(
                    "MyTestString",
                    1,
                    1.5F,
                    new boolean[]{true,false,false,false,false}
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

        Assert.assertEquals( 8, properties.size() );
    }

    @Test
    public void test_PropertiesPopulator_without_prefix_populateBean() throws PopulatorException
    {
        final PropertiesPopulator<BeanAnnotationOnGettersHeadLess> pp
            = new PropertiesPopulator<>( BeanAnnotationOnGettersHeadLess.class );
        final BeanAnnotationOnGettersHeadLess bean
        = new BeanAnnotationOnGettersHeadLess(
                "MyTestString",
                1,
                1.5F,
                new boolean[]{true,false,false,false,false}
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

        final BeanAnnotationOnGettersHeadLess copy = new BeanAnnotationOnGettersHeadLess();
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
        final PropertiesPopulator<BeanAnnotationOnGettersHeadLess> pp
            = new PropertiesPopulator<>( BeanAnnotationOnGettersHeadLess.class );
        final BeanAnnotationOnGettersHeadLess bean
            = new BeanAnnotationOnGettersHeadLess(
                    "MyTestString",
                    1,
                    1.5F,
                    new boolean[]{true,false,false,false,false}
                    );

        final Properties properties = new Properties();
        pp.populateProperties(bean, properties);

        Tools.logProperties( LOGGER, properties );

        final BeanAnnotationOnGettersHeadLess copy = new BeanAnnotationOnGettersHeadLess();
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
        final PropertiesPopulator<BeanAnnotationOnGettersHeadLess> pp
            = new PropertiesPopulator<>( BeanAnnotationOnGettersHeadLess.class );
        final BeanAnnotationOnGettersHeadLess bean
            = new BeanAnnotationOnGettersHeadLess(
                    "MyTestString",
                    1,
                    1.5F,
                    new boolean[]{true,false,false,false,false}
                    );

        final String prefix = "with_a_prefix.";
        final Properties properties = new Properties();
        pp.populateProperties(prefix, bean, properties);

        Tools.logProperties( LOGGER, properties );

        final BeanAnnotationOnGettersHeadLess copy = new BeanAnnotationOnGettersHeadLess();
        pp.populateBean( prefix, properties, copy );

        LOGGER.info( "expected : [" + bean + "]" );
        LOGGER.info( "actual   : [" + copy + "]" );

        final int cmp = bean.compareTo( copy );
        assertEquals("Must be equal", 0, cmp);
    }

    @Test
    public void test_PropertiesPopulator2() throws PopulatorException
    {
        final PropertiesPopulator<BeanAnnotationOnGettersHeadLess> pp
            = new PropertiesPopulator<>( BeanAnnotationOnGettersHeadLess.class );
        final BeanAnnotationOnGettersHeadLess bean
            = new BeanAnnotationOnGettersHeadLess(
                    "MyTestString",
                    1,
                    1.5F,
                    new boolean[]{true,false,false,false,false}
                    );

        final String prefix = "with_a_prefix.";
        final Properties properties = new Properties();
        pp.populateProperties( prefix, bean, properties );

        Tools.logProperties( LOGGER, properties );

        final BeanAnnotationOnGettersHeadLess copy = new BeanAnnotationOnGettersHeadLess();
        pp.populateBean( prefix, properties, copy );

        LOGGER.info( "expected : [" + bean + "]" );
        LOGGER.info( "actual   : [" + copy + "]" );

        final int cmp = bean.compareTo( copy );
        assertEquals("Must be equal", 0, cmp);
    }

    @Test
    public void test_loadsave() throws IOException
    {
        final File file = File.createTempFile(
                this.getClass().getName(),
                ".properties"
                );
        final BeanAnnotationOnGettersHeadLess bean = new BeanAnnotationOnGettersHeadLess(
            "This is my test String",
            1024,
            2.65F,
            new boolean[]{true,false,true}
            );
        PropertiesPopulator.saveProperties( file, bean, BeanAnnotationOnGettersHeadLess.class );

        final BeanAnnotationOnGettersHeadLess copy = PropertiesPopulator.loadProperties( file, new BeanAnnotationOnGettersHeadLess(), BeanAnnotationOnGettersHeadLess.class );

        LOGGER.info( "File = [" + file + "]" );
        LOGGER.info( "expected : [" + bean + "]" );
        LOGGER.info( "actual   : [" + copy + "]" );

        final int cmp = bean.compareTo( copy );
        assertEquals("Must be equal", 0, cmp);

        file.delete();
    }
}
