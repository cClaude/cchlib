package com.googlecode.cchlib.i18n;

import com.googlecode.cchlib.i18n.resources.MissingResourceException;
import java.io.Serializable;
import java.util.Locale;

/**
 * Resolve value ({@link String}) from a string key ({@link String})
 * according to current {@link Locale}
 *
 * @see AutoI18n
 */
public interface I18nInterface extends Serializable
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
