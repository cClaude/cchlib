package com.googlecode.cchlib.i18n.unit.util;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.AutoI18nCoreFactory;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilder;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderFactory;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleNameFactory;
import com.googlecode.cchlib.i18n.unit.REF;

public abstract class I18nITBaseConfig
{
    protected void do_performeI18n_WithValidBundle( final I18nAutoCoreUpdatable part )
    {
        final AutoI18nCore autoI18n = newAutoI18nCoreWithValidBundle();

        do_performeI18n( part, autoI18n );
    }

    protected void do_performeI18n_WithNotValidBundle( final I18nAutoCoreUpdatable part )
    {
        final AutoI18nCore autoI18n = newAutoI18nCoreWithExistingButNotValidBundle();

        do_performeI18n( part, autoI18n );
    }

    protected I18nResourceBuilderResult do_I18nResourceBuilder_WithValidBundle(
        final I18nAutoCoreUpdatable part
        )
    {
        final I18nResourceBuilder builder = I18nResourceBuilderFactory.newI18nResourceBuilder(
                newAutoI18nCoreWithValidBundle()
                );

        builder.append( part );

        return builder.getResult();
    }

    protected I18nResourceBuilderResult do_I18nResourceBuilder_WithNotValidBundle(
        final I18nAutoCoreUpdatable part
        )
    {
        final I18nResourceBuilder builder = I18nResourceBuilderFactory.newI18nResourceBuilder(
                newAutoI18nCoreWithExistingButNotValidBundle()
                );

        builder.append( part );
        return builder.getResult();
    }

    private void do_performeI18n( final I18nAutoCoreUpdatable part, final AutoI18nCore autoI18n )
    {
        part.performeI18n( autoI18n );
    }

    public final AutoI18nCore newAutoI18nCoreWithValidBundle()
    {
        return AutoI18nCoreFactory.newAutoI18nCore(
                getConfig(),
                newI18nResourceBundleName_WithValidBundle(),
                getLocale()
                );
    }

    public final AutoI18nCore newAutoI18nCoreWithExistingButNotValidBundle()
    {
        return AutoI18nCoreFactory.newAutoI18nCore(
                getConfig(),
                newI18nResourceBundleName_WithExistingButNotValidBundle(),
                getLocale()
                );
    }

    public final I18nResourceBundleName newI18nResourceBundleName_WithValidBundle()
    {
        return I18nResourceBundleNameFactory.newI18nResourceBundleName(
                REF.class.getPackage(),
                REF.class.getSimpleName()
                );
    }

    public final I18nResourceBundleName newI18nResourceBundleName_WithExistingButNotValidBundle()
    {
        return I18nResourceBundleNameFactory.newI18nResourceBundleName(
                REF.class.getPackage(),
                REF.class.getSimpleName() + "-empty"
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
