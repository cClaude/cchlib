package com.googlecode.cchlib.i18n;

import java.io.Serializable;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.i18n.core.resolve.Keys;
import com.googlecode.cchlib.i18n.core.resolve.Values;

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
     * @param values NEEDDOC
     */
    void setText(Object toI18n, Values values);

    /**
     * Returns current text string for this object
     *
     * @param toI18n object to localize
     * @return NEEDDOC
     */
    Values getText(Object toI18n);

    /**
     * NEEDDOC
     * @param toI18n
     * @param keyBaseName
     * @return NEEDDOC
     */
    Keys getKeys( Object toI18n, String keyBaseName );
}
