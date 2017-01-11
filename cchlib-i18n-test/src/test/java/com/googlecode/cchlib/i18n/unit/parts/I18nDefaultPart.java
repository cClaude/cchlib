package com.googlecode.cchlib.i18n.unit.parts;

import static com.googlecode.cchlib.i18n.unit.parts.I18nDefaultTest.DEFAULT_BUNDLE_myJButton;
import static com.googlecode.cchlib.i18n.unit.parts.I18nDefaultTest.DEFAULT_BUNDLE_myJCheckBox;
import static com.googlecode.cchlib.i18n.unit.parts.I18nDefaultTest.DEFAULT_BUNDLE_myJLabel;
import static com.googlecode.cchlib.i18n.unit.parts.I18nDefaultTest.DEFAULT_BUNDLE_myJTabbedPane1;
import static com.googlecode.cchlib.i18n.unit.parts.I18nDefaultTest.DEFAULT_BUNDLE_myJTabbedPane2;
import static com.googlecode.cchlib.i18n.unit.parts.I18nDefaultTest.DEFAULT_BUNDLE_myTitledBorder;
import static com.googlecode.cchlib.i18n.unit.parts.I18nDefaultTest.IGNORED_FIELDS;
import static com.googlecode.cchlib.i18n.unit.parts.I18nDefaultTest.INIT_myJButton;
import static com.googlecode.cchlib.i18n.unit.parts.I18nDefaultTest.INIT_myJCheckBox;
import static com.googlecode.cchlib.i18n.unit.parts.I18nDefaultTest.INIT_myJLabel;
import static com.googlecode.cchlib.i18n.unit.parts.I18nDefaultTest.INIT_myJTabbedPane1;
import static com.googlecode.cchlib.i18n.unit.parts.I18nDefaultTest.INIT_myJTabbedPane2;
import static com.googlecode.cchlib.i18n.unit.parts.I18nDefaultTest.INIT_myTitledBorder;
import static com.googlecode.cchlib.i18n.unit.parts.I18nDefaultTest.LOCALIZED_FIELDS;
import static org.fest.assertions.api.Assertions.assertThat;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.i18n.unit.REF;
import com.googlecode.cchlib.i18n.unit.TestReference;

public class I18nDefaultPart
    extends JPanel
        implements I18nAutoUpdatable, TestReference
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( I18nDefaultPart.class );

    private final JLabel        myJLabel;
    private final JButton       myJButton;
    private final JCheckBox     myJCheckBox;
    private final JTabbedPane   myJTabbedPane;
    private final JPanel        panel1;
    private final JPanel        panel2;
    private final TitledBorder  myTitledBorder;

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

    @Override // I18nAutoUpdatable
    public void performeI18n( final AutoI18n autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }

    @Override // TestReference
    public void beforePerformeI18nTest()
    {
        assertThat( this.myJLabel.getText() ).isEqualTo( INIT_myJLabel );
        assertThat( this.myJButton.getText() ).isEqualTo( INIT_myJButton );
        assertThat( this.myJCheckBox.getText() ).isEqualTo( INIT_myJCheckBox );

        assertThat( this.myJTabbedPane.getTabCount() ).isEqualTo( 2 );
        assertThat( this.myJTabbedPane.getTitleAt( 0 ) ).isEqualTo( INIT_myJTabbedPane1 );
        assertThat( this.myJTabbedPane.getTitleAt( 1 ) ).isEqualTo( INIT_myJTabbedPane2 );

        assertThat( this.myTitledBorder.getTitle() ).isEqualTo( INIT_myTitledBorder );
    }

    @Override // TestReference
    public void afterPerformeI18nTest_WithValidBundle()
    {
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
