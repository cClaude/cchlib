package com.googlecode.cchlib.i18n.config;

import java.util.EnumSet;
import java.util.Locale;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.AutoI18nCoreFactory;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;
import com.googlecode.cchlib.i18n.resources.I18nSimpleResourceBundle;
import java.util.EnumSet;
import java.util.Locale;

/**
 * @deprecated this class remain for old projects based on previous version of cchlib-i18n
 */
@Deprecated
public class DefaultI18nBundleFactory
{
    private final AutoI18nCore autoI18n;

    private DefaultI18nBundleFactory(final Locale locale, final I18nPrepHelperAutoUpdatable prep)
    {
        final EnumSet<AutoI18nConfig> config = null; // default config

        autoI18n = AutoI18nCoreFactory.createAutoI18nCore(
                config,
                new I18nSimpleResourceBundle(
                        locale,
                        new I18nResourceBundleName() {
                            @Override
                            public String getName()
                            {
                                return prep.getMessagesBundleForI18nPrepHelper(); // $codepro.audit.disable deprecatedMethod
                            }}
                        )
                );
    }

    public static <T> DefaultI18nBundleFactory createDefaultI18nBundle( final Locale locale, final I18nPrepHelperAutoUpdatable prep )
    {
        if( locale == null ) {
            return new DefaultI18nBundleFactory( Locale.getDefault(), prep );
        } else {
            return new DefaultI18nBundleFactory( locale, prep );
        }
    }

    public AutoI18nCore getAutoI18n()
    {
        return autoI18n;
    }
}
