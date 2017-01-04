package com.googlecode.cchlib.i18n.resourcebuilder;

import java.util.Locale;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.internal.AutoI18nCoreImpl;

public class I18nResourceBuilderFactory
{
    private I18nResourceBuilderFactory()
    {
        // All static
    }

    /**
     * Create a new {@link I18nResourceBuilder}
     *
     * @param autoI18nCore
     *            The {@link AutoI18nCore} configuration to use to
     *            internationalize the application.
     * @param locale
     *            Locale to use for resource builder process.
     * @param configExtension
     *            Allow to extend configuration for this process.
     * @return the {@link I18nResourceBuilder}
     * @throws UnsupportedOperationException
     *             if {@link AutoI18nCore} implementation is not supported.
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public static I18nResourceBuilder newI18nResourceBuilder(
        final AutoI18nCore     autoI18nCore,
        final Locale           locale,
        final AutoI18nConfig...configExtension
        ) throws UnsupportedOperationException
    {
        if( autoI18nCore instanceof AutoI18nCoreImpl ) {
            return new I18nResourceBuilderAutoI18nCoreImpl(
                    (AutoI18nCoreImpl)autoI18nCore,
                    locale,
                    configExtension
                    );
        } else {
            throw new UnsupportedOperationException(
                "Dont kwnon how to handle: " + autoI18nCore.getClass()
                );
        }
    }
}
