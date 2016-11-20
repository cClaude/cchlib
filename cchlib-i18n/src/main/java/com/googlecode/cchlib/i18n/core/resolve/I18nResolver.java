package com.googlecode.cchlib.i18n.core.resolve;

import com.googlecode.cchlib.NeedDoc;

@NeedDoc
public interface I18nResolver
{
    @NeedDoc
    Keys getKeys() throws MissingKeyException;

    @NeedDoc
    I18nResolvedFieldGetter getI18nResolvedFieldGetter();

    @NeedDoc
    I18nResolvedFieldSetter getI18nResolvedFieldSetter();
}
