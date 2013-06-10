package com.googlecode.cchlib.i18n.unit;

import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.unit.utils.RunI18nTestInterface;
import com.googlecode.cchlib.i18n.unit.utils.TestUtils;
import javax.swing.JButton;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;

public class I18nStringTest implements I18nAutoCoreUpdatable, RunI18nTestInterface
{
    private final static Logger logger = Logger.getLogger( I18nStringTest.class );

    private final static String INIT_myString = "my string text 1";
    private final static String DEFAULT_BUNDLE_myString = "OK(myString)";
    @I18nString private String myString = INIT_myString;

    @I18nString @I18nIgnore private String myStringIgnore = INIT_myString;
    @I18nString private Object thisNotAString1;
    @I18nString private JButton thisNotAString2;

    private final static String INIT_myGlobalStringID1 = "my Global string 1 text";
    private final static String DEFAULT_BUNDLE_myGlobalStringID1 = "OK(GlobalStringID)";
    @I18nString(id="GlobalStringID") private String myGlobalStringID = INIT_myGlobalStringID1;

    private final static String INIT_myGlobalStringIDMethod1 = "my Global string 2 text";
    private final static String DEFAULT_BUNDLE_myGlobalStringIDMethod1 = "OK(myGlobalStringIDMethod1)";
    @I18nString(id="GlobalStringID",method="customizeString1") private String myGlobalStringIDMethod1 = INIT_myGlobalStringIDMethod1;

    public void customizeString1()
    {
        myGlobalStringIDMethod1 = DEFAULT_BUNDLE_myGlobalStringIDMethod1;
    }

    private final static String INIT_myGlobalStringIDMethod2 = "my Global string 3 text";
    private final static String DEFAULT_BUNDLE_myGlobalStringIDMethod2 = "OK(myGlobalStringIDMethod2)";
    @I18nString(method="customizeString2") private String myGlobalStringIDMethod2 = INIT_myGlobalStringIDMethod2;

    public void customizeString2()
    {
        myGlobalStringIDMethod2 = DEFAULT_BUNDLE_myGlobalStringIDMethod2;
    }

    public I18nStringTest()
    {
        // Nothing !
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
        Assert.assertEquals( INIT_myString, this.myString );
        Assert.assertEquals( INIT_myString, this.myStringIgnore );

        Assert.assertEquals( INIT_myGlobalStringID1, this.myGlobalStringID );
        // These values change (always resolve) 
        // Assert.assertEquals( INIT_myGlobalStringIDMethod1, this.myGlobalStringIDMethod1 );
        // Assert.assertEquals( INIT_myGlobalStringIDMethod2, this.myGlobalStringIDMethod2 );
    }

    @Override
    public void runPerformeI18nTest()
    {
        afterPrepTest();
        
        // Also verify values using custom methods
        Assert.assertEquals( INIT_myGlobalStringIDMethod1, this.myGlobalStringIDMethod1 );
        Assert.assertEquals( INIT_myGlobalStringIDMethod2, this.myGlobalStringIDMethod2 );

        TestUtils.runPerformeI18nTest( this );

        logger.info( "TEST RESULT: this.myString = " + this.myString );
        Assert.assertEquals( DEFAULT_BUNDLE_myString, this.myString );
        Assert.assertEquals( INIT_myString, this.myStringIgnore );

        logger.info( "TEST RESULT: this.myGlobalStringID = " + this.myGlobalStringID );
        Assert.assertEquals( DEFAULT_BUNDLE_myGlobalStringID1, this.myGlobalStringID );
        
        logger.info( "TEST RESULT: this.myGlobalStringIDMethod1 = " + this.myGlobalStringIDMethod1 );
        Assert.assertEquals( DEFAULT_BUNDLE_myGlobalStringIDMethod1, this.myGlobalStringIDMethod1 );
        
        logger.info( "TEST RESULT: this.myGlobalStringIDMethod2 = " + this.myGlobalStringIDMethod2 );
        Assert.assertEquals( DEFAULT_BUNDLE_myGlobalStringIDMethod2, this.myGlobalStringIDMethod2 );
    }
}
