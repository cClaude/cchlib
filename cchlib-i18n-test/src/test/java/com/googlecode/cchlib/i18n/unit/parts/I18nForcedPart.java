// $codepro.audit.disable largeNumberOfFields, constantNamingConvention, questionableName
package com.googlecode.cchlib.i18n.unit.parts;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import com.googlecode.cchlib.i18n.annotation.I18n;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.unit.PrepTestPartInterface;
import com.googlecode.cchlib.i18n.unit.TestPartInterface;
import com.googlecode.cchlib.i18n.unit.util.TestUtils;

public class I18nForcedPart extends JPanel implements I18nAutoCoreUpdatable, TestPartInterface
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( I18nForcedPart.class );

    private static final String INIT_myJLabel = "my JLabel text";
    private static final String DEFAULT_BUNDLE_myJLabel = "OK(myJLabel)";
    @I18n private JLabel myJLabel;

    private static final String INIT_myJButton = "my JButton text";
    private static final String DEFAULT_BUNDLE_myJButton = "OK(myJButton)";
    @I18n private AbstractButton myJButton;

    private static final String INIT_myJCheckBox = "my JCheckBox text";
    private static final String DEFAULT_BUNDLE_myJCheckBox = "OK(myJCheckBox)";
    @I18n private JCheckBox myJCheckBox;

    private static final String INIT_myJTabbedPane1 =  "my JTabbedPane panel1";
    private static final String INIT_myJTabbedPane2 =  "my JTabbedPane panel2";
    private static final String DEFAULT_BUNDLE_myJTabbedPane1 = "OK(myJTabbedPane1)";
    private static final String DEFAULT_BUNDLE_myJTabbedPane2 = "OK(myJTabbedPane2)";
    @I18n private JTabbedPane myJTabbedPane;

    private static final String INIT_myTitledBorder = "my myTitledBorder text";
    private static final String DEFAULT_BUNDLE_myTitledBorder = "OK(myTitledBorder)";
    @I18n private TitledBorder myTitledBorder;

    private static final String INIT_myJTextArea = "this is my JTextArea text";
    private static final String DEFAULT_BUNDLE_myJTextArea = "OK(myJTextArea)";
    @I18n private JTextArea myJTextArea;
    private JTextArea myJTextAreaNoI18n;

    private static final String INIT_myJTextField = "this is my JTextField text";
    private static final String DEFAULT_BUNDLE_myJTextField = "OK(myJTextField)";
    @I18n private JTextField myJTextField;
    private JTextField myJTextFieldNoI18n;

    private static final String INIT_myJEditorPane = "this is my JEditorPane text";
    private static final String DEFAULT_BUNDLE_myJEditorPane = "OK(myJEditorPane)";
    @I18n private JEditorPane myJEditorPane;
    private JEditorPane myJEditorPaneNoI18n;

    public I18nForcedPart()
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
                JPanel panel1 = new JPanel();
                this.myJTabbedPane.addTab(INIT_myJTabbedPane1, null, panel1, null);
            }
            {
                JPanel panel2 = new JPanel();
                this.myJTabbedPane.addTab(INIT_myJTabbedPane2, null, panel2, null);

                this.myTitledBorder = new TitledBorder(null, INIT_myTitledBorder, TitledBorder.LEADING, TitledBorder.TOP, null, null);
                panel2.setBorder( this.myTitledBorder );
            }
        }
        {
            myJTextArea = new JTextArea();
            this.myJTextArea.setText( INIT_myJTextArea );
            super.add( myJTextArea );

            myJTextAreaNoI18n = new JTextArea();
            this.myJTextAreaNoI18n.setText( INIT_myJTextArea );
            super.add( myJTextAreaNoI18n );
        }
        {
            myJTextField = new JTextField();
            this.myJTextField.setText( INIT_myJTextField );
            super.add( myJTextField );

            myJTextFieldNoI18n = new JTextField();
            this.myJTextFieldNoI18n.setText( INIT_myJTextField );
            super.add( myJTextFieldNoI18n );
        }
        {
            myJEditorPane = new JEditorPane();
            this.myJEditorPane.setText( INIT_myJEditorPane );
            super.add( myJEditorPane );

            myJEditorPaneNoI18n = new JEditorPane();
            this.myJEditorPaneNoI18n.setText( INIT_myJEditorPane );
            super.add( myJEditorPaneNoI18n );
        }
    }

    @Ignore
    @Override // I18nAutoCoreUpdatable
    public void performeI18n( final AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }

    @Override
    public void beforePrepTest(PrepTestPartInterface prepTest)
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

        Assert.assertEquals( INIT_myJTextArea, this.myJTextArea.getText() );
        Assert.assertEquals( INIT_myJTextArea, this.myJTextAreaNoI18n.getText() );

        Assert.assertEquals( INIT_myJTextField, this.myJTextField.getText() );
        Assert.assertEquals( INIT_myJTextField, this.myJTextFieldNoI18n.getText() );

        Assert.assertEquals( INIT_myJEditorPane, this.myJEditorPane.getText() );
        Assert.assertEquals( INIT_myJEditorPane, this.myJEditorPaneNoI18n.getText() );
    }

    @Override
    public void runPerformeI18nTest()
    {
        afterPrepTest();

        TestUtils.runPerformeI18nTest( this );

        {
            final String r = this.myJLabel.getText();
            LOGGER.info( "TEST RESULT: this.myJLabel.getText() = " + r );
            Assert.assertEquals( DEFAULT_BUNDLE_myJLabel, r );
        }
        {
            final String r = this.myJButton.getText();
            LOGGER.info( "TEST RESULT: this.myJButton.getText() = " + r );
            Assert.assertEquals( DEFAULT_BUNDLE_myJButton, r );
        }
        {
            final String r = this.myJCheckBox.getText();
            LOGGER.info( "TEST RESULT: this.myJCheckBox.getText() = " + r );
            Assert.assertEquals( DEFAULT_BUNDLE_myJCheckBox, r );
        }
        {
            Assert.assertEquals( 2, myJTabbedPane.getTabCount() );
            Assert.assertEquals( DEFAULT_BUNDLE_myJTabbedPane1, myJTabbedPane.getTitleAt( 0 ) );
            Assert.assertEquals( DEFAULT_BUNDLE_myJTabbedPane2, myJTabbedPane.getTitleAt( 1 ) );
        }
        {
            final String r = this.myTitledBorder.getTitle();
            LOGGER.info( "TEST RESULT: this.myTitledBorder.getTitle() = " + r );
            Assert.assertEquals( DEFAULT_BUNDLE_myTitledBorder, r );
        }
        {
            final String r = this.myJTextArea.getText();
            LOGGER.info( "TEST RESULT: this.myJTextArea.getText() = " + r );
            Assert.assertEquals( DEFAULT_BUNDLE_myJTextArea, r );

            Assert.assertEquals( INIT_myJTextArea, this.myJTextAreaNoI18n.getText() );
        }

        {
            final String r = this.myJTextField.getText();
            LOGGER.info( "TEST RESULT: this.myJTextField.getText() = " + r );
            Assert.assertEquals( DEFAULT_BUNDLE_myJTextField, r );

            Assert.assertEquals( INIT_myJTextField, this.myJTextFieldNoI18n.getText() );
        }

        {
            final String r = this.myJEditorPane.getText();
            LOGGER.info( "TEST RESULT: this.myJEditorPane.getText() = " + r );
            Assert.assertEquals( DEFAULT_BUNDLE_myJEditorPane, r );

            Assert.assertEquals( INIT_myJEditorPane, this.myJEditorPaneNoI18n.getText() );
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
        return 8;
    }
}
