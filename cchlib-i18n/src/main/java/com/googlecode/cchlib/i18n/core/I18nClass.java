package com.googlecode.cchlib.i18n.core;

import java.io.Serializable;

interface I18nClass<T> extends Serializable, Iterable<I18nField>
{
    Class<? extends T> getObjectToI18nClass();
    //public <T> void performeI18n( T objectToI18n );
    //public void addAutoI18nExceptionHandler( AutoI18nExceptionHandler exceptionHandler );
    //public void addAutoI18nEventHandler( AutoI18nEventHandler eventHandler );
}
