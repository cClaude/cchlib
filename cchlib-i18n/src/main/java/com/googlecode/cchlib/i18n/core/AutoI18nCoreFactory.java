package com.googlecode.cchlib.i18n.core;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.AutoI18nTypeLookup;
import com.googlecode.cchlib.i18n.api.I18nResource;
import com.googlecode.cchlib.i18n.core.internal.AutoI18nCoreImpl;
import com.googlecode.cchlib.i18n.core.internal.I18nDelegator;
import com.googlecode.cchlib.i18n.logging.AutoI18nLog4JEventHandler;
import com.googlecode.cchlib.i18n.logging.AutoI18nLog4JExceptionHandler;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundle;
import com.googlecode.cchlib.i18n.resources.I18nResourceFactory;

/**
 * Create {@link AutoI18nCore} object for internationalization.
 *
 * @see AutoI18nConfig
 * @see I18nResource
 * @see I18nResourceBundle
 * @see I18nResourceFactory
 */
public class AutoI18nCoreFactory
{
    /**
     * Default configuration is handle by {@link AutoI18nConfigSet}
     */
    @SuppressWarnings({
        "squid:S2386", // Not mutable...
        "ucd" // API configuration
        })
    public static final Set<AutoI18nConfig> DEFAULT_AUTO_I18N_CONFIG = null;

    /**
     * Use default implementation of {@link AutoI18nTypeLookup}
     */
   public static final AutoI18nTypeLookup DEFAULT_AUTO_I18N_TYPES  = null;

    /**
     * Default {@link Locale}, this is a synonym for {@link Locale#getDefault()}
     */
    @SuppressWarnings("ucd") // API configuration
    public static final Locale DEFAULT_LOCALE = null;

    private AutoI18nCoreFactory() {} // All static

    /**
     * Create a reasonable {@link AutoI18nCore}
     *
     * @param config
     *            The wanted configuration, if null use default configuration
     * @param defaultAutoI18nTypes
     *            The {@link AutoI18nTypeLookup}
     * @param i18nResource
     *            The {@link I18nResource}
     * @return an {@link AutoI18nCore} with giving configuration
     */
    @SuppressWarnings("ucd") // API
    public static AutoI18nCore newAutoI18nCore(
        @Nullable final Set<AutoI18nConfig> config,
        @Nullable final AutoI18nTypeLookup  defaultAutoI18nTypes,
        @Nonnull final I18nResource         i18nResource
        )
    {
        final AutoI18nConfigSet safeConfig = new AutoI18nConfigSet( config );

        final I18nDelegator i18nDelegator
            = new I18nDelegator( safeConfig, defaultAutoI18nTypes, i18nResource );

        i18nDelegator.addAutoI18nExceptionHandler( new AutoI18nLog4JExceptionHandler( safeConfig ) );
        i18nDelegator.addAutoI18nEventHandler( new AutoI18nLog4JEventHandler() );

        return new AutoI18nCoreImpl( i18nDelegator );
    }

    /**
     * Create a reasonable {@link AutoI18nCore} base on {@link #DEFAULT_AUTO_I18N_TYPES}
     *
     * @param config
     *            The wanted configuration, if null use default configuration
     * @param i18nResource
     *            The {@link I18nResource}
     * @return an {@link AutoI18nCore} with giving configuration
     * @see #DEFAULT_AUTO_I18N_TYPES
     */
    public static AutoI18nCore newAutoI18nCore(
        @Nullable final Set<AutoI18nConfig> config,
        @Nonnull final I18nResource         i18nResource
        )
    {
        return newAutoI18nCore( config, DEFAULT_AUTO_I18N_TYPES, i18nResource );
    }

