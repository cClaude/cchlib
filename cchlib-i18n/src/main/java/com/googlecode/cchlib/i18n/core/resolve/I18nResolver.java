package com.googlecode.cchlib.i18n.core.resolve;

public interface I18nResolver
{
    Keys getKeys() throws MissingKeyException;

    I18nResolvedFieldGetter getI18nResolvedFieldGetter();
    I18nResolvedFieldSetter getI18nResolvedFieldSetter();
}
