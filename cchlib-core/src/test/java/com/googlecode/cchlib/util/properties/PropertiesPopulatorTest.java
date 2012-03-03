package com.googlecode.cchlib.util.properties;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Test;
import junit.framework.Assert;

/**
 *
 */
public class PropertiesPopulatorTest
{
    private final static Logger logger = Logger.getLogger( PropertiesPopulatorTest.class );

    @Test
    public void test_PropertiesPopulator() throws PopulatorException
    {
        PropertiesPopulator<BeanTst> pp = new PropertiesPopulator<BeanTst>( BeanTst.class );

        BeanTst bean = new BeanTst( "String", 1, 1.5F );

        {
            Properties properties = new Properties();
            pp.populateProperties(bean, properties);

            BeanTst copy = new BeanTst();
            pp.populateBean(properties, copy);

            Assert.assertEquals("Must be equal", bean, copy);
        }

        {
            final String prefix = "prefix.";
            Properties properties = new Properties();
            pp.populateProperties( bean, properties, prefix );

            BeanTst copy = new BeanTst();
            pp.populateBean( properties, prefix, copy );

            Assert.assertEquals("Must be equal", bean, copy);
        }
    }

    @Test
    public void test_PropertiesPopulator2() throws PopulatorException
    {
        PropertiesPopulator<BeanTst2> pp = new PropertiesPopulator<BeanTst2>( BeanTst2.class );

        BeanTst2 bean = new BeanTst2( "String", 1, 1.5F, "Hello" );

        Properties properties = new Properties();
        pp.populateProperties(bean, properties);

        BeanTst2 copy = new BeanTst2();
        pp.populateBean( properties, copy );

        for( String s : properties.stringPropertyNames() ) {
            String v = properties.getProperty( s );

            logger.info( "P(" + s + "," + v + ")" );
            }

//        logger.info( "bean.strangeClassContener=" + bean.strangeClassContener );
//        logger.info( "bean.strangeClassContener.get()=" + bean.strangeClassContener.get() );
//        logger.info( "bean.strangeClassContener.get().content=" + bean.strangeClassContener.get().realContent );
//
//        logger.info( "copy.strangeClassContener=" + copy.strangeClassContener );
//        logger.info( "copy.strangeClassContener.get()=" + copy.strangeClassContener.get() );
//        logger.info( "copy.strangeClassContener.get().content=" + copy.strangeClassContener.get().realContent );

        Assert.assertEquals("Must be equal", bean, copy);
    }
}

class BeanTst
{
    @Populator protected String aString;
    @Populator private int aInt;
    @Populator private float aFloat;

    public BeanTst()
    {
    }

    public BeanTst(String aString, int aInt, float aFloat)
    {
        this.aString = aString;
        this.aInt = aInt;
        this.aFloat = aFloat;
    }

    public final String getaString() {
        return aString;
    }

    public final void setaString(String aString) {
        this.aString = aString;
    }

    public final int getaInt() {
        return aInt;
    }

    public final void setaInt(int aInt) {
        this.aInt = aInt;
    }

    public final float getaFloat() {
        return aFloat;
    }

    public final void setaFloat(float aFloat) {
        this.aFloat = aFloat;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(aFloat);
        result = prime * result + aInt;
        result = prime * result + ((aString == null) ? 0 : aString.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BeanTst other = (BeanTst) obj;
        if (Float.floatToIntBits(aFloat) != Float.floatToIntBits(other.aFloat))
            return false;
        if (aInt != other.aInt)
            return false;
        if (aString == null) {
            if (other.aString != null)
                return false;
        } else if (!aString.equals(other.aString))
            return false;
        return true;
    }
}

class BeanTst2 //extends BeanTst
{
    @Populator AbstractPopulatorContener<StrangeClass> strangeClassContener
        = new AbstractPopulatorContener<StrangeClass>()
            {
                @Override
                public void init(String stringInitialization)
                {
                    set( new StrangeClass( stringInitialization ) );
                }
                @Override
                public String toString( StrangeClass content )
                {
                    return content.realContent.toString();
                }
            };

    public BeanTst2()
    {
        strangeClassContener.set( null );
    }

    public BeanTst2(String aString, int aInt, float aFloat, String initString )
    {
        //super(aString, aInt, aFloat);

        strangeClassContener.set( new StrangeClass( initString ) );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((strangeClassContener == null) ? 0 : strangeClassContener
                        .hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BeanTst2 other = (BeanTst2) obj;
        if (strangeClassContener == null) {
            if (other.strangeClassContener != null)
                return false;
        } else if (!strangeClassContener.equals(other.strangeClassContener))
            return false;
        return true;
    }
}

class StrangeClass
{
    Object realContent;

    public StrangeClass(String contentString)
    {
        realContent = contentString;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((realContent == null) ? 0 : realContent.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StrangeClass other = (StrangeClass) obj;
        if (realContent == null) {
            if (other.realContent != null)
                return false;
        } else if (!realContent.equals(other.realContent))
            return false;
        return true;
    }

}
