package com.googlecode.cchlib.xutil.google.googlecontact.analyser;

import java.lang.reflect.Method;

// NOT public
final class AnalyserCustomTypeMethodContenerImpl extends AbstractMethodContener implements AnalyserCustomTypeMethodContener {
    private final TypeInfoImpl typeInfo;

    AnalyserCustomTypeMethodContenerImpl( final Method method, final TypeInfoImpl typeInfo )
    {
        super( method );

        this.typeInfo = typeInfo;

        assert typeInfo != null;
    }

    @Override
    public boolean isSuffixValid( final String headerSuffix )
            throws GoogleContactCSVException
    {
        final Method method = this.typeInfo.getMethod( headerSuffix );

        if( method == null ) {
            throw new GoogleContactCSVException( "Can not find headerSuffix [" + headerSuffix + "] on " + this.typeInfo );
        }

        return true;
    }

    @Override
    public TypeInfo getTypeInfo()
    {
        return this.typeInfo;
    }
}
