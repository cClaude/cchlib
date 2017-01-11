package com.googlecode.cchlib.apps.editresourcesbundle;

import java.util.Locale;
import java.util.Set;
import com.googlecode.cchlib.apps.editresourcesbundle.compare.CompareResourcesBundleFrame;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.api.I18nResource;
import com.googlecode.cchlib.i18n.core.AutoI18nFactory;
import com.googlecode.cchlib.i18n.resources.I18nResourceFactory;

/**
 * Application launcher.
 */
public class EditResourcesBundleApp
{
    private EditResourcesBundleApp()
    {
        // App
    }

    /**
     * Launch application
     *
     * @param args CLI parameters (ignored)
     */
    public static void main( final String[] args )
    {
        CompareResourcesBundleFrame.main(/* args */);
    }

    public static I18nResource getI18nResource( final Locale locale )
    {
        final Locale i18nLocale;

        if( locale == null ) {
            i18nLocale = Locale.getDefault();
            }
        else {
            i18nLocale = locale;
        }

        return I18nResourceFactory.newI18nResourceBundle(
                getReferencePackage(),
                i18nLocale
                );
    }

    public static Set<AutoI18nConfig> getConfig()
    {
        return AutoI18nConfig.newAutoI18nConfig( AutoI18nConfig.DO_DEEP_SCAN );
    }

    public static AutoI18n newAutoI18n(final Locale locale )
    {
        return AutoI18nFactory.newAutoI18n(
                EditResourcesBundleApp.getConfig(),
                EditResourcesBundleApp.getI18nResource( locale )
                );
    }

    public static Package getReferencePackage()
    {
        return EditResourcesBundleApp.class.getPackage();
    }
}
