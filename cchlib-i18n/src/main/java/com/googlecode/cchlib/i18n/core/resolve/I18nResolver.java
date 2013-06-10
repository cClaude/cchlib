package com.googlecode.cchlib.i18n.core.resolve;

public interface I18nResolver 
{
    public Keys getKeys() throws MissingKeyException;

    public I18nResolvedFieldGetter getI18nResolvedFieldGetter();
    public I18nResolvedFieldSetter getI18nResolvedFieldSetter();
}
