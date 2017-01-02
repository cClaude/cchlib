package com.googlecode.cchlib.i18n.core.internal;

import java.io.Serializable;
import com.googlecode.cchlib.i18n.core.I18nField;

/**
 * I18n view of a {@link Class}.
 * <BR>
 * Contain original class and an iterator overs {@link I18nField} that
 * should be internationalized.
 *
 * @param <T> Type of object to internationalize
 */
interface I18nClass<T> extends Serializable, Iterable<I18nField>
{
    /** Original class */
   Class<? extends T> getObjectToI18nClass();
}
