package com.googlecode.cchlib.i18n.core;

import java.util.EnumSet;
import com.googlecode.cchlib.i18n.AutoI18nConfig;

public class MethodProviderFactory
{
    private static MethodProvider METHOD_PROVIDER;

    public static MethodProvider getMethodProvider( final I18nDelegator i18nDelegator )
    {
        return getMethodProvider( i18nDelegator.getConfig() );
    }

    public static MethodProvider getMethodProvider( final EnumSet<AutoI18nConfig> config )
    {
        if( MethodProviderFactory.METHOD_PROVIDER == null ) {
            MethodProviderFactory.METHOD_PROVIDER = new MethodProviderImpl();
            }

        return MethodProviderFactory.METHOD_PROVIDER;
    }

    public static void setMethodProvider( final MethodProvider methodProvider )
    {
        MethodProviderFactory.METHOD_PROVIDER = methodProvider;
    }
}
