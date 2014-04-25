package com.googlecode.cchlib.i18n.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import com.googlecode.cchlib.i18n.MethodProviderNoSuchMethodException;
import com.googlecode.cchlib.i18n.MethodProviderSecurityException;

//NOT public
final class MethodProviderImpl implements MethodProvider
{
    private static final long serialVersionUID = 1L;

    public MethodProviderImpl()
    {
    }

    @Override
    public MethodContener getMethods( final Class<?> clazz, final Field field, final String methodName )
            throws MethodProviderNoSuchMethodException, MethodProviderSecurityException
    {
        // FIXME look for f.getDeclaringClass() up to clazz (when enable access to not public methods)
        final Class<?>[] todo_improve_this_but_not_so_bad = I18nClassImpl.NOT_HANDLED_CLASS_TYPES;

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

    private MethodContener getValidMethods( final Class<?> clazz, final String methodName )
        throws SecurityException, NoSuchMethodException
    {
        final MethodContener methodContener = new MethodContenerImpl( clazz, methodName );
        final Method method = methodContener.getMethod();

        if( ! method.getReturnType().equals( void.class ) ) {
            throw new NoSuchMethodException( "Method " + method + " return : " + method.getReturnType() );
            }

        return methodContener;
    }
}
