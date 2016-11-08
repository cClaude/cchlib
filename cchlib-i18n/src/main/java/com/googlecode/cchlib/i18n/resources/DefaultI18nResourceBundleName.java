package com.googlecode.cchlib.i18n.resources;

import java.io.Serializable;

/**
 * Default implementation of {@link I18nResourceBundleName}
 */
public class DefaultI18nResourceBundleName
    implements I18nResourceBundleName, Serializable
{
    private static final long serialVersionUID = 2L;

    /** Default name for messages bundle : {@value} */
    public static final String DEFAULT_MESSAGE_BUNDLE_BASENAME = "MessagesBundle";

    private String name;

    /**
     * Build a DefaultI18nResourceBundleName based on package name and on a simple name
     *
     * @param packageMessageBundleBase package to use
     * @param messageBundleBaseName    simple name to use
     */
    public DefaultI18nResourceBundleName(
        final Package packageMessageBundleBase,
        final String  messageBundleBaseName
        )
    {
        this.name = packageMessageBundleBase.getName() + '.' + messageBundleBaseName;
    }

    /**
     * Build a DefaultI18nResourceBundleName based on package name of a class and on a simple name
     *
     * @param clazz                  class to use
     * @param messageBundleBaseName  simple name to use
     */
    public DefaultI18nResourceBundleName(
        final Class<?> clazz,
        final String   messageBundleBaseName
        )
    {
        this( clazz.getPackage(), messageBundleBaseName );
    }

    /**
     * Build a DefaultI18nResourceBundleName based on a full class name
     *
     * @param clazz class to use
     */
    public DefaultI18nResourceBundleName(
        final Class<?> clazz
        )
    {
        this( clazz.getPackage(), clazz.getSimpleName() );
    }

    /**
     * Build a DefaultI18nResourceBundleName based on package name and
     * {@link #DEFAULT_MESSAGE_BUNDLE_BASENAME} value
     *
     * @param packageMessageBundleBase package to use
     */
    public DefaultI18nResourceBundleName( final Package packageMessageBundleBase )
    {
       this( packageMessageBundleBase, DEFAULT_MESSAGE_BUNDLE_BASENAME );
    }

    @Override
    public String getName()
    {
        return name;
    }
}
