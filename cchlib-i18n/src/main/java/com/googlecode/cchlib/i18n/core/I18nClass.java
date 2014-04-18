package com.googlecode.cchlib.i18n.core;

import com.googlecode.cchlib.NeedDoc;
import java.io.Serializable;

/**
 * 
 * @param <T>
 */
@NeedDoc
interface I18nClass<T> extends Serializable, Iterable<I18nField>
{
   Class<? extends T> getObjectToI18nClass();
}
