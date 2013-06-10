package com.googlecode.cchlib.i18n.unit;

import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.unit.utils.RunI18nTestInterface;
import com.googlecode.cchlib.i18n.unit.utils.TestUtils;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import javax.swing.border.TitledBorder;

public class I18nDefaultTest extends JPanel implements I18nAutoCoreUpdatable, RunI18nTestInterface
{
    private static final long serialVersionUID = 1L;
    private final static Logger logger = Logger.getLogger( I18nDefaultTest.class );

    private static final String INIT_myJLabel = "my JLabel text";
    private static final String DEFAULT_BUNDLE_myJLabel = "OK(myJLabel)";
    private JLabel myJLabel;

    private static final String INIT_myJButton = "my JButton text";
    private static final String DEFAULT_BUNDLE_myJButton = "OK(myJButton)";
    private JButton myJButton;

    private static final String INIT_myJCheckBox = "my JCheckBox text";
    private static final String DEFAULT_BUNDLE_myJCheckBox = "OK(myJCheckBox)";
    private JCheckBox myJCheckBox;

    private static final String INIT_myJTabbedPane1 =  "my JTabbedPane panel1";
    private static final String INIT_myJTabbedPane2 =  "my JTabbedPane panel2";
    private static final String DEFAULT_BUNDLE_myJTabbedPane1 = "OK(myJTabbedPane1)";
    private static final String DEFAULT_BUNDLE_myJTabbedPane2 = "OK(myJTabbedPane2)";
    private JTabbedPane myJTabbedPane;
    private JPanel panel1;
    private JPanel panel2;

    private static final String INIT_myTitledBorder = "my myTitledBorder text";
    private static final String DEFAULT_BUNDLE_myTitledBorder = "OK(myTitledBorder)";
    private TitledBorder myTitledBorder;

    public I18nDefaultTest()
    {
        {
            myJLabel = new JLabel( INIT_myJLabel );
            super.add( myJLabel );
        }
        {
            myJButton = new JButton( INIT_myJButton );
            super.add( myJButton );
        }
        {
            myJCheckBox = new JCheckBox( INIT_myJCheckBox );
            super.add( myJCheckBox );
        }
        {
            myJTabbedPane = new JTabbedPane();
            super.add( myJTabbedPane );
            {
                this.panel1 = new JPanel();
                this.myJTabbedPane.addTab(INIT_myJTabbedPane1, null, this.panel1, null);
            }
            {
                this.panel2 = new JPanel();
                this.myJTabbedPane.addTab(INIT_myJTabbedPane2, null, this.panel2, null);

                this.myTitledBorder = new TitledBorder(null, INIT_myTitledBorder, TitledBorder.LEADING, TitledBorder.TOP, null, null);
                this.panel2.setBorder( this.myTitledBorder );
            }
        }
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
        Assert.assertEquals( INIT_myJLabel, this.myJLabel.getText() );
        Assert.assertEquals( INIT_myJButton, this.myJButton.getText() );
        Assert.assertEquals( INIT_myJCheckBox, this.myJCheckBox.getText() );

        Assert.assertEquals( 2, myJTabbedPane.getTabCount() );
        Assert.assertEquals( INIT_myJTabbedPane1, myJTabbedPane.getTitleAt( 0 ) );
        Assert.assertEquals( INIT_myJTabbedPane2, myJTabbedPane.getTitleAt( 1 ) );

        Assert.assertEquals( INIT_myTitledBorder, this.myTitledBorder.getTitle() );
    }

    @Override
    public void runPerformeI18nTest()
    {
        afterPrepTest();

        TestUtils.runPerformeI18nTest( this );

        {
            final String r = this.myJLabel.getText();
            logger.info( "TEST RESULT: this.myJLabel.getText() = " + r );
            Assert.assertEquals( DEFAULT_BUNDLE_myJLabel, r );
        }
        {
            final String r = this.myJButton.getText();
            logger.info( "TEST RESULT: this.myJButton.getText() = " + r );
            Assert.assertEquals( DEFAULT_BUNDLE_myJButton, r );
        }
        {
            final String r = this.myJCheckBox.getText();
            logger.info( "TEST RESULT: this.myJCheckBox.getText() = " + r );
            Assert.assertEquals( DEFAULT_BUNDLE_myJCheckBox, r );
        }
        {
            Assert.assertEquals( 2, myJTabbedPane.getTabCount() );
            Assert.assertEquals( DEFAULT_BUNDLE_myJTabbedPane1, myJTabbedPane.getTitleAt( 0 ) );
            Assert.assertEquals( DEFAULT_BUNDLE_myJTabbedPane2, myJTabbedPane.getTitleAt( 1 ) );
        }
        {
            final String r = this.myTitledBorder.getTitle();
            logger.info( "TEST RESULT: this.myTitledBorder.getTitle() = " + r );
            Assert.assertEquals( DEFAULT_BUNDLE_myTitledBorder, r );
        }
    }
}
