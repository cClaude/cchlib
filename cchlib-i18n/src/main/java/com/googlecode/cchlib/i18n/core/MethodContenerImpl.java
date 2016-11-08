package com.googlecode.cchlib.i18n.core;

import java.lang.reflect.Method;
import javax.annotation.Nonnull;
import com.googlecode.cchlib.lang.Classes;

class MethodContenerImpl implements MethodContener
{
    private static final long serialVersionUID = 1L;

    private final Class<?> clazz;
    private final String methodName;
    private final Class<?>[] parameterTypes;
    private transient Method method;

    public MethodContenerImpl( final Class<?> clazz, final String methodName )
    {
        this(clazz,methodName, Classes.emptyArray());
    }

    public MethodContenerImpl( //
            @Nonnull final Class<?> clazz, //
            @Nonnull final String methodName, //
            @Nonnull final Class<?> ... parameterTypes //
            ) //
    {
        assert clazz != null : "clazz parameter is null";
        assert methodName != null : "methodName parameter is null";
        assert parameterTypes != null : "parameterTypes parameter is null";

        this.clazz          = clazz;
        this.methodName     = methodName;
        this.parameterTypes = parameterTypes;
    }

    @Override
    public String getMethodName()
    {
        return this.methodName;
    }

    @Override
    public Method getMethod() throws SecurityException, NoSuchMethodException
    {
        if( this.method == null ) {
            this.method = getMethodForClass( this.clazz, getMethodName(), this.parameterTypes );
        }

        return this.method;
    }

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
