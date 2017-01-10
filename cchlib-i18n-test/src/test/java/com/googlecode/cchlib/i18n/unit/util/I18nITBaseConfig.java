package com.googlecode.cchlib.i18n.unit.util;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.core.AutoI18n;
import com.googlecode.cchlib.i18n.core.AutoI18nFactory;
import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilder;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderFactory;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundle;
import com.googlecode.cchlib.i18n.resources.I18nResourceFactory;
import com.googlecode.cchlib.i18n.unit.REF;

public abstract class I18nITBaseConfig
{
    protected void do_performeI18n_WithValidBundle( final I18nAutoUpdatable part )
    {
        final AutoI18n autoI18n = newAutoI18nWithValidBundle();

        do_performeI18n( part, autoI18n );
    }

    protected void do_performeI18n_WithNotValidBundle( final I18nAutoUpdatable part )
    {
        final AutoI18n autoI18n = newAutoI18nWithExistingButNotValidBundle();

        do_performeI18n( part, autoI18n );
    }

    protected I18nResourceBuilderResult do_I18nResourceBuilder_WithValidBundle(
        final I18nAutoUpdatable part
        )
    {
        final I18nResourceBuilder builder = I18nResourceBuilderFactory.newI18nResourceBuilder(
                newAutoI18nWithValidBundle()
                );

        builder.append( part );

        return builder.getResult();
    }

    protected I18nResourceBuilderResult do_I18nResourceBuilder_WithNotValidBundle(
        final I18nAutoUpdatable part
        )
    {
        final I18nResourceBuilder builder = I18nResourceBuilderFactory.newI18nResourceBuilder(
                newAutoI18nWithExistingButNotValidBundle()
                );

        builder.append( part );

        return builder.getResult();
    }

    private void do_performeI18n( final I18nAutoUpdatable part, final AutoI18n autoI18n )
    {
        part.performeI18n( autoI18n );
    }

    public final AutoI18n newAutoI18nWithValidBundle()
    {
        return AutoI18nFactory.newAutoI18n(
                getConfig(),
                newI18nResourceBundleName_WithValidBundle()
                );
    }

    public final AutoI18n newAutoI18nWithExistingButNotValidBundle()
    {
        return AutoI18nFactory.newAutoI18n(
                getConfig(),
                newI18nResourceBundleName_WithExistingButNotValidBundle()
                );
    }

    public final I18nResourceBundle newI18nResourceBundleName_WithValidBundle()
    {
        return I18nResourceFactory.newI18nResourceBundle(
                REF.class.getPackage(),
                REF.class.getSimpleName(),
                getLocale()
                );
    }

    public final I18nResourceBundle newI18nResourceBundleName_WithExistingButNotValidBundle()
    {
        return I18nResourceFactory.newI18nResourceBundle(
                REF.class.getPackage(),
                REF.class.getSimpleName() + "-empty",
                getLocale()
                );
    }

    public final Locale getLocale()
    {
        return Locale.ENGLISH;
    }

    public final Set<AutoI18nConfig> getConfig()
    {
        // unmodifiable is not required by API,
        // but API must not modify configuration set.
        return Collections.unmodifiableSet(
                EnumSet.of( AutoI18nConfig.PRINT_STACKTRACE_IN_LOGS )
                );
    }
}
