package com.googlecode.cchlib.apps.editresourcesbundle;

import java.util.Locale;
import java.util.Set;
import com.googlecode.cchlib.apps.editresourcesbundle.compare.CompareResourcesBundleFrame;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.resources.DefaultI18nResourceBundleName;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;
import com.googlecode.cchlib.i18n.resources.I18nSimpleResourceBundle;

/**
 * Application launcher.
 */
public class EditResourcesBundleApp
{
    /**
    * Launch application
    */
    public static void main( final String[] args )
    {
        CompareResourcesBundleFrame.main(/* args */);
    }

    public static I18nResourceBundleName getI18nResourceBundleName()
    {
        return new DefaultI18nResourceBundleName( EditResourcesBundleApp.class.getPackage() );
    }

    public static I18nSimpleResourceBundle getI18nSimpleResourceBundle( Locale locale )
    {
        if( locale == null ) {
            locale = Locale.getDefault();
            }
        return new I18nSimpleResourceBundle( locale, EditResourcesBundleApp.getI18nResourceBundleName() );
    }

    public static Set<AutoI18nConfig> getConfig()
    {
        return AutoI18nConfig.newAutoI18nConfig( AutoI18nConfig.DO_DEEP_SCAN );
    }
}