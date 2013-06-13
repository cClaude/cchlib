package com.googlecode.cchlib.i18n.core;

import java.util.Locale;
import com.googlecode.cchlib.NeedDoc;

/**
 * 
 *
 */
@NeedDoc
public interface AutoI18nCore 
{
    /**
     * 
     * @param objectToI18n
     * @param clazz
     */
    public <T> void performeI18n( T objectToI18n, Class<? extends T> clazz );

    /**
     * 
     * @param locale
     */
    public void setLocale( Locale locale );
}
