package com.googlecode.cchlib.i18n.unit.parts;

import org.junit.Assume;
import org.junit.Test;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.i18n.unit.util.I18nITBase;
import com.googlecode.cchlib.swing.SafeSwingUtilities;

public class I18nToolTipTextIgnoreTest extends I18nITBase
{
    static final String TOOLTIPTEXT_INIT = "my tool tip text 1";
    static final String TOOLTIPTEXT_DEFAULT_BUNDLE = "OK(ToolTipText)";

    static final String TEXT_INIT = "my button with tool tip text 1";

    static final int LOCALIZED_FIELDS = 1;
    static final int IGNORED_FIELDS   = 1;

    @Test
    public void testI18n_WithValidBundle_I18nToolTipTextIgnorePart()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final I18nToolTipTextIgnorePart part = new I18nToolTipTextIgnorePart();

        // Apply tests
        runI18nTests_WithValidBundle( part );
    }

    @Test
    public void testI18n_WithNotValidBundle_I18nToolTipTextIgnorePart()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final I18nToolTipTextIgnorePart part = new I18nToolTipTextIgnorePart();

        runI18nTests_WithNotValidBundle( part );
    }

    @Test
    public void testI18nResourceBuilder_WithValidBundle_I18nToolTipTextIgnorePart()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final I18nToolTipTextIgnorePart part = new I18nToolTipTextIgnorePart();

        // Invoke I18nResourceBuilder
        final I18nResourceBuilderResult result = do_I18nResourceBuilder_WithValidBundle( part );

        // Apply tests
        runI18nResourceBuilderTests_WithValidBundle( part, result );
    }

    @Test
    public void testI18nResourceBuilder_WithNotValidBundle_I18nToolTipTextIgnorePart()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final I18nToolTipTextIgnorePart          part   = new I18nToolTipTextIgnorePart();
        final I18nResourceBuilderResult result = do_I18nResourceBuilder_WithNotValidBundle( part );

        runI18nResourceBuilderTests_WithNotValidBundle( part, result );
    }
}

