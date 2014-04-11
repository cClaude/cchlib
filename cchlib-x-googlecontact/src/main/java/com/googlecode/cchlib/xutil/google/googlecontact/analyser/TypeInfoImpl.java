package com.googlecode.cchlib.xutil.google.googlecontact.analyser;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.cchlib.util.iterable.Iterables;
import com.googlecode.cchlib.util.iterator.Selectable;
import com.googlecode.cchlib.xutil.google.googlecontact.util.Header;

final class TypeInfoImpl implements TypeInfo {
    private final List<Method>                                  declaredMethods;
    private final Map<String,AnalyserMethodContener>            methodForStrings     = new HashMap<>();
    private final Map<String,AnalyserCustomTypeMethodContener>  methodForCustomTypes = new HashMap<>();

    TypeInfoImpl( final Iterable<Method> declaredMethods )
    {
        this.declaredMethods = Iterables.newList( declaredMethods );

        assert this.declaredMethods.size() > 0;
    }

    @Override
    public int getParameterCount()
    {
        assert this.declaredMethods.size() == (methodForStrings.size() + methodForCustomTypes.size());

        return this.declaredMethods.size();
    }

    Method getMethod( final String headerSuffix )
    {
        return Iterables.find( declaredMethods, new Selectable<Method>() {
            @Override
            public boolean isSelected( final Method method )
            {
                return method.getAnnotation( Header.class ) != null;
            }} );
    }

    @Override
    public Map<String,AnalyserMethodContener> getMethodForStrings()
    {
        return Collections.unmodifiableMap( methodForStrings );
    }

    @Override
    public Map<String,AnalyserCustomTypeMethodContener> getMethodForCustomType()
    {
        return Collections.unmodifiableMap( methodForCustomTypes );
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "TypeInfoImpl [declaredMethods=" );
        builder.append( declaredMethods );
        builder.append( ", getParameterCount()=" );
        builder.append( getParameterCount() );
        builder.append( ']' );
        return builder.toString();
    }

    public void addStringMethod( final String value, final Method method )
    {
        methodForStrings.put( value, new AnalyserMethodContenerImpl( method ) );
    }

    public void addCustomTypeMethod(
        final String       value,
        final Method       method,
        final TypeInfoImpl subTypeInfo
        )
    {
        methodForCustomTypes.put( value, new AnalyserCustomTypeMethodContenerImpl( method, subTypeInfo ) );
    }
}
