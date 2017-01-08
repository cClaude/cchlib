package com.googlecode.cchlib.i18n.resources;

import java.util.ResourceBundle;

/**
 * @since 4.2
 *
 */
public class ResourceBundleHelper
{
    private ResourceBundleHelper()
    {
        // All static
    }

    /**
     * Returns name based on {@code referenceType} full name.
     *
     * @param referenceType
     *            Class support for resource bundle. This is basically a class
     *            in the same package than the {@link ResourceBundle} and with
     *            the same name.
     * @return the {@link ResourceBundle} name
     */
    public static String newName( final Class<?> referenceType )
    {
        return newName(
            referenceType.getPackage(),
            referenceType.getSimpleName()
            );
    }

    /**
     * Returns name based on package name of a class and on a simple name
     *
     * @param referencePackage
     *            Package name to use for {@link ResourceBundle} access
     * @param messageBundleFilenameBase
     *            Filename prefix to use for {@link ResourceBundle}
     * @return the {@link ResourceBundle} name
     */
    public static String newName(
        final Package referencePackage,
        final String  messageBundleFilenameBase
        )
    {
        return referencePackage.getName() + '.' + messageBundleFilenameBase;
    }

    /**
     * Returns name based on package name of a class
     * and on a simple name
     *
     * @param referenceClass
     *            Reference class to use for {@link ResourceBundle} access
     * @param messageBundleBaseName
     *            Filename prefix to use for {@link ResourceBundle}
     * @return the {@link ResourceBundle} name
     */
    public static String newName(
        final Class<?> referenceClass,
        final String   messageBundleBaseName
        )
    {
        return newName( referenceClass.getPackage(), messageBundleBaseName );
    }

    /**
     * Returns name based on package name and
     * {@link I18nResourceFactory#DEFAULT_MESSAGE_BUNDLE_BASENAME} value
     *
     * @param packageMessageBundleBase package to use
     * @return the {@link ResourceBundle} name
     */
    public static String newName(
        final Package packageMessageBundleBase
        )
    {
        return newName(
                packageMessageBundleBase,
                I18nResourceFactory.DEFAULT_MESSAGE_BUNDLE_BASENAME
                );
    }
}
