package com.googlecode.cchlib.i18n.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import com.googlecode.cchlib.i18n.MethodProviderNoSuchMethodException;
import com.googlecode.cchlib.i18n.MethodProviderSecurityException;
import com.googlecode.cchlib.i18n.core.internal.I18nDelegator;

public class MethodProviderFactory
{
    private static MethodProvider methodProvider;

    private MethodProviderFactory()
    {
        // All static
    }

    public static MethodProvider getMethodProvider( final I18nDelegator i18nDelegator )
    {
        return getMethodProvider( i18nDelegator.getConfig() );
    }

    public static MethodProvider getMethodProvider(
        @SuppressWarnings("squid:S1172") final AutoI18nConfigSet config
        )
    {
        // config : not use there, but keep it for future extension.

        if( MethodProviderFactory.methodProvider == null ) {
            MethodProviderFactory.methodProvider = newMethodProviderImpl();
            }

        return MethodProviderFactory.methodProvider;
    }

    /**
     * Allow to define you own {@link MethodProvider}
     *
     * @param methodProvider to use for this context.
     */
    @SuppressWarnings("ucd") // API
    public static void setMethodProvider( final MethodProvider methodProvider )
    {
        MethodProviderFactory.methodProvider = methodProvider;
    }

    private static MethodProvider newMethodProviderImpl()
    {
        return MethodProviderFactory::getMethods;
    }

    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    private static MethodContener getMethods(
        final Class<?> clazz,
        final Field    field,
        final String   methodName
        ) throws MethodProviderNoSuchMethodException,
                 MethodProviderSecurityException
    {
        // FIXME look for field.getDeclaringClass() up to clazz
        // (when enable access to not public methods)
        // final Class<?>[] todo_improve_this_but_not_so_bad = I18nClassImpl.NOT_HANDLED_CLASS_TYPES; - NOSONAR

        try {
            return getValidMethods( field.getDeclaringClass(), methodName );
            }
        catch( final SecurityException e ) {
            throw new MethodProviderSecurityException( field, methodName, clazz, e );
            }
        catch( final NoSuchMethodException e ) {
            throw new MethodProviderNoSuchMethodException( field, methodName, clazz, e );
            }
    }

    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    private static MethodContener getValidMethods(
        final Class<?> clazz,
        final String   methodName
        ) throws SecurityException,
                 NoSuchMethodException
    {
        final MethodContener methodContener = new MethodContenerImpl( clazz, methodName );
        final Method         method         = methodContener.getMethod();

        if( ! method.getReturnType().equals( void.class ) ) {
            throw new NoSuchMethodException( "Method " + method + " return : " + method.getReturnType() );
            }

        return methodContener;
    }
}
