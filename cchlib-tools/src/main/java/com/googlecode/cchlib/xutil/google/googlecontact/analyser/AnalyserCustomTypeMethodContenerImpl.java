package com.googlecode.cchlib.xutil.google.googlecontact.analyser;

import java.lang.reflect.Method;

final class AnalyserCustomTypeMethodContenerImpl extends AbstractMethodContener implements AnalyserCustomTypeMethodContener {
    private final TypeInfoImpl typeInfo;

    AnalyserCustomTypeMethodContenerImpl( final Method method, final TypeInfoImpl typeInfo )
    {
        super( method );
        this.typeInfo = typeInfo;

        assert method != null;
        assert typeInfo != null;
    }

    @Override
    public boolean checkSuffix( final String headerSuffix )
            throws GoogleContactCSVException
    {
        final Method method = typeInfo.getMethod( headerSuffix );

        if( method == null ) {
            throw new GoogleContactCSVException( "Can not find headerSuffix [" + headerSuffix + "] on " + typeInfo );
        }

        return true;
    }

    @Override
    public TypeInfo getTypeInfo()
    {
        return typeInfo;
    }
}