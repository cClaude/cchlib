package com.googlecode.cchlib.i18n.unit.parts;

import org.junit.Test;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.i18n.unit.util.I18nITBase;

public class I18nBaseNamePartTest extends I18nITBase
{
    @Test
    public void testI18n_WithValidBundle_I18nBaseNamePart()
    {
        final I18nBaseNamePart part = new I18nBaseNamePart();

        // Apply tests
        runI18nTests_WithValidBundle( part );
    }

    @Test
    public void testI18n_WithNotValidBundle_I18nBaseNamePart()
    {
        final I18nBaseNamePart part = new I18nBaseNamePart();


        runI18nTests_WithNotValidBundle( part );
    }

    @Test
    public void testI18nResourceBuilder_WithValidBundle_I18nBaseNamePart()
    {
        final I18nBaseNamePart part = new I18nBaseNamePart();

        // Invoke I18nResourceBuilder
        final I18nResourceBuilderResult result = do_I18nResourceBuilder_WithValidBundle( part );

        // Apply tests
        runI18nResourceBuilderTests_WithValidBundle( part, result );
    }

    @Test
    public void testI18nResourceBuilder_WithNotValidBundle_I18nBaseNamePart()
    {
        final I18nBaseNamePart part = new I18nBaseNamePart();

        final I18nResourceBuilderResult result = do_I18nResourceBuilder_WithNotValidBundle( part );

        runI18nResourceBuilderTests_WithNotValidBundle( part, result );
    }
}

