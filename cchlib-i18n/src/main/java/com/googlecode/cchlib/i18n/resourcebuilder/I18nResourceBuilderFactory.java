package com.googlecode.cchlib.i18n.resourcebuilder;

import java.util.Locale;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.core.AutoI18n;
import com.googlecode.cchlib.i18n.core.AutoI18nFactory;
import com.googlecode.cchlib.i18n.core.internal.AutoI18nImpl;

public class I18nResourceBuilderFactory
{
    private I18nResourceBuilderFactory()
    {
        // All static
    }

    /**
     * Create a new {@link I18nResourceBuilder}
     *
     * @param autoI18n
     *            The {@link AutoI18n} configuration to use to
     *            internationalize the application.
     * @param locale
     *            Locale to use for resource builder process.
     * @param configExtension
     *            Allow to extend configuration for this process.
     * @return the {@link I18nResourceBuilder}
     * @throws UnsupportedOperationException
     *             if {@link AutoI18n} implementation is not supported.
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public static I18nResourceBuilder newI18nResourceBuilder(
        final AutoI18n         autoI18n,
        final Locale           locale,
        final AutoI18nConfig...configExtension
        ) throws UnsupportedOperationException
    {
        if( autoI18n instanceof AutoI18nImpl ) {
            return new I18nResourceBuilderAutoI18nImpl(
                    (AutoI18nImpl)autoI18n,
                    locale,
                    configExtension
                    );
        } else {
            throw new UnsupportedOperationException(
                "Dont kwnon how to handle: " + autoI18n.getClass()
                );
        }
    }

    /**
     * Create a new {@link I18nResourceBuilder}
     *
     * @param autoI18n
     *            The {@link AutoI18n} configuration to use to
     *            internationalize the application.
     * @param configExtension
     *            Allow to extend configuration for this process.
     * @return the {@link I18nResourceBuilder}
     * @throws UnsupportedOperationException
     *             if {@link AutoI18n} implementation is not supported.
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public static I18nResourceBuilder newI18nResourceBuilder(
        final AutoI18n         autoI18n,
        final AutoI18nConfig...configExtension
        )
    {
        return newI18nResourceBuilder(
                autoI18n,
                AutoI18nFactory.DEFAULT_LOCALE,
                configExtension
                );
    }
}
