package com.googlecode.cchlib.i18n;

import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.i18n.core.resolve.Keys;
import com.googlecode.cchlib.i18n.core.resolve.Values;
import java.io.Serializable;

@NeedDoc
public interface AutoI18nType extends Serializable
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
     * @param values TODOC
     */
    void setText(Object toI18n, Values values);

    /**
     * Returns current text string for this object
     *
     * @param toI18n object to localize
     * @return TODOC
     */
    Values getText(Object toI18n);

    /**
     * TODOC
     * @param toI18n
     * @param keyBaseName
     * @return TODOC
     */
    Keys getKeys( Object toI18n, String keyBaseName );
}
