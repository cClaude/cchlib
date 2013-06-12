package com.googlecode.cchlib.i18n;

import java.io.Serializable;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.i18n.core.resolve.Keys;
import com.googlecode.cchlib.i18n.core.resolve.Values;

/**
 * TODOC
 *
 */
@NeedDoc
public interface AutoI18nType extends Serializable
{
    /**
     * Returns class handled by this Type
     * @return class handled by this Type
     */
    public Class<?> getType();

    /**
     * Set localized text
     *
     * @param toI18n object to localize
     * @param values TODOC
     */
    public void setText(Object toI18n, Values values);

    /**
     * Returns current text string for this object
     * 
     * @param toI18n object to localize
     * @return TODOC
     */ 
    public Values getText(Object toI18n);

    /**
     * TODOC
     * @param toI18n
     * @param keyBaseName
     * @return TODOC
     */
    public Keys getKeys( Object toI18n, String keyBaseName );
}