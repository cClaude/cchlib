package com.googlecode.cchlib.i18n.core;

import java.util.Locale;

public interface I18nApplyable<T>
{
    public void performeI18n( T objectToI18n, Locale locale );
}
