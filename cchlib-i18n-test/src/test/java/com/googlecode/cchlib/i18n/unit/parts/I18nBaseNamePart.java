package com.googlecode.cchlib.i18n.unit.parts;

import static org.fest.assertions.api.Assertions.assertThat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Assume;
import com.googlecode.cchlib.i18n.annotation.I18n;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.i18n.unit.PrepTestPart;
import com.googlecode.cchlib.i18n.unit.REF;
import com.googlecode.cchlib.i18n.unit.TestReference;
import com.googlecode.cchlib.i18n.unit.TestReferenceDeprecated;
import com.googlecode.cchlib.i18n.unit.util.TestUtils;
import com.googlecode.cchlib.swing.SafeSwingUtilities;

@I18nName("I18nBaseNameTest")
public class I18nBaseNamePart
    extends JPanel
        implements I18nAutoCoreUpdatable, TestReference, TestReferenceDeprecated
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( I18nBaseNamePart.class );

    private static final String INIT_myString1 = "this is my String 1";
    private static final String DEFAULT_BUNDLE_myString1 = "OK(myString1)";
    @I18nString String myString1 = INIT_myString1;

    private static final String INIT_myString2 = "this is my String 2";
    private static final String DEFAULT_BUNDLE_myString2 = "OK(myString2)";
    @I18nString(id="MyString2ID") String myString2 = INIT_myString2;

    private static final String INIT_myJLabel1 = "my JLabel 1 text";
    private static final String DEFAULT_BUNDLE_myJLabel1 = "OK(myJLabel1)";
    private final JLabel myJLabel1;

    private static final String INIT_myJLabel2 = "my JLabel 2 text";
    private static final String DEFAULT_BUNDLE_myJLabel2 = "OK(myJLabel2)";
    @I18n(id="MyJLabel2ID") private final JLabel myJLabel2;

    public I18nBaseNamePart()
    {
        {
            this.myJLabel1 = new JLabel( INIT_myJLabel1 );
            add( this.myJLabel1 );
        }
        {
            this.myJLabel2 = new JLabel( INIT_myJLabel2 );
            add( this.myJLabel2 );
        }
    }

    @Override
    @Deprecated
    public void beforePrepTest(final PrepTestPart prepTest)
    {
        TestUtils.preparePrepTest( prepTest, this );
    }

    @Override
    public void afterPrepTest( final boolean firstRun )
    {
        Assert.assertEquals( INIT_myString1, this.myString1 );
        Assert.assertEquals( INIT_myString2, this.myString2 );

        Assert.assertEquals( INIT_myJLabel1, this.myJLabel1.getText() );
        Assert.assertEquals( INIT_myJLabel2, this.myJLabel2.getText() );
    }

    @Override
    @Deprecated
    public void performeI18n()
    {
        beforePerformeI18nTest();

        TestUtils.performeI18n( this );

        afterPerformeI18nTest_WithValidBundle();
    }

    @Override
    public void performeI18n( final AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }

    @Override
    public int getSyntaxeExceptionCount()
    {
        return 0;
    }

    @Override
    public int getMissingResourceExceptionCount()
    {
        return 4;
    }

    @Override // TestReference
    public void beforePerformeI18nTest()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        assertThat( this.myString1 ).isEqualTo( INIT_myString1 );
        assertThat( this.myString2 ).isEqualTo( INIT_myString2 );

        assertThat( this.myJLabel1.getText() ).isEqualTo( INIT_myJLabel1 );
        assertThat( this.myJLabel2.getText() ).isEqualTo( INIT_myJLabel2 );
    }

    @Override // TestReference
    public void afterPerformeI18nTest_WithValidBundle()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        assertThat( this.myString1 ).isEqualTo( DEFAULT_BUNDLE_myString1 );
        assertThat( this.myString2 ).isEqualTo( DEFAULT_BUNDLE_myString2 );

        assertThat( this.myJLabel1.getText() ).isEqualTo( DEFAULT_BUNDLE_myJLabel1 );
        assertThat( this.myJLabel2.getText() ).isEqualTo( DEFAULT_BUNDLE_myJLabel2 );
    }

    @Override // TestReference
    public void afterPerformeI18nTest_WithNotValidBundle()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        beforePerformeI18nTest(); // No Change
    }

    private static final int LOCALIZED_FIELDS = 4;
    private static final int IGNORED_FIELDS   = 12;

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
