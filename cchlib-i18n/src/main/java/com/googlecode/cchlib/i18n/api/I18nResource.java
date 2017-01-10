package com.googlecode.cchlib.i18n.api;

import java.io.Serializable;
import java.util.Locale;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundle;
import com.googlecode.cchlib.i18n.resources.I18nResourceFactory;
import com.googlecode.cchlib.i18n.resources.MissingResourceException;

/**
 * Resolve value ({@link String}) from a string key ({@link String})
 * according to current {@link Locale}
 *
 * @see AutoI18nConfig
 * @see I18nResourceBundle
 * @see I18nResourceFactory
 */
@FunctionalInterface
public interface I18nResource extends Serializable
{
    /**
     * Resolve key according to current {@link Locale}
     *
     * @param key Key to lookup for localization
     * @return String for giving key
     *
     * @throws MissingResourceException if key not found
     */
    String getString( String key ) throws MissingResourceException;
}
