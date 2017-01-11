package com.googlecode.cchlib.i18n.unit.strings.errors;

import org.junit.Test;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderHelper;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.i18n.unit.util.I18nITBase;

public class I18nStringWithErrorsTestReferenceTest extends I18nITBase
{
    static final String INIT_myGlobalStringIDMethod1           = "myGlobalStringIDMethod1 NoI18n";
    static final String DEFAULT_BUNDLE_myGlobalStringIDMethod1 = "I18nDone: myGlobalStringIDMethod1";

    static final int LOCALIZED_FIELDS = 0;
    static final int IGNORED_FIELDS   = 1;

    @Test
    public void testI18n_WithValidBundle_I18nStringWithErrorsTestReference()
    {
        final I18nStringWithErrorsTestReference part = new I18nStringWithErrorsTestReference();

        // Apply tests
        runI18nTests_WithValidBundle( part );
    }

    @Test
    public void testI18n_WithNotValidBundle_I18nStringWithErrorsTestReference()
    {
        final I18nStringWithErrorsTestReference part = new I18nStringWithErrorsTestReference();

        runI18nTests_WithNotValidBundle( part );
    }

    @Test
    public void testI18nResourceBuilder_WithValidBundle_I18nStringWithErrorsTestReference()
    {
        final I18nStringWithErrorsTestReference part = new I18nStringWithErrorsTestReference();

        // Invoke I18nResourceBuilder
        final I18nResourceBuilderResult result = do_I18nResourceBuilder_WithValidBundle( part );

        I18nResourceBuilderHelper.fmtLocalizedFields( System.out, result );
        I18nResourceBuilderHelper.fmtIgnoredFields( System.err, result );

        // Apply tests
        runI18nResourceBuilderTests_WithValidBundle( part, result );
    }

    @Test
    public void testI18nResourceBuilder_WithNotValidBundle_I18nStringWithErrorsTestReference()
    {
        final I18nStringWithErrorsTestReference part = new I18nStringWithErrorsTestReference();

        final I18nResourceBuilderResult result = do_I18nResourceBuilder_WithNotValidBundle( part );

        runI18nResourceBuilderTests_WithNotValidBundle( part, result );
    }
}
