package com.googlecode.cchlib.xutil.google.googlecontact.analyser;

import java.lang.reflect.Method;
import com.googlecode.cchlib.xutil.google.googlecontact.util.GoogleContactType;

public abstract class AbstractMethodContener
{
    private final Method method;

    public AbstractMethodContener( final Method method )
    {
        assert GoogleContactType.class.isAssignableFrom( method.getDeclaringClass() );

        this.method = method;
    }

    public Method getMethod()
    {
        return method;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( getClass().getSimpleName() );
        builder.append( " [method=" );
        builder.append( method );
        builder.append( ']' );
        return builder.toString();
    }
}