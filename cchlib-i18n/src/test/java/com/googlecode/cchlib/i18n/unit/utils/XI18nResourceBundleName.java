package com.googlecode.cchlib.i18n.unit.utils;

import java.util.Locale;
import java.util.ResourceBundle;
import com.googlecode.cchlib.i18n.prep.I18nPrepHelper;
import com.googlecode.cchlib.i18n.resources.DefaultI18nResourceBundleName;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;
import com.googlecode.cchlib.i18n.resources.I18nSimpleResourceBundle;

public class XI18nResourceBundleName
    extends DefaultI18nResourceBundleName
        implements I18nResourceBundleName
{
    private static final long serialVersionUID = 1L;
    
    private Package packageMessageBundleBase;
    private String messageBundleBaseName;

    public XI18nResourceBundleName(
        Package packageMessageBundleBase,
        String  messageBundleBaseName
        )
    {
        super( packageMessageBundleBase, messageBundleBaseName );

        this.packageMessageBundleBase = packageMessageBundleBase;
        this.messageBundleBaseName = messageBundleBaseName;
    }

    public XI18nResourceBundleName(
        Class<?> packageMessageBundleBase,
        String   messageBundleBaseName 
        )
    {
        this( packageMessageBundleBase.getPackage(), messageBundleBaseName );
    }

    public XI18nResourceBundleName( Class<?> clazz )
    {
        this( clazz.getPackage(), clazz.getSimpleName() );
    }

    public XI18nResourceBundleName( Package packageMessageBundleBase )
    {
        this( packageMessageBundleBase, I18nPrepHelper.DEFAULT_MESSAGE_BUNDLE_BASENAME );
    }

    public Package getPackageMessageBundleBase()
    {
        return packageMessageBundleBase;
    }

    public String getMessageBundleBaseName()
    {
        return messageBundleBaseName;
    }
    
    private I18nSimpleResourceBundle createI18nSimpleResourceBundle(Locale locale)
    {
        return new I18nSimpleResourceBundle( locale, this );
    }
    
    public ResourceBundle createResourceBundle(Locale locale)
    {
        return createI18nSimpleResourceBundle( locale ).getResourceBundle();
    }
}
