package com.googlecode.cchlib.i18n.builder;

import java.util.Locale;
import com.googlecode.cchlib.i18n.I18nInterface;

/**
 * @see I18nPropertyResourceBundleAutoUpdate
 */
public interface I18nAutoUpdateInterface
    extends I18nInterface
{
    /**
     * Must accept null to be able to use default system {@link Locale}
     *
     * @param locale a valid {@link Locale} or null
     */
    public void setLocale( Locale locale );

    /**
     * Return {@link Locale} to use for I18n
     *
     * @return always return a valid {@link Locale}
     */
    public Locale getLocale();

    /**
     * @return a String that typically identify
     * Resource bundle properties file.
     */
    public String getResourceBundleBaseName();
}
