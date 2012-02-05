package com.googlecode.cchlib.i18n;
//package cx.ath.choisnet.i18n;

import java.io.Serializable;
import java.util.Locale;

/**
 * @see AutoI18n
 * @author Claude CHOISNET
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