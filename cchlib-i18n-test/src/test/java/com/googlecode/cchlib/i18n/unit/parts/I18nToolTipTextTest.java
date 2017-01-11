package com.googlecode.cchlib.i18n.unit.parts;

import org.junit.Assume;
import org.junit.Test;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.i18n.unit.util.I18nITBase;
import com.googlecode.cchlib.swing.SafeSwingUtilities;

public class I18nToolTipTextTest extends I18nITBase
{
    static final String TOOLTIPTEXT_INIT           = "my tool tip text 1";
    static final String TOOLTIPTEXT_DEFAULT_BUNDLE = "OK(ToolTipText)";

    static final String TEXT_INIT           = "my button with tool tip text 1";
    static final String TEXT_DEFAULT_BUNDLE = "OK(myButtonWithToolTipText1)";

    static final int LOCALIZED_FIELDS = 2;
    static final int IGNORED_FIELDS   = 0;

    @Test
    public void testI18n_WithValidBundle_I18nToolTipTextPart()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final I18nToolTipTextPart part = new I18nToolTipTextPart();

        // Apply tests
        runI18nTests_WithValidBundle( part );
    }

    @Test
    public void testI18n_WithNotValidBundle_I18nToolTipTextPart()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final I18nToolTipTextPart part = new I18nToolTipTextPart();

        runI18nTests_WithNotValidBundle( part );
    }

    @Test
    public void testI18nResourceBuilder_WithValidBundle_I18nToolTipTextPart()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final I18nToolTipTextPart part = new I18nToolTipTextPart();

        // Invoke I18nResourceBuilder
        final I18nResourceBuilderResult result = do_I18nResourceBuilder_WithValidBundle( part );

        // Apply tests
        runI18nResourceBuilderTests_WithValidBundle( part, result );
    }

    @Test
    public void testI18nResourceBuilder_WithNotValidBundle_I18nToolTipTextPart()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final I18nToolTipTextPart       part   = new I18nToolTipTextPart();
        final I18nResourceBuilderResult result = do_I18nResourceBuilder_WithNotValidBundle( part );

        runI18nResourceBuilderTests_WithNotValidBundle( part, result );
    }
}

