package com.googlecode.cchlib.i18n.unit;

import javax.swing.JLabel;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import org.junit.Assert;
import com.googlecode.cchlib.i18n.annotation.I18n;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.unit.utils.RunI18nTestInterface;
import com.googlecode.cchlib.i18n.unit.utils.TestUtils;

@I18nName("I18nBaseNameTest")
public class I18nBaseNameTest extends JPanel implements I18nAutoCoreUpdatable, RunI18nTestInterface
{
    private static final long serialVersionUID = 1L;
    private final static Logger logger = Logger.getLogger( I18nBaseNameTest.class );

    private static final String INIT_myString1 = "this is my String 1";
    private static final String DEFAULT_BUNDLE_myString1 = "OK(myString1)";
    @I18nString String myString1 = INIT_myString1;

    private static final String INIT_myString2 = "this is my String 2";
    private static final String DEFAULT_BUNDLE_myString2 = "OK(myString2)";
    @I18nString(id="MyString2ID") String myString2 = INIT_myString2;

    private static final String INIT_myJLabel1 = "my JLabel 1 text";
    private static final String DEFAULT_BUNDLE_myJLabel1 = "OK(myJLabel1)";
    private JLabel myJLabel1;

    private static final String INIT_myJLabel2 = "my JLabel 2 text";
    private static final String DEFAULT_BUNDLE_myJLabel2 = "OK(myJLabel2)";
    @I18n(id="MyJLabel2ID") private JLabel myJLabel2;

    public I18nBaseNameTest()
    {
        {
            myJLabel1 = new JLabel( INIT_myJLabel1 );
            add( myJLabel1 );
        }
        {
            myJLabel2 = new JLabel( INIT_myJLabel2 );
            add( myJLabel2 );
        }
    }

    @Override
    public void beforePrepTest(PrepTest prepTest)
    {
        TestUtils.preparePrepTest( prepTest, this );
    }

    @Override
    public void afterPrepTest()
    {
        Assert.assertEquals( INIT_myString1, this.myString1 );
        Assert.assertEquals( INIT_myString2, this.myString2 );

        Assert.assertEquals( INIT_myJLabel1, this.myJLabel1.getText() );
        Assert.assertEquals( INIT_myJLabel2, this.myJLabel2.getText() );
    }

    @Override
    public void runPerformeI18nTest()
    {
        Assert.assertEquals( INIT_myString1, this.myString1 );
        Assert.assertEquals( INIT_myString2, this.myString2 );

        Assert.assertEquals( INIT_myJLabel1, this.myJLabel1.getText() );
        Assert.assertEquals( INIT_myJLabel2, this.myJLabel2.getText() );

        TestUtils.runPerformeI18nTest( this );

        Assert.assertEquals( DEFAULT_BUNDLE_myString1, this.myString1 );
        Assert.assertEquals( DEFAULT_BUNDLE_myString2, this.myString2 );
        {
            final String r = this.myJLabel1.getText();
            logger.info( "TEST RESULT: this.myJLabel1.getText() = " + r );
            Assert.assertEquals( DEFAULT_BUNDLE_myJLabel1, r );
        }
        {
            final String r = this.myJLabel2.getText();
            logger.info( "TEST RESULT: this.myJLabel2.getText() = " + r );
            Assert.assertEquals( DEFAULT_BUNDLE_myJLabel2, r );
        }
    }

    @Override
    public void performeI18n( AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }

}
