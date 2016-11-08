package com.googlecode.cchlib.lang.reflect;

import java.io.Serializable;
import java.lang.reflect.Method;

class MethodFilterByName implements MethodFilter, Serializable
{
    private static final long serialVersionUID = 1L;
    private final String methodName;

    public MethodFilterByName( final String methodName )
    {
        this.methodName = methodName;
    }

    @Override
    public boolean isSelected( final Method method )
    {
        return method.getName().equals( getMethodName() );
    }

    public String getMethodName()
    {
        return methodName;
    }

}
