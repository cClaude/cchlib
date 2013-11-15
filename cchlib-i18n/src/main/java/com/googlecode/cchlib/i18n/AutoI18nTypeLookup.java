package com.googlecode.cchlib.i18n;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Describe a class that is able to resolve an {@link AutoI18nType} base
 * on a {@link Field}
 *
 * @since 4.1.7
 */
public interface AutoI18nTypeLookup extends Serializable
{
    /**
     * Try to find {@link AutoI18nType} for this <code>field</code>.
     *
     * @param field {@link Field} to analyse.
     * @return an  {@link AutoI18nType} if this <code>field</code> is supported, null otherwise.
     */
    AutoI18nType lookup( Field field );
}
