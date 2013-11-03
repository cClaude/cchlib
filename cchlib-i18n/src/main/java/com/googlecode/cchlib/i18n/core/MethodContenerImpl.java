package com.googlecode.cchlib.i18n.core;

import java.lang.reflect.Method;
import com.googlecode.cchlib.lang.Classes;

class MethodContenerImpl implements MethodContener
{
    private static final long serialVersionUID = 1L;

    private Class<?> clazz;
    private String methodName;

    public MethodContenerImpl( Class<?> clazz, String methodName )
    {
        this.clazz      = clazz;
        this.methodName = methodName;
    }

    @Override
    public String getBaseName()
    {
        return methodName;
    }

    @Override
    public String getInvokeMethodName()
    {
        return getBaseName();
    }

//    @Override
//    public String getSetterName()
//    {
//        return "set" + getBaseName();
//    }

    @Override
    public Method getInvokeMethod() throws SecurityException, NoSuchMethodException
    {
        final String fullMethodName = getInvokeMethodName();

        Method m = getMethodForClass( clazz, fullMethodName, Classes.emptyArray() );

        if( m.getReturnType().equals( void.class ) ) {
            return m;
            }
        else {
            throw new NoSuchMethodException( "Method " + m + " return : " + m.getReturnType() );
            }
    }

//    @Override
//    public Method getSetter() throws SecurityException, NoSuchMethodException
//    {
//        final String fullMethodName = getSetterName();
//        return getMethodForClass( clazz, fullMethodName, new Class<?>[] {String.class} );
//    }

    private static Method getMethodForClass(
            final Class<?>   clazz,
            final String     methodName,
            final Class<?>[] parameterTypes
            )
            throws SecurityException, NoSuchMethodException
    {
        return clazz.getMethod( methodName, parameterTypes );
    }
}
