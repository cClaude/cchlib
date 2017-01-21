package com.googlecode.cchlib.util.properties;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assume.assumeTrue;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import com.googlecode.cchlib.swing.SafeSwingUtilities;

public class PropertiesPopulator_Fields_NotHeadLess_Test
{
    private static final Logger LOGGER = Logger.getLogger( PropertiesPopulator_Fields_NotHeadLess_Test.class );

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
        pp.populateBean( properties, copy );

        LOGGER.info( "expected : [" + bean + "]" );
        LOGGER.info( "actual   : [" + copy + "]" );

        assertThat( copy.getaFloat() ).isEqualTo( bean.getaFloat() );
        assertThat( copy.getaInt() ).isEqualTo( bean.getaInt() );
        assertThat( copy.getaString() ).isEqualTo( bean.getaString() );

        assertThat( copy.getSomeBooleans() ).isEqualTo( bean.getSomeBooleans() );
        assertThat( copy.getaJCheckBox().getText() ).isEqualTo( bean.getaJCheckBox().getText() );
        assertThat( copy.getaJComboBox().getSelectedIndex() ).isEqualTo( bean.getaJComboBox().getSelectedIndex() );
        assertThat( copy.getaJTextField().getText() ).isEqualTo( bean.getaJTextField().getText()  );
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

        assertThat( copy.getaFloat() ).isEqualTo( bean.getaFloat() );
        assertThat( copy.getaInt() ).isEqualTo( bean.getaInt() );
        assertThat( copy.getaString() ).isEqualTo( bean.getaString() );
        assertThat( copy.getSomeBooleans() ).isEqualTo( bean.getSomeBooleans() );
        assertThat( copy.getaJCheckBox().getText() ).isEqualTo( bean.getaJCheckBox().getText() );
        assertThat( copy.getaJComboBox().getSelectedIndex() ).isEqualTo( bean.getaJComboBox().getSelectedIndex() );
        assertThat( copy.getaJTextField().getText() ).isEqualTo( bean.getaJTextField().getText()  );
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

        assertThat( copy.getaFloat() ).isEqualTo( bean.getaFloat() );
        assertThat( copy.getaInt() ).isEqualTo( bean.getaInt() );
        assertThat( copy.getaString() ).isEqualTo( bean.getaString() );

        assertThat( copy.getSomeBooleans() ).isEqualTo( bean.getSomeBooleans() );
        assertThat( copy.getaJCheckBox().getText() ).isEqualTo( bean.getaJCheckBox().getText() );
        assertThat( copy.getaJComboBox().getSelectedIndex() ).isEqualTo( bean.getaJComboBox().getSelectedIndex() );
        assertThat( copy.getaJTextField().getText() ).isEqualTo( bean.getaJTextField().getText()  );
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

        assertThat( copy.myStrangeClass.getPrivateRealContent() )
            .isEqualTo( bean.myStrangeClass.getPrivateRealContent() );
    }

    @Test
    public void test_loadsave() throws IOException
    {
        final File file = File.createTempFile(
                this.getClass().getName(),
                ".properties"
                );
        file.deleteOnExit();
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

        assertThat( copy.getaFloat() ).isEqualTo( bean.getaFloat() );
        assertThat( copy.getaInt() ).isEqualTo( bean.getaInt() );
        assertThat( copy.getaString() ).isEqualTo( bean.getaString() );
        assertThat( copy.getSomeBooleans() ).isEqualTo( bean.getSomeBooleans() );
        assertThat( copy.getaJCheckBox().getText() ).isEqualTo( bean.getaJCheckBox().getText() );
        assertThat( copy.getaJComboBox().getSelectedIndex() ).isEqualTo( bean.getaJComboBox().getSelectedIndex() );
        assertThat( copy.getaJTextField().getText() ).isEqualTo( bean.getaJTextField().getText()  );

        file.delete();
    }
}
