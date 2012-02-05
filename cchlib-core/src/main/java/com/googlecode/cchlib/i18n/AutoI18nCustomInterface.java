package com.googlecode.cchlib.i18n;
//package cx.ath.choisnet.i18n;

import java.io.Serializable;
import java.util.Map;

/**
 * AutoI18nCustomInterface is design to help localization
 * process for objects which have their how logic in
 * localization.
 * 
 * @author Claude CHOISNET
 * @see AutoI18n
 */
public interface AutoI18nCustomInterface 
    extends Serializable
{
    /**
     * Returns a Map of key-value pairs strings.
     * TODO: Add param Locale ???
     * 
     * @param i18n
     * @return a Map of key-value pairs strings.
     */
    public Map<String,String> getI18n( I18nInterface i18n );

    /**
     * Set {@link I18nInterface} 
     *
     * @param i18n {@link I18nInterface} to set
     */
    public void setI18n( I18nInterface i18n );

}
