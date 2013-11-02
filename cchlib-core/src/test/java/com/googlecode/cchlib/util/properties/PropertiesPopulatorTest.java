// $codepro.audit.disable importOrder
package com.googlecode.cchlib.util.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 *
 */
public class PropertiesPopulatorTest
{
    private static final Logger logger = Logger.getLogger( PropertiesPopulatorTest.class );

    @Test
    public void test_PropertiesPopulator0() throws PopulatorException
    {
        final PropertiesPopulator<PPSimpleBean> pp
            = new PropertiesPopulator<PPSimpleBean>( PPSimpleBean.class );
        final PPSimpleBean bean
            = new PPSimpleBean(
                    "MyTestString",
                    1,
                    1.5F,
                    new boolean[]{true,false,false,false,false},
                    "MyTestString for JTextField",
                    true,
                    3 // selected index in model
                    );
        {
            Properties properties = new Properties();
            pp.populateProperties(bean, properties);

            logProperties( properties );

            PPSimpleBean copy = new PPSimpleBean();
            pp.populateBean(properties, copy);

            logger.info( "expected : [" + bean + "]" );
            logger.info( "actual   : [" + copy + "]" );

            assertEquals("Must be equal", bean.aString, copy.aString);
            //assertEquals("Must be equal", bean, copy);
            assertTrue("Must be equal", bean.compareTo( copy ) == 0);
        }
    }

    @Test
    public void test_PropertiesPopulator1() throws PopulatorException
    {
        final PropertiesPopulator<PPSimpleBean> pp
            = new PropertiesPopulator<PPSimpleBean>( PPSimpleBean.class );
        final PPSimpleBean bean
            = new PPSimpleBean(
                    "MyTestString",
                    1,
                    1.5F,
                    new boolean[]{true,false,false,false,false},
                    "MyTestString for JTextField",
                    true,
                    3 // selected index in model
                    );
        {
            final String prefix = "with_a_prefix.";
            Properties properties = new Properties();
            pp.populateProperties(prefix, bean, properties);
            logProperties( properties );

            PPSimpleBean copy = new PPSimpleBean();
            pp.populateBean( prefix, properties, copy );

            logger.info( "expected : [" + bean + "]" );
            logger.info( "actual   : [" + copy + "]" );

           // assertEquals("Must be equal", bean, copy);
            assertTrue("Must be equal", bean.compareTo( copy ) == 0);
       }
    }

    @Test
    public void test_PropertiesPopulator2() throws PopulatorException
    {
        final PropertiesPopulator<PPSimpleBean> pp
            = new PropertiesPopulator<PPSimpleBean>( PPSimpleBean.class );
        final PPSimpleBean bean
            = new PPSimpleBean(
                    "MyTestString",
                    1,
                    1.5F,
                    new boolean[]{true,false,false,false,false},
                    "MyTestString for JTextField",
                    true,
                    3 // selected index in model
                    );
        {
            final String prefix = "with_a_prefix.";
            Properties properties = new Properties();
            pp.populateProperties( prefix, bean, properties );

            logProperties( properties );

            PPSimpleBean copy = new PPSimpleBean();
            pp.populateBean( prefix, properties, copy );

            logger.info( "expected : [" + bean + "]" );
            logger.info( "actual   : [" + copy + "]" );

           // assertEquals("Must be equal", bean, copy);
            assertTrue("Must be equal", bean.compareTo( copy ) == 0);
       }
    }

    @Test
    public void test_PopulatorContener() throws PopulatorException
    {
        PropertiesPopulator<PPBeanWithPopulatorContener> pp
            = new PropertiesPopulator<PPBeanWithPopulatorContener>(
                    PPBeanWithPopulatorContener.class
                    );

        final PPBeanWithPopulatorContener bean = new PPBeanWithPopulatorContener( "My Hello String" );

        Properties properties = new Properties();
        pp.populateProperties(bean, properties);

        PPBeanWithPopulatorContener copy = new PPBeanWithPopulatorContener();
        pp.populateBean( properties, copy );

        logProperties( properties );

//        logger.info( "bean.strangeClassContener=" + bean.strangeClassContener );
//        logger.info( "bean.strangeClassContener.get()=" + bean.strangeClassContener.get() );
//        logger.info( "bean.strangeClassContener.get().content=" + bean.strangeClassContener.get().realContent );
//
//        logger.info( "copy.strangeClassContener=" + copy.strangeClassContener );
//        logger.info( "copy.strangeClassContener.get()=" + copy.strangeClassContener.get() );
//        logger.info( "copy.strangeClassContener.get().content=" + copy.strangeClassContener.get().realContent );

        logger.info( "expected : [" + bean + "]" );
        logger.info( "actual   : [" + copy + "]" );

        assertEquals("Must be equal", bean, copy);
    }

    @Test
    public void test_loadsave() throws IOException
    {
        final File file = File.createTempFile(
                this.getClass().getName(),
                ".properties"
                );
        final PPSimpleBean bean = new PPSimpleBean(
            "This is my test String",
            1024,
            2.65F,
            new boolean[]{true,false,true},
            "JTextField text",
            true,
            2 // selected index in boolean array / well just testing :p
            );
        PropertiesPopulator.saveProperties( file, bean, PPSimpleBean.class );

        final PPSimpleBean copy = PropertiesPopulator.loadProperties( file, new PPSimpleBean(), PPSimpleBean.class );

        logger.info( "File = [" + file + "]" );
        logger.info( "expected : [" + bean + "]" );
        logger.info( "actual   : [" + copy + "]" );

        assertTrue("Must be equal", bean.compareTo( copy ) == 0);
        file.delete();
    }


    private static final void logProperties( final Properties properties )
    {
        final StringBuilder sb = new StringBuilder();

        sb.append( "Properties" );
        for( String s : properties.stringPropertyNames() ) {
            sb.append("\n\t(").append(s).append(',').append(properties.getProperty( s )).append(')');
            }
        logger.info( sb.toString() );
    }
}
