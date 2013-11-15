package com.googlecode.cchlib.i18n;

import java.io.Serializable;
import java.util.MissingResourceException;

/**
 *
 *
 */
public interface AutoI18nTypes
    extends Serializable, Iterable<AutoI18nTypes.Type>
{
    /**
     *
     *
     */
    public interface Type extends Serializable
    {
        /**
         * Returns class handled by this Type
         * @return class handled by this Type
         */
        Class<?> getType();

        /**
         * Set localized text
         *
         * @param toI18n object to localize
         * @param key    key object for resolve value
         * @throws MissingResourceException if resource missing
         */
        void setText(Object toI18n, AutoI18n.Key key)
            throws MissingResourceException;

        /**
         * Returns current text string for this object
         *
         * @param toI18n object to localize
         * @return not empty String
         *         array, null if not supported
         */
        String[] getText(Object toI18n);
    }
}
