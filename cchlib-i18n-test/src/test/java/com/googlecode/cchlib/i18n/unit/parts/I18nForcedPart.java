package com.googlecode.cchlib.i18n.unit.parts;

import static org.fest.assertions.api.Assertions.assertThat;
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
import org.junit.Assume;
import org.junit.Ignore;
import com.googlecode.cchlib.i18n.annotation.I18n;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.i18n.unit.PrepTestPart;
import com.googlecode.cchlib.i18n.unit.REF;
import com.googlecode.cchlib.i18n.unit.TestReference;
import com.googlecode.cchlib.i18n.unit.TestReferenceDeprecated;
import com.googlecode.cchlib.i18n.unit.util.TestUtils;
import com.googlecode.cchlib.swing.SafeSwingUtilities;

public class I18nForcedPart
    extends JPanel
        implements I18nAutoCoreUpdatable, TestReference, TestReferenceDeprecated
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( I18nForcedPart.class );

    private static final String INIT_myJLabel = "my JLabel text";
    private static final String DEFAULT_BUNDLE_myJLabel = "OK(myJLabel)";
    @I18n private final JLabel myJLabel;

    private static final String INIT_myJButton = "my JButton text";
    private static final String DEFAULT_BUNDLE_myJButton = "OK(myJButton)";
    @I18n private final AbstractButton myJButton;

    private static final String INIT_myJCheckBox = "my JCheckBox text";
    private static final String DEFAULT_BUNDLE_myJCheckBox = "OK(myJCheckBox)";
    @I18n private final JCheckBox myJCheckBox;

    private static final String INIT_myJTabbedPane1 =  "my JTabbedPane panel1";
    private static final String INIT_myJTabbedPane2 =  "my JTabbedPane panel2";
    private static final String DEFAULT_BUNDLE_myJTabbedPane1 = "OK(myJTabbedPane1)";
    private static final String DEFAULT_BUNDLE_myJTabbedPane2 = "OK(myJTabbedPane2)";
    @I18n private final JTabbedPane myJTabbedPane;

    private static final String INIT_myTitledBorder = "my myTitledBorder text";
    private static final String DEFAULT_BUNDLE_myTitledBorder = "OK(myTitledBorder)";
    @I18n private final TitledBorder myTitledBorder;

    private static final String INIT_myJTextArea = "this is my JTextArea text";
    private static final String DEFAULT_BUNDLE_myJTextArea = "OK(myJTextArea)";
    @I18n private final JTextArea myJTextArea;
    private final JTextArea myJTextAreaNoI18n;

    private static final String INIT_myJTextField = "this is my JTextField text";
    private static final String DEFAULT_BUNDLE_myJTextField = "OK(myJTextField)";
    @I18n private final JTextField myJTextField;
    private final JTextField myJTextFieldNoI18n;

    private static final String INIT_myJEditorPane = "this is my JEditorPane text";
    private static final String DEFAULT_BUNDLE_myJEditorPane = "OK(myJEditorPane)";
    @I18n private final JEditorPane myJEditorPane;
    private final JEditorPane myJEditorPaneNoI18n;

    public I18nForcedPart()
    {
        {
            this.myJLabel = new JLabel( INIT_myJLabel );
            super.add( this.myJLabel );
        }
        {
            this.myJButton = new JButton( INIT_myJButton );
            super.add( this.myJButton );
        }
        {
            this.myJCheckBox = new JCheckBox( INIT_myJCheckBox );
            super.add( this.myJCheckBox );
        }
        {
            this.myJTabbedPane = new JTabbedPane();
            super.add( this.myJTabbedPane );
            {
                final JPanel panel1 = new JPanel();
                this.myJTabbedPane.addTab(INIT_myJTabbedPane1, null, panel1, null);
            }
            {
                final JPanel panel2 = new JPanel();
                this.myJTabbedPane.addTab(INIT_myJTabbedPane2, null, panel2, null);

                this.myTitledBorder = new TitledBorder(null, INIT_myTitledBorder, TitledBorder.LEADING, TitledBorder.TOP, null, null);
                panel2.setBorder( this.myTitledBorder );
            }
        }
        {
            this.myJTextArea = new JTextArea();
            this.myJTextArea.setText( INIT_myJTextArea );
            super.add( this.myJTextArea );

            this.myJTextAreaNoI18n = new JTextArea();
            this.myJTextAreaNoI18n.setText( INIT_myJTextArea );
            super.add( this.myJTextAreaNoI18n );
        }
        {
            this.myJTextField = new JTextField();
            this.myJTextField.setText( INIT_myJTextField );
            super.add( this.myJTextField );

            this.myJTextFieldNoI18n = new JTextField();
            this.myJTextFieldNoI18n.setText( INIT_myJTextField );
            super.add( this.myJTextFieldNoI18n );
        }
        {
            this.myJEditorPane = new JEditorPane();
            this.myJEditorPane.setText( INIT_myJEditorPane );
            super.add( this.myJEditorPane );

            this.myJEditorPaneNoI18n = new JEditorPane();
            this.myJEditorPaneNoI18n.setText( INIT_myJEditorPane );
            super.add( this.myJEditorPaneNoI18n );
        }
    }

    @Ignore
    @Override // I18nAutoCoreUpdatable
    public void performeI18n( final AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }

    @Override
    public void beforePrepTest(final PrepTestPart prepTest)
    {
        TestUtils.preparePrepTest( prepTest, this );
    }

    @Override
    public void afterPrepTest()
    {
        beforePerformeI18nTest();
    }

    @Override
    public void performeI18n()
    {
        afterPrepTest();

        TestUtils.performeI18n( this );

        afterPerformeI18nTest_WithValidBundle();
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

    @Override // TestReference
    public void beforePerformeI18nTest()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        assertThat( this.myJLabel.getText() ).isEqualTo( INIT_myJLabel );
        assertThat( this.myJButton.getText() ).isEqualTo( INIT_myJButton );
        assertThat( this.myJCheckBox.getText() ).isEqualTo( INIT_myJCheckBox );

        assertThat( this.myJTabbedPane.getTabCount() ).isEqualTo( 2 );
        assertThat( this.myJTabbedPane.getTitleAt( 0 ) ).isEqualTo( INIT_myJTabbedPane1 );
        assertThat( this.myJTabbedPane.getTitleAt( 1 ) ).isEqualTo( INIT_myJTabbedPane2 );

        assertThat( this.myTitledBorder.getTitle() ).isEqualTo( INIT_myTitledBorder );

        assertThat( this.myJTextArea.getText() ).isEqualTo( INIT_myJTextArea );
        assertThat( this.myJTextAreaNoI18n.getText() ).isEqualTo( INIT_myJTextArea );

        assertThat( this.myJTextField.getText() ).isEqualTo( INIT_myJTextField );
        assertThat( this.myJTextFieldNoI18n.getText() ).isEqualTo( INIT_myJTextField );

        assertThat( this.myJEditorPane.getText() ).isEqualTo( INIT_myJEditorPane );
        assertThat( this.myJEditorPaneNoI18n.getText() ).isEqualTo( INIT_myJEditorPane );
    }

    @Override // TestReference
    public void afterPerformeI18nTest_WithValidBundle()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        LOGGER.info( "TEST RESULT: this.myJLabel.getText() = " + this.myJLabel.getText() );
        LOGGER.info( "TEST RESULT: this.myJButton.getText() = " + this.myJButton.getText() );
        LOGGER.info( "TEST RESULT: this.myJCheckBox.getText() = " + this.myJCheckBox.getText() );

        LOGGER.info( "TEST RESULT: this.myTitledBorder.getTitle() = " + this.myTitledBorder.getTitle() );
        LOGGER.info( "TEST RESULT: this.myJTextArea.getText() = " + this.myJTextArea.getText() );
        LOGGER.info( "TEST RESULT: this.myJTextField.getText() = " + this.myJTextField.getText() );
        LOGGER.info( "TEST RESULT: this.myJEditorPane.getText() = " + this.myJEditorPane.getText() );

        assertThat( this.myJLabel.getText() ).isEqualTo( DEFAULT_BUNDLE_myJLabel );
        assertThat( this.myJButton.getText() ).isEqualTo( DEFAULT_BUNDLE_myJButton );
        assertThat( this.myJCheckBox.getText() ).isEqualTo( DEFAULT_BUNDLE_myJCheckBox );

        assertThat( this.myJTabbedPane.getTabCount() ).isEqualTo( 2 );
        assertThat( this.myJTabbedPane.getTitleAt( 0 ) ).isEqualTo( DEFAULT_BUNDLE_myJTabbedPane1 );
        assertThat( this.myJTabbedPane.getTitleAt( 1 ) ).isEqualTo( DEFAULT_BUNDLE_myJTabbedPane2 );

        assertThat( this.myTitledBorder.getTitle() ).isEqualTo( DEFAULT_BUNDLE_myTitledBorder );

        assertThat( this.myJTextArea.getText() ).isEqualTo( DEFAULT_BUNDLE_myJTextArea );
        assertThat( this.myJTextAreaNoI18n.getText() ).isEqualTo( INIT_myJTextArea );

        assertThat( this.myJTextField.getText() ).isEqualTo( DEFAULT_BUNDLE_myJTextField );
        assertThat( this.myJTextFieldNoI18n.getText() ).isEqualTo( INIT_myJTextField );

        assertThat( this.myJEditorPane.getText() ).isEqualTo( DEFAULT_BUNDLE_myJEditorPane );
        assertThat( this.myJEditorPaneNoI18n.getText() ).isEqualTo( INIT_myJEditorPane );
    }

    @Override // TestReference
    public void afterPerformeI18nTest_WithNotValidBundle()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        beforePerformeI18nTest(); // No Change
    }

    private static final int LOCALIZED_FIELDS = 9;
    private static final int IGNORED_FIELDS   = 25;

    @Override // TestReference
    public void afterResourceBuilderTest_WithValidBundle( final I18nResourceBuilderResult result )
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        assertThat( result.getIgnoredFields() ).hasSize( IGNORED_FIELDS );
        assertThat( result.getLocalizedFields() ).hasSize( LOCALIZED_FIELDS );
        assertThat( result.getMissingProperties() ).hasSize( 0 );
        assertThat( result.getUnusedProperties() ).hasSize( REF.size() - LOCALIZED_FIELDS );
    }

    @Override // TestReference
    public void afterResourceBuilderTest_WithNotValidBundle( final I18nResourceBuilderResult result )
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        assertThat( result.getIgnoredFields() ).hasSize( IGNORED_FIELDS );
        assertThat( result.getLocalizedFields() ).hasSize( 0 );
        assertThat( result.getMissingProperties() ).hasSize( LOCALIZED_FIELDS );
        assertThat( result.getUnusedProperties() ).hasSize( 0 );
     }
}
