package com.googlecode.cchlib.util.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import javax.swing.JTextField;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 *
 */
public class PropertiesPopulatorTest
{
    private final static Logger logger = Logger.getLogger( PropertiesPopulatorTest.class );

    @Test
    public void test_PropertiesPopulator() throws PopulatorException
    {
        final PropertiesPopulator<SimpleBean> pp
            = new PropertiesPopulator<SimpleBean>( SimpleBean.class );
        final SimpleBean bean
            = new SimpleBean(
                    "MyTestString",
                    1,
                    1.5F,
                    new boolean[]{true,false},
                    "MyTestString for JTextField"
                    );
        {
            Properties properties = new Properties();
            pp.populateProperties(bean, properties);

            logProperties( properties );

            SimpleBean copy = new SimpleBean();
            pp.populateBean(properties, copy);

            logger.info( "expected : [" + bean + "]" );
            logger.info( "actual   : [" + copy + "]" );

            assertEquals("Must be equal", bean.aString, copy.aString);
            assertEquals("Must be equal", bean, copy);
        }

        {
            final String prefix = "with_a_prefix.";
            Properties properties = new Properties();
            pp.populateProperties( bean, properties, prefix );

            logProperties( properties );

            SimpleBean copy = new SimpleBean();
            pp.populateBean( properties, prefix, copy );

            logger.info( "expected : [" + bean + "]" );
            logger.info( "actual   : [" + copy + "]" );

            assertEquals("Must be equal", bean, copy);
        }
    }

    @Test
    public void test_PopulatorContener() throws PopulatorException
    {
        PropertiesPopulator<BeanTstWithPopulatorContener> pp
            = new PropertiesPopulator<BeanTstWithPopulatorContener>(
                    BeanTstWithPopulatorContener.class
                    );

        final BeanTstWithPopulatorContener bean = new BeanTstWithPopulatorContener( "My Hello String" );

        Properties properties = new Properties();
        pp.populateProperties(bean, properties);

        BeanTstWithPopulatorContener copy = new BeanTstWithPopulatorContener();
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
        final SimpleBean bean = new SimpleBean(
            "This is my test String",
            1024,
            2.65F,
            new boolean[]{true,false,true},
            "JTextField text"
            );
        PropertiesPopulator.saveProperties( file, bean, SimpleBean.class );

        final SimpleBean copy = PropertiesPopulator.loadProperties( file, new SimpleBean(), SimpleBean.class );

        logger.info( "File = [" + file + "]" );
        logger.info( "expected : [" + bean + "]" );
        logger.info( "actual   : [" + copy + "]" );

        assertTrue("Must be equal", bean.compareTo( copy ) == 0);
        file.delete();
    }


    private final static void logProperties( final Properties properties )
    {
        final StringBuilder sb = new StringBuilder();

        sb.append( "Properties" );
        for( String s : properties.stringPropertyNames() ) {
            sb.append( "\n\t(" + s + "," + properties.getProperty( s ) + ")" );
            }
        logger.info( sb.toString() );
    }
}

class SimpleBean implements Comparable<SimpleBean>
{
    @Populator protected String aString;
    @Populator private int aInt;
    @Populator private float aFloat;
    @Populator public boolean[] someBooleans;

    @Persistent private JTextField aJTextField;

    public SimpleBean()
    {
        this.aJTextField = new JTextField();
    }

    @Override
    public String toString() {
        return "SimpleBean [aString=" + aString + ", aInt=" + aInt
                + ", aFloat=" + aFloat + ", someBooleans="
                + Arrays.toString(someBooleans) + ", aJTextField(text)="
                + aJTextField.getText() + "]";
    }

