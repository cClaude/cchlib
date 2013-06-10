package com.googlecode.cchlib.i18n.core;

import java.util.Locale;

public interface AutoI18nCore 
{
    public <T> void performeI18n( T objectToI18n, Class<? extends T> clazz );

    public void setLocale( Locale locale );
}
