package com.googlecode.cchlib.i18n.unit.parts;

import static org.fest.assertions.api.Assertions.assertThat;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.annotation.I18nToolTipText;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.unit.PrepTestPart;
import com.googlecode.cchlib.i18n.unit.TestReference;
import com.googlecode.cchlib.i18n.unit.util.TestUtils;

public class I18nToolTipText_for_JTabbedPanePart implements TestReference, I18nAutoCoreUpdatable
{
    private static final Logger LOGGER = Logger.getLogger( I18nToolTipTextIgnorePart.class );

    @I18nIgnore private static final String TIP1 = "Tool tip text 1";
    @I18nIgnore private static final String TIP2 = "Tool tip text 2";
    @I18nIgnore private static final String TIP3 = "Tool tip text 3";
    @I18nIgnore private static final String TIP4 = "Tool tip text 4";

    @I18nIgnore private static final String TITLE1 = "Title 1";
    @I18nIgnore private static final String TITLE2 = "Title 2";
    @I18nIgnore private static final String TITLE3 = "Title 3";
    @I18nIgnore private static final String TITLE4 = "Title 4";

    @I18nIgnore private static final String TITLE1_I18N = "OK(Title 1)";
    @I18nIgnore private static final String TITLE2_I18N = "OK(Title 2)";
    @I18nIgnore private static final String TITLE3_I18N = "OK(Title 3)";
    @I18nIgnore private static final String TITLE4_I18N = "OK(Title 4)";

    private static final String TIP1_I18N = "OK(Tool tip text 1)";
    private static final String TIP2_I18N = "OK(Tool tip text 2)";
    private static final String TIP3_I18N = "OK(Tool tip text 3)";
    private static final String TIP4_I18N = "OK(Tool tip text 4)";

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
    public void performeI18n( final AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }

    @Override
    public void beforePrepTest( final PrepTestPart prepTest )
    {
        TestUtils.preparePrepTest( prepTest, this );
    }

    @Override
    public void afterPrepTest()
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
    public void performeI18n()
    {
        afterPrepTest();

        for( int i = 0; i<4; i++ ) {
            LOGGER.info( "before contentJTabbedPane.getTitleAt( " + i + " ) =" + this.myJTabbedPane.getTitleAt( i ) );
            LOGGER.info( "before contentJTabbedPane.getToolTipTextAt( " + i + " ) =" + this.myJTabbedPane.getToolTipTextAt( i ) );
            }

        TestUtils.performeI18n( this );

        assertThat( this.myJTabbedPane.getTabCount() ).isEqualTo( 4 );

        for( int i = 0; i<4; i++ ) {
            LOGGER.info( "after contentJTabbedPane.getTitleAt( " + i + " ) =" + this.myJTabbedPane.getTitleAt( i ) );
            LOGGER.info( "after contentJTabbedPane.getToolTipTextAt( " + i + " ) =" + this.myJTabbedPane.getToolTipTextAt( i ) );
            }

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
    public int getSyntaxeExceptionCount()
    {
        return 0;
    }

    @Override
    public int getMissingResourceExceptionCount()
    {
        return 1 /*myJTabbedPane 1st title */ + 1 /* myJTabbedPane 1st tool tip text */;
    }
}