    public SimpleBean(
        final String    aString,
        final int       aInt,
        final float     aFloat,
        final boolean[] booleans,
        final String    jTextFieldString
        )
    {
        this.aString        = aString;
        this.aInt           = aInt;
        this.aFloat         = aFloat;
        this.someBooleans   = booleans;
        this.someBooleans   = booleans;
        this.aJTextField    = new JTextField( jTextFieldString );
    }
    public final String getaString()
    {
        return aString;
    }
    public final void setaString(String aString)
    {
        this.aString = aString;
    }
    public final int getaInt()
    {
        return aInt;
    }
    public final void setaInt(int aInt)
    {
        this.aInt = aInt;
    }
    public final float getaFloat()
    {
        return aFloat;
    }
    public final void setaFloat(float aFloat)
    {
        this.aFloat = aFloat;
    }
    @Override
    public int compareTo( final SimpleBean o )
    {
        int res = this.aString.compareTo( o.aString );
        if( res != 0 ) {
            return res;
            }

        res = this.aInt - o.aInt;
        if( res != 0 ) {
            return res;
            }

        res = Float.compare( this.aFloat, o.aFloat );
        if( res != 0 ) {
            return res;
            }

        res = this.someBooleans.length - o.someBooleans.length;
        if( res != 0 ) {
            return res;
            }

        for( int i = 0; i<this.someBooleans.length; i++ ) {
            res = Boolean.valueOf( this.someBooleans[ i ] ).compareTo( o.someBooleans[ i ] );
            if( res != 0 ) {
                return res;
                }
            }

        res = this.aJTextField.getText().compareTo( o.aJTextField.getText() );

        return res;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(aFloat);
        result = prime * result + aInt;
        result = prime * result
                + ((aJTextField.getText() == null) ? 0 : aJTextField.getText().hashCode());
        result = prime * result + ((aString == null) ? 0 : aString.hashCode());
        result = prime * result + Arrays.hashCode(someBooleans);
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if( this == obj ) {
            return true;
            }
        else if( obj instanceof SimpleBean ) {
            return this.compareTo( SimpleBean.class.cast( obj ) ) == 0;
            }
        return false;
    }

}

class BeanTstWithPopulatorContener
{
    @Populator
    MyStrangeClass myStrangeClass;

    public BeanTstWithPopulatorContener()
    {
        myStrangeClass = new MyStrangeClass( "DeFaUlTvAlUe");
    }

    public BeanTstWithPopulatorContener( final String str )
    {
        myStrangeClass = new MyStrangeClass( str );
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "BeanTstWithPopulatorContener [myStrangeClass=" );
        builder.append( myStrangeClass );
        builder.append( ']' );
        return builder.toString();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((myStrangeClass == null) ? 0 : myStrangeClass.hashCode());
        return result;
    }

    @Override
    public boolean equals( Object obj )
    {
        if( this == obj ) return true;
        if( obj == null ) return false;
        if( getClass() != obj.getClass() ) return false;
        BeanTstWithPopulatorContener other = (BeanTstWithPopulatorContener)obj;
        if( myStrangeClass == null ) {
            if( other.myStrangeClass != null ) return false;
        } else if( !myStrangeClass.equals( other.myStrangeClass ) )
            return false;
        return true;
    }
}

class MyStrangeClass implements PopulatorContener
{
    private String privateRealContent;

    public MyStrangeClass( final String something )
    {
        this.privateRealContent = something;
    }

    @Override
    public String getConvertToString()
    {
        return this.privateRealContent;
    }

    @Override
    public void setConvertToString( String s )
    {
        this.privateRealContent = s;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "MyStrangeClass [privateRealContent=" );
        builder.append( privateRealContent );
        builder.append( ']' );
        return builder.toString();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((privateRealContent == null) ? 0 : privateRealContent
                        .hashCode());
        return result;
    }

    @Override
    public boolean equals( Object obj )
    {
        if( this == obj ) return true;
        if( obj == null ) return false;
        if( getClass() != obj.getClass() ) return false;
        MyStrangeClass other = (MyStrangeClass)obj;
        if( privateRealContent == null ) {
            if( other.privateRealContent != null ) return false;
        } else if( !privateRealContent.equals( other.privateRealContent ) )
            return false;
        return true;
    }
}
