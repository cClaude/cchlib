package com.googlecode.cchlib.i18n.unit.parts;

import org.junit.Assume;
import org.junit.Test;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.i18n.unit.util.I18nITBase;
import com.googlecode.cchlib.swing.SafeSwingUtilities;

public class I18nToolTipText_for_JTabbedPaneTest extends I18nITBase
{
    static final String TIP1 = "Tool tip text 1";
    static final String TIP2 = "Tool tip text 2";
    static final String TIP3 = "Tool tip text 3";
    static final String TIP4 = "Tool tip text 4";

    static final String TIP1_I18N = "OK(Tool tip text 1)";
    static final String TIP2_I18N = "OK(Tool tip text 2)";
    static final String TIP3_I18N = "OK(Tool tip text 3)";
    static final String TIP4_I18N = "OK(Tool tip text 4)";

    static final String TITLE1 = "Title 1";
    static final String TITLE2 = "Title 2";
    static final String TITLE3 = "Title 3";
    static final String TITLE4 = "Title 4";

    static final String TITLE1_I18N = "OK(Title 1)";
    static final String TITLE2_I18N = "OK(Title 2)";
    static final String TITLE3_I18N = "OK(Title 3)";
    static final String TITLE4_I18N = "OK(Title 4)";

    static final int LOCALIZED_FIELDS = 8;
    static final int IGNORED_FIELDS   = 0;

    @Test
    public void testI18n_WithValidBundle_I18nToolTipText_for_JTabbedPanePart()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final I18nToolTipText_for_JTabbedPanePart part = new I18nToolTipText_for_JTabbedPanePart();

        // Apply tests
        runI18nTests_WithValidBundle( part );
    }

    @Test
    public void testI18n_WithNotValidBundle_I18nToolTipText_for_JTabbedPanePart()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final I18nToolTipText_for_JTabbedPanePart part = new I18nToolTipText_for_JTabbedPanePart();

        runI18nTests_WithNotValidBundle( part );
    }

    @Test
    public void testI18nResourceBuilder_WithValidBundle_I18nToolTipText_for_JTabbedPanePart()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final I18nToolTipText_for_JTabbedPanePart part = new I18nToolTipText_for_JTabbedPanePart();

        // Invoke I18nResourceBuilder
        final I18nResourceBuilderResult result = do_I18nResourceBuilder_WithValidBundle( part );

        // Apply tests
        runI18nResourceBuilderTests_WithValidBundle( part, result );
    }

    @Test
    public void testI18nResourceBuilder_WithNotValidBundle_I18nToolTipText_for_JTabbedPanePart()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final I18nToolTipText_for_JTabbedPanePart          part   = new I18nToolTipText_for_JTabbedPanePart();
        final I18nResourceBuilderResult result = do_I18nResourceBuilder_WithNotValidBundle( part );

        runI18nResourceBuilderTests_WithNotValidBundle( part, result );
    }
}

