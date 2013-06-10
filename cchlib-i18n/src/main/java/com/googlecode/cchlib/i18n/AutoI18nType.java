package com.googlecode.cchlib.i18n;

import java.io.Serializable;
import com.googlecode.cchlib.i18n.core.resolve.Keys;
import com.googlecode.cchlib.i18n.core.resolve.Values;

/**
 * TODOC
 *
 */
public interface AutoI18nType extends Serializable
{
    /**
     * Returns class handled by this Type
     * @return class handled by this Type
     */
    public Class<?> getType();

//    /**
//     * Set localized text
//     *
//     * @param toI18n object to localize
//     * @param key    key object for resolve value
//     * @throws MissingResourceException if resource missing
//     */
//    public void setText(Object toI18n, I18nResolvedField key)
//        throws MissingResourceException;
    
    /**
     * Set localized text
     *
     * @param toI18n object to localize
     * @param key    TODOC
     */
    public void setText(Object toI18n, Values values);

//    /**
//     * Returns current text string for this object
//     * 
//     * @param toI18n object to localize
//     * @return not empty String
//     *         array, null if not supported
//     */
//    public String[] getText(Object toI18n);
    
    /**
     * Returns current text string for this object
     * 
     * @param toI18n object to localize
     * @return TODOC
     */ 
    public Values getText(Object toI18n);

    public Keys getKeys( Object toI18n, String keyBaseName );
}