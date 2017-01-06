package com.googlecode.cchlib.i18n.unit.parts;

import static org.fest.assertions.api.Assertions.assertThat;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Ignore;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.i18n.unit.PrepTestPart;
import com.googlecode.cchlib.i18n.unit.REF;
import com.googlecode.cchlib.i18n.unit.TestReference;
import com.googlecode.cchlib.i18n.unit.TestReferenceDeprecated;
import com.googlecode.cchlib.i18n.unit.util.TestUtils;
import com.googlecode.cchlib.swing.SafeSwingUtilities;

public class I18nDefaultPart
    extends JPanel
        implements I18nAutoCoreUpdatable, TestReference, TestReferenceDeprecated
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( I18nDefaultPart.class );

    private static final String INIT_myJLabel = "my JLabel text";
    private static final String DEFAULT_BUNDLE_myJLabel = "OK(myJLabel)";
    private final JLabel myJLabel;

    private static final String INIT_myJButton = "my JButton text";
    private static final String DEFAULT_BUNDLE_myJButton = "OK(myJButton)";
    private final JButton myJButton;

    private static final String INIT_myJCheckBox = "my JCheckBox text";
    private static final String DEFAULT_BUNDLE_myJCheckBox = "OK(myJCheckBox)";
    private final JCheckBox myJCheckBox;

    private static final String INIT_myJTabbedPane1 =  "my JTabbedPane panel1";
    private static final String INIT_myJTabbedPane2 =  "my JTabbedPane panel2";
    private static final String DEFAULT_BUNDLE_myJTabbedPane1 = "OK(myJTabbedPane1)";
    private static final String DEFAULT_BUNDLE_myJTabbedPane2 = "OK(myJTabbedPane2)";
    private final JTabbedPane myJTabbedPane;
    private final JPanel panel1;
    private final JPanel panel2;

    private static final String INIT_myTitledBorder = "my myTitledBorder text";
    private static final String DEFAULT_BUNDLE_myTitledBorder = "OK(myTitledBorder)";
    private final TitledBorder myTitledBorder;

    public I18nDefaultPart()
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
                this.panel1 = new JPanel();
                this.myJTabbedPane.addTab(INIT_myJTabbedPane1, (Icon)null, this.panel1, (String)null);
            }
            {
                this.panel2 = new JPanel();
                this.myJTabbedPane.addTab(INIT_myJTabbedPane2, (Icon)null, this.panel2, (String)null);

                this.myTitledBorder = new TitledBorder(null, INIT_myTitledBorder, TitledBorder.LEADING, TitledBorder.TOP, null, null);
                this.panel2.setBorder( this.myTitledBorder );
            }
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
        return 5;
    }

    @Override // TestReference
    public void beforePerformeI18nTest()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        Assert.assertEquals( INIT_myJLabel, this.myJLabel.getText() );
        Assert.assertEquals( INIT_myJButton, this.myJButton.getText() );
        Assert.assertEquals( INIT_myJCheckBox, this.myJCheckBox.getText() );

        Assert.assertEquals( 2, this.myJTabbedPane.getTabCount() );
        Assert.assertEquals( INIT_myJTabbedPane1, this.myJTabbedPane.getTitleAt( 0 ) );
        Assert.assertEquals( INIT_myJTabbedPane2, this.myJTabbedPane.getTitleAt( 1 ) );

        Assert.assertEquals( INIT_myTitledBorder, this.myTitledBorder.getTitle() );
    }

    @Override // TestReference
    public void afterPerformeI18nTest_WithValidBundle()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        LOGGER.info( "TEST RESULT: this.myJLabel.getText() = " + this.myJLabel.getText() );
        LOGGER.info( "TEST RESULT: this.myJButton.getText() = " + this.myJButton.getText() );
        LOGGER.info( "TEST RESULT: this.myJCheckBox.getText() = " + this.myJCheckBox.getText() );
        LOGGER.info( "TEST RESULT: this.myTitledBorder.getTitle() = " + this.myTitledBorder.getTitle() );

        assertThat( this.myJLabel.getText() ).isEqualTo( DEFAULT_BUNDLE_myJLabel );
        assertThat( this.myJButton.getText() ).isEqualTo( DEFAULT_BUNDLE_myJButton );
        assertThat( this.myJCheckBox.getText() ).isEqualTo( DEFAULT_BUNDLE_myJCheckBox );

        assertThat( this.myJTabbedPane.getTabCount() ).isEqualTo( 2 );
        assertThat( this.myJTabbedPane.getTitleAt( 0 ) ).isEqualTo( DEFAULT_BUNDLE_myJTabbedPane1 );
        assertThat( this.myJTabbedPane.getTitleAt( 1 ) ).isEqualTo( DEFAULT_BUNDLE_myJTabbedPane2 );

        assertThat( this.myTitledBorder.getTitle() ).isEqualTo( DEFAULT_BUNDLE_myTitledBorder );
    }

    @Override // TestReference
    public void afterPerformeI18nTest_WithNotValidBundle()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        beforePerformeI18nTest(); // No Change
    }

    private static final int LOCALIZED_FIELDS = 6;
    private static final int IGNORED_FIELDS   = 18;

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
