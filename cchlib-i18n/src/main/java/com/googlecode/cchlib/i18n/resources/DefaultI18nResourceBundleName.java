package com.googlecode.cchlib.i18n.resources;

import java.io.Serializable;
import com.googlecode.cchlib.i18n.prep.I18nPrepHelper;

public class DefaultI18nResourceBundleName 
    implements I18nResourceBundleName, Serializable 
{
    private static final long serialVersionUID = 1L;
    private String name;

    public DefaultI18nResourceBundleName(
        final Package packageMessageBundleBase,
        final String  messageBundleBaseName
        )
    {
        this.name = packageMessageBundleBase.getName() + '.' + messageBundleBaseName;
    }
    
    public DefaultI18nResourceBundleName(
        final Class<?> packageMessageBundleBase,
        final String   messageBundleBaseName
        )
    {
        this( packageMessageBundleBase.getPackage(), messageBundleBaseName );
    }

    public DefaultI18nResourceBundleName(
        final Class<?> clazz
        )
    {
        this( clazz.getPackage(), clazz.getSimpleName() );
    }

    public DefaultI18nResourceBundleName( Package packageMessageBundleBase )
    {
       this( packageMessageBundleBase, I18nPrepHelper.DEFAULT_MESSAGE_BUNDLE_BASENAME );
    }

    @Override
    public String getName()
    {
        return name;
    }
}
