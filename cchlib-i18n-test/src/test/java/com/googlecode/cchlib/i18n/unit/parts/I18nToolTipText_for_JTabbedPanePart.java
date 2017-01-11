package com.googlecode.cchlib.i18n.unit.parts;

import static com.googlecode.cchlib.i18n.unit.parts.I18nToolTipText_for_JTabbedPaneTest.IGNORED_FIELDS;
import static com.googlecode.cchlib.i18n.unit.parts.I18nToolTipText_for_JTabbedPaneTest.LOCALIZED_FIELDS;
import static com.googlecode.cchlib.i18n.unit.parts.I18nToolTipText_for_JTabbedPaneTest.TIP1;
import static com.googlecode.cchlib.i18n.unit.parts.I18nToolTipText_for_JTabbedPaneTest.TIP1_I18N;
import static com.googlecode.cchlib.i18n.unit.parts.I18nToolTipText_for_JTabbedPaneTest.TIP2;
import static com.googlecode.cchlib.i18n.unit.parts.I18nToolTipText_for_JTabbedPaneTest.TIP2_I18N;
import static com.googlecode.cchlib.i18n.unit.parts.I18nToolTipText_for_JTabbedPaneTest.TIP3;
import static com.googlecode.cchlib.i18n.unit.parts.I18nToolTipText_for_JTabbedPaneTest.TIP3_I18N;
import static com.googlecode.cchlib.i18n.unit.parts.I18nToolTipText_for_JTabbedPaneTest.TIP4;
import static com.googlecode.cchlib.i18n.unit.parts.I18nToolTipText_for_JTabbedPaneTest.TIP4_I18N;
import static com.googlecode.cchlib.i18n.unit.parts.I18nToolTipText_for_JTabbedPaneTest.TITLE1;
import static com.googlecode.cchlib.i18n.unit.parts.I18nToolTipText_for_JTabbedPaneTest.TITLE1_I18N;
import static com.googlecode.cchlib.i18n.unit.parts.I18nToolTipText_for_JTabbedPaneTest.TITLE2;
import static com.googlecode.cchlib.i18n.unit.parts.I18nToolTipText_for_JTabbedPaneTest.TITLE2_I18N;
import static com.googlecode.cchlib.i18n.unit.parts.I18nToolTipText_for_JTabbedPaneTest.TITLE3;
import static com.googlecode.cchlib.i18n.unit.parts.I18nToolTipText_for_JTabbedPaneTest.TITLE3_I18N;
import static com.googlecode.cchlib.i18n.unit.parts.I18nToolTipText_for_JTabbedPaneTest.TITLE4;
import static com.googlecode.cchlib.i18n.unit.parts.I18nToolTipText_for_JTabbedPaneTest.TITLE4_I18N;
import static org.fest.assertions.api.Assertions.assertThat;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.annotation.I18nToolTipText;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.i18n.unit.REF;
import com.googlecode.cchlib.i18n.unit.TestReference;

public class I18nToolTipText_for_JTabbedPanePart implements TestReference
{
    @I18nToolTipText private final JTabbedPane myJTabbedPane;

    public I18nToolTipText_for_JTabbedPanePart()
    {
        this.myJTabbedPane = new JTabbedPane();

        this.myJTabbedPane.addTab( TITLE1, (Icon)null, new JPanel(), TIP1 );
        this.myJTabbedPane.addTab( TITLE2, (Icon)null, new JPanel(), TIP2 );
        this.myJTabbedPane.addTab( TITLE3, (Icon)null, new JPanel(), TIP3 );
        this.myJTabbedPane.addTab( TITLE4, (Icon)null, new JPanel(), TIP4 );
    }

    @Override
    public void performeI18n( final AutoI18n autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }

    @Override
    public void beforePerformeI18nTest()
    {
        assertThat( this.myJTabbedPane.getTabCount() ).isEqualTo( 4 );

        assertThat( this.myJTabbedPane.getTitleAt( 0 ) ).isEqualTo( TITLE1 );
        assertThat( this.myJTabbedPane.getTitleAt( 1 ) ).isEqualTo( TITLE2 );
        assertThat( this.myJTabbedPane.getTitleAt( 2 ) ).isEqualTo( TITLE3 );
        assertThat( this.myJTabbedPane.getTitleAt( 3 ) ).isEqualTo( TITLE4 );

        assertThat( this.myJTabbedPane.getToolTipTextAt( 0 ) ).isEqualTo( TIP1 );
        assertThat( this.myJTabbedPane.getToolTipTextAt( 1 ) ).isEqualTo( TIP2 );
        assertThat( this.myJTabbedPane.getToolTipTextAt( 2 ) ).isEqualTo( TIP3 );
        assertThat( this.myJTabbedPane.getToolTipTextAt( 3 ) ).isEqualTo( TIP4 );
    }

    @Override
    public void afterPerformeI18nTest_WithValidBundle()
    {
        assertThat( this.myJTabbedPane.getTabCount() ).isEqualTo( 4 );

        assertThat( this.myJTabbedPane.getTitleAt( 0 ) ).isEqualTo( TITLE1_I18N );
        assertThat( this.myJTabbedPane.getTitleAt( 1 ) ).isEqualTo( TITLE2_I18N );
        assertThat( this.myJTabbedPane.getTitleAt( 2 ) ).isEqualTo( TITLE3_I18N );
        assertThat( this.myJTabbedPane.getTitleAt( 3 ) ).isEqualTo( TITLE4_I18N );

        assertThat( this.myJTabbedPane.getToolTipTextAt( 0 ) ).isEqualTo( TIP1_I18N );
        assertThat( this.myJTabbedPane.getToolTipTextAt( 1 ) ).isEqualTo( TIP2_I18N );
        assertThat( this.myJTabbedPane.getToolTipTextAt( 2 ) ).isEqualTo( TIP3_I18N );
        assertThat( this.myJTabbedPane.getToolTipTextAt( 3 ) ).isEqualTo( TIP4_I18N );
    }

    @Override
    public void afterPerformeI18nTest_WithNotValidBundle()
    {
        beforePerformeI18nTest();
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
