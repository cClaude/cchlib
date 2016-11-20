package com.googlecode.cchlib.i18n.core;

import com.googlecode.cchlib.NeedDoc;

/**
 * Define a class able to be internationalized.
 *
 * @see I18nAutoCoreUpdatable
 */
@NeedDoc
public interface AutoI18nCore
{
    /**
     * Apply internationalization on <code>objectToI18n</code> based on
     * class definition <code>clazz</code>.
     *
     * @param objectToI18n Object to internationalize
     * @param clazz        Class to use for internationalization
     */
    <T> void performeI18n( T objectToI18n, Class<? extends T> clazz );
}
