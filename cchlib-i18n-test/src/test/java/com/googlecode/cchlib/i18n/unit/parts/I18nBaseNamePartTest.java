package com.googlecode.cchlib.i18n.unit.parts;

import org.junit.Assume;
import org.junit.Test;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.i18n.unit.util.I18nITBase;
import com.googlecode.cchlib.swing.SafeSwingUtilities;

public class I18nBaseNamePartTest extends I18nITBase
{
    static final String INIT_myString1           = "this is my String 1";
    static final String DEFAULT_BUNDLE_myString1 = "OK(myString1)";

    static final String INIT_myString2           = "this is my String 2";
    static final String DEFAULT_BUNDLE_myString2 = "OK(myString2)";

    static final String INIT_myJLabel1           = "my JLabel 1 text";
    static final String DEFAULT_BUNDLE_myJLabel1 = "OK(myJLabel1)";

    static final String INIT_myJLabel2           = "my JLabel 2 text";
    static final String DEFAULT_BUNDLE_myJLabel2 = "OK(myJLabel2)";

    static final int LOCALIZED_FIELDS = 4;
    static final int IGNORED_FIELDS   = 1;

    @Test
    public void testI18n_WithValidBundle_I18nBaseNamePart()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final I18nBaseNamePart part = new I18nBaseNamePart();

        // Apply tests
        runI18nTests_WithValidBundle( part );
    }

    @Test
    public void testI18n_WithNotValidBundle_I18nBaseNamePart()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final I18nBaseNamePart part = new I18nBaseNamePart();

        runI18nTests_WithNotValidBundle( part );
    }

    @Test
    public void testI18nResourceBuilder_WithValidBundle_I18nBaseNamePart()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final I18nBaseNamePart part = new I18nBaseNamePart();

        // Invoke I18nResourceBuilder
        final I18nResourceBuilderResult result = do_I18nResourceBuilder_WithValidBundle( part );

        // Apply tests
        runI18nResourceBuilderTests_WithValidBundle( part, result );
    }

    @Test
    public void testI18nResourceBuilder_WithNotValidBundle_I18nBaseNamePart()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final I18nBaseNamePart          part   = new I18nBaseNamePart();
        final I18nResourceBuilderResult result = do_I18nResourceBuilder_WithNotValidBundle( part );

        runI18nResourceBuilderTests_WithNotValidBundle( part, result );
    }
}

