package com.googlecode.cchlib.i18n.resources;

import java.util.ResourceBundle;

/**
 * Allow to create {@link I18nResourceBundleName}
 * @deprecated use {@link I18nResourceFactory} or {@link ResourceBundleHelper} instead
 */
@Deprecated
public class I18nResourceBundleNameFactory
{
    private I18nResourceBundleNameFactory()
    {
        // All static
    }

    /**
     * Create a {@link I18nResourceBundleName} to access
     * to a resource bundle based on {@code referenceType} full name.
     *
     * @param referenceType
     *            Class support for resource bundle. This is basically a class
     *            in the same package than the {@link ResourceBundle} and with
     *            the same name.
     * @return an {@link I18nResourceBundleName} base on class package path.
     */
    public static I18nResourceBundleName newI18nResourceBundleName(
        final Class<?> referenceType
        )
    {
        return newI18nResourceBundleName(
            referenceType.getPackage(),
            referenceType.getSimpleName()
            );
    }

    /**
     * Create a {@link I18nResourceBundleName} based on package name of a
     * class and on a simple name
     *
     * @param referencePackage
     *            Package name to use for {@link ResourceBundle} access
     * @param messageBundleFilenameBase
     *            Filename prefix to use for {@link ResourceBundle}
     * @return an {@link I18nResourceBundleName}
     */
    public static I18nResourceBundleName newI18nResourceBundleName(
            final Package referencePackage,
            final String  messageBundleFilenameBase
            )
    {
        return () -> referencePackage.getName() + '.' + messageBundleFilenameBase;
    }

    /**
     * Create a {@link I18nResourceBundleName} based on package name of a class
     * and on a simple name
     *
     * @param referenceClass
     *            Reference class to use for {@link ResourceBundle} access
     * @param messageBundleBaseName
     *            Filename prefix to use for {@link ResourceBundle}
     * @return an {@link I18nResourceBundleName}
     */
    public static I18nResourceBundleName newI18nResourceBundleName(
        final Class<?> referenceClass,
        final String   messageBundleBaseName
        )
    {
        return newI18nResourceBundleName( referenceClass.getPackage(), messageBundleBaseName );
    }

    /**
     * Create a {@link I18nResourceBundleName} based on package name and
     * {@link I18nResourceFactory#DEFAULT_MESSAGE_BUNDLE_BASENAME} value
     *
     * @param packageMessageBundleBase package to use
     * @return an {@link I18nResourceBundleName}
     */
    public static I18nResourceBundleName newI18nResourceBundleName(
        final Package packageMessageBundleBase
        )
    {
        return newI18nResourceBundleName( packageMessageBundleBase, I18nResourceFactory.DEFAULT_MESSAGE_BUNDLE_BASENAME );
    }
}
