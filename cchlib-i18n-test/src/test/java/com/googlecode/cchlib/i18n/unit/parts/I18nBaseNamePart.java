package com.googlecode.cchlib.i18n.unit.parts;

import static com.googlecode.cchlib.i18n.unit.parts.I18nBaseNamePartTest.DEFAULT_BUNDLE_myJLabel1;
import static com.googlecode.cchlib.i18n.unit.parts.I18nBaseNamePartTest.DEFAULT_BUNDLE_myJLabel2;
import static com.googlecode.cchlib.i18n.unit.parts.I18nBaseNamePartTest.DEFAULT_BUNDLE_myString1;
import static com.googlecode.cchlib.i18n.unit.parts.I18nBaseNamePartTest.DEFAULT_BUNDLE_myString2;
import static com.googlecode.cchlib.i18n.unit.parts.I18nBaseNamePartTest.IGNORED_FIELDS;
import static com.googlecode.cchlib.i18n.unit.parts.I18nBaseNamePartTest.INIT_myJLabel1;
import static com.googlecode.cchlib.i18n.unit.parts.I18nBaseNamePartTest.INIT_myJLabel2;
import static com.googlecode.cchlib.i18n.unit.parts.I18nBaseNamePartTest.INIT_myString1;
import static com.googlecode.cchlib.i18n.unit.parts.I18nBaseNamePartTest.INIT_myString2;
import static com.googlecode.cchlib.i18n.unit.parts.I18nBaseNamePartTest.LOCALIZED_FIELDS;
import static org.fest.assertions.api.Assertions.assertThat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.annotation.I18n;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.i18n.unit.REF;
import com.googlecode.cchlib.i18n.unit.TestReference;

@I18nName("I18nBaseNameTest")
public class I18nBaseNamePart
    extends JPanel
        implements I18nAutoUpdatable, TestReference
{
    private static final long serialVersionUID = 1L;

    @I18nString String myString1 = INIT_myString1;

    @I18nString(id="MyString2ID") String myString2 = INIT_myString2;

    private final JLabel myJLabel1;

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
    public void performeI18n( final AutoI18n autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }

    @Override // TestReference
    public void beforePerformeI18nTest()
    {
        assertThat( this.myString1 ).isEqualTo( INIT_myString1 );
        assertThat( this.myString2 ).isEqualTo( INIT_myString2 );

        assertThat( this.myJLabel1.getText() ).isEqualTo( INIT_myJLabel1 );
        assertThat( this.myJLabel2.getText() ).isEqualTo( INIT_myJLabel2 );
    }

    @Override // TestReference
    public void afterPerformeI18nTest_WithValidBundle()
    {
        assertThat( this.myString1 ).isEqualTo( DEFAULT_BUNDLE_myString1 );
        assertThat( this.myString2 ).isEqualTo( DEFAULT_BUNDLE_myString2 );

        assertThat( this.myJLabel1.getText() ).isEqualTo( DEFAULT_BUNDLE_myJLabel1 );
        assertThat( this.myJLabel2.getText() ).isEqualTo( DEFAULT_BUNDLE_myJLabel2 );
    }

    @Override // TestReference
    public void afterPerformeI18nTest_WithNotValidBundle()
    {
        beforePerformeI18nTest(); // No Change
    }

    @Override // TestReference
    public void afterResourceBuilderTest_WithValidBundle( final I18nResourceBuilderResult result )
    {
        assertThat( result.getIgnoredFields() ).hasSize( IGNORED_FIELDS );
        assertThat( result.getLocalizedFields() ).hasSize( LOCALIZED_FIELDS );
        assertThat( result.getMissingProperties() ).hasSize( 0 );
        assertThat( result.getUnusedProperties() ).hasSize( REF.size() - LOCALIZED_FIELDS );
    }

    @Override // TestReference
    public void afterResourceBuilderTest_WithNotValidBundle( final I18nResourceBuilderResult result )
    {
        assertThat( result.getIgnoredFields() ).hasSize( IGNORED_FIELDS );
        assertThat( result.getLocalizedFields() ).hasSize( 0 );
        assertThat( result.getMissingProperties() ).hasSize( LOCALIZED_FIELDS );
        assertThat( result.getUnusedProperties() ).hasSize( 0 );
     }
}
