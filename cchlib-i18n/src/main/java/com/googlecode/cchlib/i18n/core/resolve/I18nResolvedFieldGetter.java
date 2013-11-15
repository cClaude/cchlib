package com.googlecode.cchlib.i18n.core.resolve;

public interface I18nResolvedFieldGetter
{
    Values getValues( Keys keys ) throws GetFieldException;
}
