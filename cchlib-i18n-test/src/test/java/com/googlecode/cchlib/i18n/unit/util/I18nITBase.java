package com.googlecode.cchlib.i18n.unit.util;

import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.i18n.unit.TestReference;

public abstract class I18nITBase extends I18nITBaseConfig
{
    protected void runI18nTests_WithValidBundle( final TestReference part )
    {
        part.beforePerformeI18nTest();

       // Use I18nAutoUpdatable API to initialize part I18n
       do_performeI18n_WithValidBundle( part );

       part.afterPerformeI18nTest_WithValidBundle();
    }

    protected void runI18nResourceBuilderTests_WithValidBundle(
        final TestReference             part,
        final I18nResourceBuilderResult result
        )
    {
        // Should have same result.
        part.afterPerformeI18nTest_WithValidBundle();

        part.afterResourceBuilderTest_WithValidBundle( result );
    }

    protected void runI18nTests_WithNotValidBundle( final TestReference part )
    {
        part.beforePerformeI18nTest();

        // Use I18nAutoUpdatable API to initialize part I18n
        do_performeI18n_WithNotValidBundle( part );

        part.afterPerformeI18nTest_WithNotValidBundle();
    }

    protected void runI18nResourceBuilderTests_WithNotValidBundle(
        final TestReference             part,
        final I18nResourceBuilderResult result
        )
    {
        // Should have same result.
        part.afterPerformeI18nTest_WithNotValidBundle();

        part.afterResourceBuilderTest_WithNotValidBundle( result );
    }
}
