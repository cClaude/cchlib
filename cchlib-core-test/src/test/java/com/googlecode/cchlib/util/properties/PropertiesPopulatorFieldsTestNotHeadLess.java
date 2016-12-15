package com.googlecode.cchlib.util.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import com.googlecode.cchlib.swing.SafeSwingUtilities;

public class PropertiesPopulatorFieldsTestNotHeadLess
{
    private static final Logger LOGGER = Logger.getLogger( PropertiesPopulatorFieldsTestNotHeadLess.class );

    @Before
    public void setUp() throws Exception
    {
        // Stop if GUI usage is not allowed
        assumeTrue( SafeSwingUtilities.isSwingAvailable() );
    }

    @Test
    public void test_PropertiesPopulator_without_prefix() throws PopulatorException
    {
        // Stop if GUI usage is not allowed
        assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final PropertiesPopulator<BeanAnnotationOnFieldsNotHeadLess> pp
            = new PropertiesPopulator<>( BeanAnnotationOnFieldsNotHeadLess.class );
        final BeanAnnotationOnFieldsNotHeadLess bean
            = new BeanAnnotationOnFieldsNotHeadLess(
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

        final BeanAnnotationOnFieldsNotHeadLess copy = new BeanAnnotationOnFieldsNotHeadLess();
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

        final PropertiesPopulator<BeanAnnotationOnFieldsNotHeadLess> pp
            = new PropertiesPopulator<BeanAnnotationOnFieldsNotHeadLess>( BeanAnnotationOnFieldsNotHeadLess.class );
        final BeanAnnotationOnFieldsNotHeadLess bean
            = new BeanAnnotationOnFieldsNotHeadLess(
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

        final BeanAnnotationOnFieldsNotHeadLess copy = new BeanAnnotationOnFieldsNotHeadLess();
        pp.populateBean( prefix, properties, copy );

        LOGGER.info( "expected : [" + bean + "]" );
        LOGGER.info( "actual   : [" + copy + "]" );

        final int cmp = bean.compareTo( copy );
        assertEquals("Must be equal", 0, cmp);
    }

    @Test
    public void test_PropertiesPopulator2() throws PopulatorException
    {
        final PropertiesPopulator<BeanAnnotationOnFieldsNotHeadLess> pp
            = new PropertiesPopulator<BeanAnnotationOnFieldsNotHeadLess>( BeanAnnotationOnFieldsNotHeadLess.class );
        final BeanAnnotationOnFieldsNotHeadLess bean
            = new BeanAnnotationOnFieldsNotHeadLess(
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

        final BeanAnnotationOnFieldsNotHeadLess copy = new BeanAnnotationOnFieldsNotHeadLess();
        pp.populateBean( prefix, properties, copy );

        LOGGER.info( "expected : [" + bean + "]" );
        LOGGER.info( "actual   : [" + copy + "]" );

        final int cmp = bean.compareTo( copy );
        assertEquals("Must be equal", 0, cmp);
    }

    @Test
    public void test_PopulatorContener() throws PopulatorException
    {
        final PropertiesPopulator<BeanWithPopulatorContener> pp
            = new PropertiesPopulator<BeanWithPopulatorContener>(
                    BeanWithPopulatorContener.class
                    );

        final BeanWithPopulatorContener bean = new BeanWithPopulatorContener( "My Hello String" );

        final Properties properties = new Properties();
        pp.populateProperties(bean, properties);

        final BeanWithPopulatorContener copy = new BeanWithPopulatorContener();
        pp.populateBean( properties, copy );

        Tools.logProperties( LOGGER, properties );

        LOGGER.info( "expected : [" + bean + "]" );
        LOGGER.info( "actual   : [" + copy + "]" );

        assertEquals("Must be equal", bean, copy);
    }

    @Test
    public void test_loadsave() throws IOException
    {
        final File file = File.createTempFile(
                this.getClass().getName(),
                ".properties"
                );
        final BeanAnnotationOnFieldsNotHeadLess bean = new BeanAnnotationOnFieldsNotHeadLess(
            "This is my test String",
            1024,
            2.65F,
            new boolean[]{true,false,true},
            "JTextField text",
            true,
            2 // selected index in boolean array / well just testing :p
            );
        PropertiesPopulator.saveProperties( file, bean, BeanAnnotationOnFieldsNotHeadLess.class );

        final BeanAnnotationOnFieldsNotHeadLess copy = PropertiesPopulator.loadProperties( file, new BeanAnnotationOnFieldsNotHeadLess(), BeanAnnotationOnFieldsNotHeadLess.class );

        LOGGER.info( "File = [" + file + "]" );
        LOGGER.info( "expected : [" + bean + "]" );
        LOGGER.info( "actual   : [" + copy + "]" );

        final int cmp = bean.compareTo( copy );
        assertEquals("Must be equal", 0, cmp);
        file.delete();
    }
}