    /**
     * Create a reasonable {@link AutoI18nCore}
     *
     * @param config
     *            The wanted configuration, if null use default configuration
     * @param resourceBundleName
     *            The {@link com.googlecode.cchlib.i18n.resources.I18nResourceBundleName}
     * @param locale
     *            The wanted {@link Locale}, use {@link Locale#getDefault()} if null
     * @return an {@link AutoI18nCore} with giving configuration
     *
     * @see com.googlecode.cchlib.i18n.resources.I18nResourceBundleNameFactory
     * @deprecated use {@link I18nResourceFactory} and {@link #newAutoI18nCore(Set, I18nResource)} instead.
     */
    @Deprecated
    public static AutoI18nCore newAutoI18nCore(
        @Nullable final Set<AutoI18nConfig>                                        config,
        @Nonnull final com.googlecode.cchlib.i18n.resources.I18nResourceBundleName resourceBundleName,
        @Nullable final Locale                                                     locale
        )
    {
         return newAutoI18nCore(
             config,
             I18nResourceFactory.newI18nResourceBundle( resourceBundleName, locale )
             );
    }

    /**
     * Create a reasonable {@link AutoI18nCore}
     *
     * @param resourceBundleName
     *            The {@link com.googlecode.cchlib.i18n.resources.I18nResourceBundleName}
     * @param locale
     *            The wanted {@link Locale}, use {@link Locale#getDefault()} if null
     * @return an {@link AutoI18nCore} with giving configuration
     *
     * @see com.googlecode.cchlib.i18n.resources.I18nResourceBundleNameFactory
     * @deprecated use {@link I18nResourceFactory} and {@link #newAutoI18nCore(I18nResource)} instead.
     */
    @Deprecated
    public static AutoI18nCore newAutoI18nCore(
        @Nonnull final com.googlecode.cchlib.i18n.resources.I18nResourceBundleName resourceBundleName,
        @Nullable final Locale                                                     locale
        )
    {
         return newAutoI18nCore(
             DEFAULT_AUTO_I18N_CONFIG,
             I18nResourceFactory.newI18nResourceBundle( resourceBundleName, locale )
             );
    }

    /**
     * Create a reasonable {@link AutoI18nCore} based on defaults
     *
     * @param i18nResource
     *            The {@link I18nResource}
     * @return an {@link AutoI18nCore} with giving configuration
     * @see #DEFAULT_AUTO_I18N_CONFIG
     * @see #DEFAULT_AUTO_I18N_TYPES
     */
    public static AutoI18nCore newAutoI18nCore(
        @Nonnull final I18nResource resourceBundle
        )
    {
         return newAutoI18nCore(
             DEFAULT_AUTO_I18N_CONFIG,
             resourceBundle
             );
    }

    /**
     * Create a reasonable {@link AutoI18nCore}
     *
     * @param resourceBundleName
     *            The {@link com.googlecode.cchlib.i18n.resources.I18nResourceBundleName}
     * @return an {@link AutoI18nCore} with giving configuration
     *
     * @see com.googlecode.cchlib.i18n.resources.I18nResourceBundleNameFactory
     * @deprecated use {@link I18nResourceFactory} and {@link #newAutoI18nCore(I18nResource)} instead.
     */
    @Deprecated
    public static AutoI18nCore newAutoI18nCore(
        @Nonnull final com.googlecode.cchlib.i18n.resources.I18nResourceBundleName resourceBundleName
        )
    {
         return newAutoI18nCore( resourceBundleName, DEFAULT_LOCALE  );
    }

    /**
     * Build a {@link AutoI18nCore} using default configuration, and
     * use {@code referenceType} to access {@link ResourceBundle} with
     * {@link #DEFAULT_LOCALE}
     *
     * @param referenceType
     *            Class support for resource bundle. This is basically a class
     *            in the same package than the {@link ResourceBundle} and with
     *            the same name.
     * @return an {@link AutoI18nCore} using default configuration.
     */
    @SuppressWarnings("ucd") // API
    public static AutoI18nCore newAutoI18nCore( @Nonnull final Class<?> referenceType )
    {
        return newAutoI18nCore(
            I18nResourceFactory.newI18nResource( referenceType, DEFAULT_LOCALE )
            );
    }

    public static AutoI18nCore newAutoI18nCore(
        @Nullable final Set<AutoI18nConfig> config,
        @Nonnull final Class<?>             referenceType
        )
    {
        return newAutoI18nCore(
                config,
                I18nResourceFactory.newI18nResource( referenceType, DEFAULT_LOCALE )
                );
    }
}
