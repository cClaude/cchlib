package com.googlecode.cchlib.xutil.google.googlecontact.analyser;

import java.lang.reflect.Method;
import com.googlecode.cchlib.xutil.google.googlecontact.util.GoogleContactType;

public abstract class AbstractMethodContener
{
    private final Method method;

    public AbstractMethodContener( final Method method )
    {
        assert method != null;
        assert GoogleContactType.class.isAssignableFrom( method.getDeclaringClass() ); // NOSONAR

        this.method = method;
    }

    public Method getMethod()
    {
        return this.method;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( getClass().getSimpleName() );
        builder.append( " [method=" );
        builder.append( this.method );
        builder.append( ']' );
        return builder.toString();
    }
}