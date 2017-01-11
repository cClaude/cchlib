package com.googlecode.cchlib.i18n.resources;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import com.googlecode.cchlib.i18n.api.I18nResource;

/**
 * Factory for {@link I18nResource}
 *
 * @since 4.2
 */
public class I18nResourceFactory
{
    /** Default name for messages bundle : {@value} */
    public static final String DEFAULT_MESSAGE_BUNDLE_BASENAME = "MessagesBundle";

    private I18nResourceFactory()
    {
        // All static
    }

    /**
     * Create an {@link I18nResourceBundle}
     *
     * @param resourceBundleName the resource bundle name
     * @param locale the locale
     * @return a new {@link I18nResourceBundle}
     * @deprecated avoid to use deprecated {@link I18nResourceBundleName}
     */
    @Deprecated
    public static I18nResourceBundle newI18nResourceBundle(
        @Nonnull final I18nResourceBundleName resourceBundleName,
        @Nullable final Locale                locale
        )
    {
         return newI18nResourceBundle(
                 resourceBundleName.getName(),
                 locale
                 );
    }

    public static I18nResourceBundle newI18nResourceBundle(
        @Nonnull final String  resourceBundleFullBaseName,
        @Nullable final Locale locale
        )
    {
        return new I18nResourceBundle(
                resourceBundleFullBaseName,
                getSafeLocale( locale )
                );
    }

    /**
     * Create an {@link I18nResource}
     * <p>
     * This is method invoke {@link #newI18nResourceBundle(Class, Locale)}
     *
     * @param referenceType
     *            Class support for resource bundle. This is basically a class
     *            in the same package than the {@link ResourceBundle} and with
     *            the same name.
     * @param locale the locale
     * @return a new {@link I18nResource}
     */
    public static I18nResource newI18nResource(
        @Nonnull final Class<?> referenceType,
        @Nullable final Locale  locale
        )
    {
         return newI18nResourceBundle( referenceType, locale );
    }

    /**
     * Create a {@link I18nResourceBundle} based on {@code referenceType} full name.
     *
     * @param referenceType
     *            Class support for resource bundle. This is basically a class
     *            in the same package than the {@link ResourceBundle} and with
     *            the same name.
     * @param locale the locale
     * @return a new {@link I18nResourceBundle}
     */
    public static I18nResourceBundle newI18nResourceBundle(
        @Nonnull final Class<?> referenceType,
        @Nullable final Locale  locale
        )
    {
        return newI18nResourceBundle(
            ResourceBundleHelper.newName(
                referenceType.getPackage(),
                referenceType.getSimpleName()
                ),
            locale
            );
    }

    /**
     * Create a {@link I18nResourceBundle} based on package name of a
     * class and on a simple name
     *
     * @param referencePackage
     *            Package name to use for {@link ResourceBundle} access
     * @param messageBundleFilenameBase
     *            Filename prefix to use for {@link ResourceBundle}
     * @param locale the locale
     * @return a new {@link I18nResourceBundle}
     */
    public static I18nResourceBundle newI18nResourceBundle(
        @Nonnull final Package referencePackage,
        @Nonnull final String  messageBundleFilenameBase,
        @Nonnull final Locale  locale
        )
    {
        return newI18nResourceBundle(
            ResourceBundleHelper.newName(
                referencePackage,
                messageBundleFilenameBase
                ),
            getSafeLocale( locale )
            );
    }

    /**
     * Create a {@link I18nResourceBundle} based on package name of a class
     * and on a simple name
     *
     * @param referenceClass
     *            Reference class to use for {@link ResourceBundle} access
     * @param messageBundleBaseName
     *            Filename prefix to use for {@link ResourceBundle}
     * @param locale the locale
     * @return a new {@link I18nResourceBundle}
     */
    public static I18nResourceBundle newI18nResourceBundle(
        @Nonnull final Class<?> referenceClass,
        @Nonnull final String   messageBundleBaseName,
        @Nonnull final Locale   locale
        )
    {
        return newI18nResourceBundle(
            ResourceBundleHelper.newName(
                referenceClass.getPackage(),
                messageBundleBaseName
                ),
            locale
            );
    }

    /**
     * Create a {@link I18nResourceBundleName} based on package name and
     * {@link #DEFAULT_MESSAGE_BUNDLE_BASENAME} value
     *
     * @param packageMessageBundleBase Package to use
     * @param locale The {@link Locale}
     * @return an {@link I18nResourceBundle}
     */
    public static I18nResourceBundle newI18nResourceBundle(
        @Nonnull final Package packageMessageBundleBase,
        @Nonnull final Locale  locale
        )
    {
        return newI18nResourceBundle(
            ResourceBundleHelper.newName(
               packageMessageBundleBase,
               I18nResourceFactory.DEFAULT_MESSAGE_BUNDLE_BASENAME
               ),
            locale
            );
    }

    private static Locale getSafeLocale( final Locale locale )
    {
        return locale == null ? Locale.getDefault() : locale;
    }
}
