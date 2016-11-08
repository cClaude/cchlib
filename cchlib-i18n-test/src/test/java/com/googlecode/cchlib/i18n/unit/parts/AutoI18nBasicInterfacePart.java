package com.googlecode.cchlib.i18n.unit.parts;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import com.googlecode.cchlib.i18n.AutoI18nBasicInterface;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.unit.PrepTestPartInterface;
import com.googlecode.cchlib.i18n.unit.TestReference;
import com.googlecode.cchlib.i18n.unit.util.TestUtils;

public final class AutoI18nBasicInterfacePart implements I18nAutoCoreUpdatable, TestReference
{
    private static final Logger LOGGER = Logger.getLogger( AutoI18nBasicInterfacePart.class );
    private static final String INIT_TEXT = "my MyAutoI18nBasicInterface text 1";
    private static final String DEFAULT_BUNDLE_TEXT = "OK(myAutoI18nBasicInterface)";

    private final MyAutoI18nBasicInterface myAutoI18nBasicInterface;

    public AutoI18nBasicInterfacePart()
    {
        this.myAutoI18nBasicInterface = new MyAutoI18nBasicInterface( INIT_TEXT );
    }

    @Ignore
    @Override // I18nAutoCoreUpdatable
    public void performeI18n( final AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }

    @Override
    public void beforePrepTest(final PrepTestPartInterface prepTest)
    {
        TestUtils.preparePrepTest( prepTest, this );
    }

    @Override
    public void afterPrepTest()
    {
        Assert.assertEquals( INIT_TEXT, this.myAutoI18nBasicInterface.getI18nString() );
    }

    @Override
    public void performeI18n()
    {
        afterPrepTest();

        TestUtils.performeI18n( this );

        {
            final String text = this.myAutoI18nBasicInterface.getI18nString();
            LOGGER.info( "TEST RESULT: this.myAutoI18nBasicInterface.getI18nString() = " + text );
            Assert.assertEquals( DEFAULT_BUNDLE_TEXT, text );
        }
    }

    private static class MyAutoI18nBasicInterface implements AutoI18nBasicInterface
    {
        private String i18nString;

        public MyAutoI18nBasicInterface( final String i18nString )
        {
            this.i18nString = i18nString;
        }

        @Override
        public String getI18nString()
        {
            return i18nString;
        }

        @Override
        public void setI18nString( final String localString )
        {
            this.i18nString = localString;
        }
    }

    @Override
    public int getSyntaxeExceptionCount()
    {
        return 0;
    }

    @Override
    public int getMissingResourceExceptionCount()
    {
        return 1;
    }
}
