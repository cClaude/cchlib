package com.googlecode.cchlib.lang.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/*NOT public*/ class StaticMethodFilter implements MethodFilter
{
    private static final long serialVersionUID = 1L;

    @Override
    public boolean isSelected( Method method )
    {
        return Modifier.isStatic( method.getModifiers() );
    }
}
