package com.googlecode.cchlib.i18n.core;

import java.util.EnumSet;
import com.googlecode.cchlib.i18n.AutoI18nConfig;

public class MethodProviderFactory
{
    private static MethodProvider methodProvider; // $codepro.audit.disable staticFieldNamingConvention

    public static MethodProvider getMethodProvider( I18nDelegator i18nDelegator )
    {
        return getMethodProvider( i18nDelegator.getConfig() );
    }

    public static MethodProvider getMethodProvider( EnumSet<AutoI18nConfig> config )
    {
        if( MethodProviderFactory.methodProvider == null ) {
            MethodProviderFactory.methodProvider = new MethodProviderImpl( config );
            }

        return MethodProviderFactory.methodProvider;
    }

    public static void setMethodProvider( MethodProvider methodProvider )
    {
        MethodProviderFactory.methodProvider = methodProvider;
    }
}
