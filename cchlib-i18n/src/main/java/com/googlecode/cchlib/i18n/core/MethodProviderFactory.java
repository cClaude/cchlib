package com.googlecode.cchlib.i18n.core;

import java.util.Set;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.core.internal.I18nDelegator;

public class MethodProviderFactory
{
    private static MethodProvider METHOD_PROVIDER;

    private MethodProviderFactory() {} // All static

    public static MethodProvider getMethodProvider( final I18nDelegator i18nDelegator )
    {
        return getMethodProvider( i18nDelegator.getConfig() );
    }

    public static MethodProvider getMethodProvider( final Set<AutoI18nConfig> config )
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
