package com.googlecode.cchlib.i18n.unit.utils;

import java.util.Locale;
import java.util.ResourceBundle;

import com.googlecode.cchlib.i18n.resources.DefaultI18nResourceBundleName;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;
import com.googlecode.cchlib.i18n.resources.I18nSimpleResourceBundle;

public class XI18nResourceBundleName
    extends DefaultI18nResourceBundleName
        implements I18nResourceBundleName
{
    private static final long serialVersionUID = 1L;

    private final Package packageMessageBundleBase;
    private final String messageBundleBaseName;

    public XI18nResourceBundleName(
        final Package packageMessageBundleBase,
        final String  messageBundleBaseName
        )
    {
        super( packageMessageBundleBase, messageBundleBaseName );

        this.packageMessageBundleBase = packageMessageBundleBase;
        this.messageBundleBaseName = messageBundleBaseName;
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
        this( packageMessageBundleBase, DefaultI18nResourceBundleName.DEFAULT_MESSAGE_BUNDLE_BASENAME );
    }

    public Package getPackageMessageBundleBase()
    {
        return packageMessageBundleBase;
    }

    public String getMessageBundleBaseName()
    {
        return messageBundleBaseName;
    }

    private I18nSimpleResourceBundle createI18nSimpleResourceBundle(final Locale locale)
    {
        return new I18nSimpleResourceBundle( locale, this );
    }

    public ResourceBundle createResourceBundle(final Locale locale)
    {
        return createI18nSimpleResourceBundle( locale ).getResourceBundle();
    }
}
