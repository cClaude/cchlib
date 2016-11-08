// $codepro.audit.disable largeNumberOfFields, constantNamingConvention
package com.googlecode.cchlib.i18n.unit.parts;

import javax.swing.JButton;
import org.apache.log4j.Logger;
import org.junit.Assert;
import com.googlecode.cchlib.i18n.annotation.I18nCustomMethod;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.unit.PrepTestPartInterface;
import com.googlecode.cchlib.i18n.unit.TestReference;
import com.googlecode.cchlib.i18n.unit.util.TestUtils;

public class I18nStringPart implements I18nAutoCoreUpdatable, TestReference
{
    private static final Logger LOGGER = Logger.getLogger( I18nStringPart.class );
    private static final String INIT_myString = "my-string-text-1";
    private static final String DEFAULT_BUNDLE_myString = "OK(myString)";

    @I18nString private String myString = INIT_myString;

    @I18nString @I18nIgnore private String myStringIgnore = INIT_myString;
    @I18nString private Object thisNotAString1;
    @I18nString private JButton thisNotAString2;

    private static final String INIT_myGlobalStringID1 = "my Global string 1 text";
    private static final String DEFAULT_BUNDLE_myGlobalStringID1 = "OK(I18nStringPart_GlobalStringID1)";
    @I18nString(id="I18nStringPart_GlobalStringID1") private String myGlobalStringID = INIT_myGlobalStringID1;

    private static final String INIT_myGlobalStringIDMethod1 = "my Global string 2 text";
    private static final String DEFAULT_BUNDLE_myGlobalStringIDMethod1 = "OK(myGlobalStringIDMethod1)";
    @I18nString(id="I18nStringPart_GlobalStringID2",method="customizeString1") private String myGlobalStringIDMethod1 = INIT_myGlobalStringIDMethod1;

    private static final String INIT_myGlobalStringIDMethod2 = "my Global string 3 text";
    private static final String DEFAULT_BUNDLE_myGlobalStringIDMethod2 = "OK(myGlobalStringIDMethod2)";
    @I18nString(method="customizeString2") private String myGlobalStringIDMethod2 = INIT_myGlobalStringIDMethod2;

    public I18nStringPart()
    {
        shouldNotBeFinal();
    }

    private void shouldNotBeFinal()
    {
        this.myString = INIT_myString;
        this.myStringIgnore = INIT_myString;
        this.myGlobalStringID = INIT_myGlobalStringID1;
    }

    @I18nCustomMethod
    public void customizeString1()
    {
        myGlobalStringIDMethod1 = DEFAULT_BUNDLE_myGlobalStringIDMethod1;
    }

    @I18nCustomMethod
    public void customizeString2()
    {
        myGlobalStringIDMethod2 = DEFAULT_BUNDLE_myGlobalStringIDMethod2;
    }

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
        Assert.assertEquals( INIT_myString, this.myString );
        Assert.assertEquals( INIT_myString, this.myStringIgnore );

        Assert.assertEquals( INIT_myGlobalStringID1, this.myGlobalStringID );
        // These values change (always resolve)
        // Assert.assertEquals( INIT_myGlobalStringIDMethod1, this.myGlobalStringIDMethod1 );
        // Assert.assertEquals( INIT_myGlobalStringIDMethod2, this.myGlobalStringIDMethod2 );
    }

    @Override
    public void performeI18n()
    {
        afterPrepTest();

        // Also verify values using custom methods
        Assert.assertEquals( INIT_myGlobalStringIDMethod1, this.myGlobalStringIDMethod1 );
        Assert.assertEquals( INIT_myGlobalStringIDMethod2, this.myGlobalStringIDMethod2 );

        TestUtils.performeI18n( this );

        LOGGER.info( "TEST RESULT: this.myString = " + this.myString );
        Assert.assertEquals( DEFAULT_BUNDLE_myString, this.myString );
        Assert.assertEquals( INIT_myString, this.myStringIgnore );

        LOGGER.info( "TEST RESULT: this.myGlobalStringID = " + this.myGlobalStringID );
        Assert.assertEquals( DEFAULT_BUNDLE_myGlobalStringID1, this.myGlobalStringID );

        // No change, since there is a syntax exception
        LOGGER.info( "TEST RESULT: this.myGlobalStringIDMethod1 = " + this.myGlobalStringIDMethod1 );
        Assert.assertEquals( INIT_myGlobalStringIDMethod1, this.myGlobalStringIDMethod1 );

        LOGGER.info( "TEST RESULT: this.myGlobalStringIDMethod2 = " + this.myGlobalStringIDMethod2 );
        Assert.assertEquals( DEFAULT_BUNDLE_myGlobalStringIDMethod2, this.myGlobalStringIDMethod2 );
    }

    @Override
    public int getSyntaxeExceptionCount()
    {
        return 3;
    }

    @Override
    public int getMissingResourceExceptionCount()
    {
        return 2;
    }

    public final String getMyString()
    {
        return myString;
    }
}
