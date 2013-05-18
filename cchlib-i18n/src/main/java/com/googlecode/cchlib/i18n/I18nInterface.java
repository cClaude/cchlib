package com.googlecode.cchlib.i18n;

import java.io.Serializable;
import java.util.Locale;
import com.googlecode.cchlib.i18n.hidden.AutoI18nImpl;

/**
 * @see AutoI18nImpl
 */
public interface I18nInterface extends Serializable
{
    /**
     * Resolve key according to current {@link Locale}
     *
     * @param key Key to lookup for localization
     * @return String for giving key
     * @throws java.util.MissingResourceException if key not found
     */
    public String getString(String key)
        throws java.util.MissingResourceException;
}