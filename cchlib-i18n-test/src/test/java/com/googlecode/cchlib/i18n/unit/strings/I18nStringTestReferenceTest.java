package com.googlecode.cchlib.i18n.unit.strings;

import org.junit.Test;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderHelper;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.i18n.unit.util.I18nITBase;

public class I18nStringTestReferenceTest extends I18nITBase
{
    static final String INIT_myString           = "my-string-text-1 (noI18n)";
    static final String DEFAULT_BUNDLE_myString = "OK(myString)";

    static final String INIT_myGlobalStringID1           = "my Global string 1 text (noI18n)";
    static final String DEFAULT_BUNDLE_myGlobalStringID1 = "OK(I18nStringTestContener_GlobalStringID1)";

    static final String INIT_myGlobalStringIDMethod2           = "my Global string 3 text (noI18n)";
    static final String DEFAULT_BUNDLE_myGlobalStringIDMethod2 = "OK(myGlobalStringIDMethod2)";

    static final int LOCALIZED_FIELDS = 2;
    static final int IGNORED_FIELDS   = 2;

    @Test
    public void testI18n_WithValidBundle_I18nStringTestReference()
    {
        final I18nStringTestReference part = new I18nStringTestReference();

        // Apply tests
        runI18nTests_WithValidBundle( part );
    }

    @Test
    public void testI18n_WithNotValidBundle_I18nStringTestReference()
    {
        final I18nStringTestReference part = new I18nStringTestReference();

        runI18nTests_WithNotValidBundle( part );
    }

    @Test
    public void testI18nResourceBuilder_WithValidBundle_I18nStringTestReference()
    {
        final I18nStringTestReference part = new I18nStringTestReference();

        // Invoke I18nResourceBuilder
        final I18nResourceBuilderResult result = do_I18nResourceBuilder_WithValidBundle( part );

        I18nResourceBuilderHelper.fmtLocalizedFields( System.out, result );
        I18nResourceBuilderHelper.fmtIgnoredFields( System.err, result );

        // Apply tests
        runI18nResourceBuilderTests_WithValidBundle( part, result );
    }

    @Test
    public void testI18nResourceBuilder_WithNotValidBundle_I18nStringTestReference()
    {
        final I18nStringTestReference part = new I18nStringTestReference();

        final I18nResourceBuilderResult result = do_I18nResourceBuilder_WithNotValidBundle( part );

        runI18nResourceBuilderTests_WithNotValidBundle( part, result );
    }

    @Test
    public void testI18n_WithValidBundle_I18nStringTestReference_OLD()
    {
        final I18nStringTestReference part = new I18nStringTestReference();
        part.performeI18n();
    }
}

