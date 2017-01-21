package com.googlecode.cchlib.util.populator.test;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import com.googlecode.cchlib.util.populator.PopulatorException;
import com.googlecode.cchlib.util.populator.PropertiesPopulator;
import com.googlecode.cchlib.util.properties.PropertiesTestTools;

public class PropertiesPopulator_Methods_HeadLess_Test
{
    private static final Logger LOGGER = Logger.getLogger( PropertiesPopulator_Methods_HeadLess_Test.class );

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

        PropertiesTestTools.logProperties( LOGGER, properties );

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

        PropertiesTestTools.logProperties( LOGGER, properties );

        final BeanAnnotationOnGettersHeadLess copy = new BeanAnnotationOnGettersHeadLess();
        pp.populateBean(properties, copy);

        LOGGER.info( "copy : " + copy );

        assertThat( copy.getaFloat() ).isEqualTo( bean.getaFloat() );
        assertThat( copy.getaInt() ).isEqualTo( bean.getaInt() );
        assertThat( copy.getaString() ).isEqualTo( bean.getaString() );
        assertThat( copy.getSomeBooleans() ).isEqualTo( bean.getSomeBooleans() );
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

        PropertiesTestTools.logProperties( LOGGER, properties );

        final BeanAnnotationOnGettersHeadLess copy = new BeanAnnotationOnGettersHeadLess();
        pp.populateBean(properties, copy);

        LOGGER.info( "expected : [" + bean + "]" );
        LOGGER.info( "actual   : [" + copy + "]" );

        assertThat( copy.getaFloat() ).isEqualTo( bean.getaFloat() );
        assertThat( copy.getaInt() ).isEqualTo( bean.getaInt() );
        assertThat( copy.getaString() ).isEqualTo( bean.getaString() );
        assertThat( copy.getSomeBooleans() ).isEqualTo( bean.getSomeBooleans() );
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

        PropertiesTestTools.logProperties( LOGGER, properties );

        final BeanAnnotationOnGettersHeadLess copy = new BeanAnnotationOnGettersHeadLess();
        pp.populateBean( prefix, properties, copy );

        LOGGER.info( "expected : [" + bean + "]" );
        LOGGER.info( "actual   : [" + copy + "]" );

        assertThat( copy.getaFloat() ).isEqualTo( bean.getaFloat() );
        assertThat( copy.getaInt() ).isEqualTo( bean.getaInt() );
        assertThat( copy.getaString() ).isEqualTo( bean.getaString() );
        assertThat( copy.getSomeBooleans() ).isEqualTo( bean.getSomeBooleans() );
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

        PropertiesTestTools.logProperties( LOGGER, properties );

        final BeanAnnotationOnGettersHeadLess copy = new BeanAnnotationOnGettersHeadLess();
        pp.populateBean( prefix, properties, copy );

        LOGGER.info( "expected : [" + bean + "]" );
        LOGGER.info( "actual   : [" + copy + "]" );

        assertThat( copy.getaFloat() ).isEqualTo( bean.getaFloat() );
        assertThat( copy.getaInt() ).isEqualTo( bean.getaInt() );
        assertThat( copy.getaString() ).isEqualTo( bean.getaString() );
        assertThat( copy.getSomeBooleans() ).isEqualTo( bean.getSomeBooleans() );
    }

    @Test
    public void test_loadsave() throws IOException
    {
        final File file = File.createTempFile(
                this.getClass().getName(),
                ".properties"
                );
        file.deleteOnExit();

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

        assertThat( copy.getaFloat() ).isEqualTo( bean.getaFloat() );
        assertThat( copy.getaInt() ).isEqualTo( bean.getaInt() );
        assertThat( copy.getaString() ).isEqualTo( bean.getaString() );
        assertThat( copy.getSomeBooleans() ).isEqualTo( bean.getSomeBooleans() );

        file.delete();
    }
}
