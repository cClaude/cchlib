package com.googlecode.cchlib.i18n.api;

import java.io.Serializable;
import java.util.Locale;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.resources.MissingResourceException;

/**
 * Resolve value ({@link String}) from a string key ({@link String})
 * according to current {@link Locale}
 *
 * @see AutoI18n
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
