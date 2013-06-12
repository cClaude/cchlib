package com.googlecode.cchlib.i18n.core;

import java.io.Serializable;
import com.googlecode.cchlib.NeedDoc;

/**
 * 
 * @param <T>
 */
@NeedDoc
interface I18nClass<T> extends Serializable, Iterable<I18nField>
{
   Class<? extends T> getObjectToI18nClass();
}
