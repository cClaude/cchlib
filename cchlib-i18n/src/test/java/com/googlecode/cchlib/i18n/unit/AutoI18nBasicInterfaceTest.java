package com.googlecode.cchlib.i18n.unit;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;

import com.googlecode.cchlib.i18n.AutoI18nBasicInterface;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.unit.utils.RunI18nTestInterface;
import com.googlecode.cchlib.i18n.unit.utils.TestUtils;

public class AutoI18nBasicInterfaceTest implements I18nAutoCoreUpdatable, RunI18nTestInterface
{
    private static final Logger LOGGER = Logger.getLogger( AutoI18nBasicInterfaceTest.class );
    private static final String INIT_TEXT = "my MyAutoI18nBasicInterface text 1";
    private static final String DEFAULT_BUNDLE_TEXT = "OK(myAutoI18nBasicInterface)";

    private MyAutoI18nBasicInterface myAutoI18nBasicInterface;

    public AutoI18nBasicInterfaceTest()
    {
        this.myAutoI18nBasicInterface = new MyAutoI18nBasicInterface( INIT_TEXT );
    }

    @Ignore
    @Override // I18nAutoCoreUpdatable
    public void performeI18n( AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }

    @Override
    public void beforePrepTest(PrepTest prepTest)
    {
        TestUtils.preparePrepTest( prepTest, this );
    }

    @Override
    public void afterPrepTest()
    {
        Assert.assertEquals( INIT_TEXT, this.myAutoI18nBasicInterface.getI18nString() );
    }

    @Override
    public void runPerformeI18nTest()
    {
        afterPrepTest();

        TestUtils.runPerformeI18nTest( this );

        {
            final String text = this.myAutoI18nBasicInterface.getI18nString();
            LOGGER.info( "TEST RESULT: this.myAutoI18nBasicInterface.getI18nString() = " + text );
            Assert.assertEquals( DEFAULT_BUNDLE_TEXT, text );
        }
    }

    private static class MyAutoI18nBasicInterface implements AutoI18nBasicInterface
    {
        private String i18nString;

        public MyAutoI18nBasicInterface( String i18nString )
        {
            this.i18nString = i18nString;
        }

        @Override
        public String getI18nString()
        {
            return i18nString;
        }

        @Override
        public void setI18nString( String localString )
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
