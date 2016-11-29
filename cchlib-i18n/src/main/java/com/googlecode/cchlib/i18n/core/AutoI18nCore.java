package com.googlecode.cchlib.i18n.core;


/**
 * Define a class able to be internationalized.
 *
 * @see I18nAutoCoreUpdatable
 */
@FunctionalInterface
public interface AutoI18nCore
{
    /**
     * Apply internationalization on <code>objectToI18n</code> based on
     * class definition <code>clazz</code>.
     *
     * @param <T>          Type of {@code objectToI18n}
     * @param objectToI18n Object to internationalize
     * @param clazz        Class to use for internationalization
     */
    <T> void performeI18n( T objectToI18n, Class<? extends T> clazz );
}
