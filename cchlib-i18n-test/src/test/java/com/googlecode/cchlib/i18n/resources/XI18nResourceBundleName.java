package com.googlecode.cchlib.i18n.resources;

import java.util.Locale;
import java.util.ResourceBundle;

public class XI18nResourceBundleName implements I18nResourceBundleName
{
    private final String  name;
    private final Package packageMessageBundleBase;
    private final String  messageBundleBaseName;

    public XI18nResourceBundleName(
        final Package packageMessageBundleBase,
        final String  messageBundleBaseName
        )
    {
        this.name = I18nResourceBundleNameFactory.newI18nResourceBundleName(
                packageMessageBundleBase,
                messageBundleBaseName
                ).getName();
        this.packageMessageBundleBase = packageMessageBundleBase;
        this.messageBundleBaseName    = messageBundleBaseName;
    }

    public XI18nResourceBundleName(
        final Class<?> packageMessageBundleBase,
        final String   messageBundleBaseName
        )
    {
        this( packageMessageBundleBase.getPackage(), messageBundleBaseName );
    }

    public XI18nResourceBundleName( final Class<?> clazz )
    {
        this( clazz.getPackage(), clazz.getSimpleName() );
    }

    public XI18nResourceBundleName( final Package packageMessageBundleBase )
    {
        this( packageMessageBundleBase, I18nResourceBundleNameFactory.DEFAULT_MESSAGE_BUNDLE_BASENAME );
    }

    public Package getPackageMessageBundleBase()
    {
        return this.packageMessageBundleBase;
    }

    public String getMessageBundleBaseName()
    {
        return this.messageBundleBaseName;
    }

    private I18nSimpleResourceBundle createI18nSimpleResourceBundle( final Locale locale )
    {
        return new I18nSimpleResourceBundle( this, locale );
    }

    public ResourceBundle createResourceBundle( final Locale locale )
    {
        return createI18nSimpleResourceBundle( locale ).getResourceBundle();
    }

    @Override
    public String getName()
    {
        return this.name;
    }
}
