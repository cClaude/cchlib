package com.googlecode.cchlib.xutil.google.googlecontact.analyser;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.util.iterable.Iterables;
import com.googlecode.cchlib.util.iterable.XIterables;
import com.googlecode.cchlib.util.iterator.Iterators;
import com.googlecode.cchlib.util.iterator.Selectable;
import com.googlecode.cchlib.xutil.google.googlecontact.types.GoogleContact;
import com.googlecode.cchlib.xutil.google.googlecontact.util.GoogleContactType;
import com.googlecode.cchlib.xutil.google.googlecontact.util.Header;

public class GoogleContactAnalyser {

    private final static Logger LOGGER = Logger.getLogger( GoogleContactAnalyser.class );
    private final TypeInfoImpl rootTypeInfo;

    private final Map<Class<GoogleContactType>,TypeInfoImpl> typeInfos = new HashMap<>();

    public GoogleContactAnalyser()
    {
        this.rootTypeInfo = createTypeInfo( GoogleContact.class );
    }

    private TypeInfoImpl createTypeInfo( final Class<? extends GoogleContactType> clazz )
    {
        final Collection<Method> declaredMethods = getHeaderMethods( clazz );
        final TypeInfoImpl       typeInfo        = new TypeInfoImpl( declaredMethods );

        if( ! declaredMethods.isEmpty() ) {
            for( final Method method : declaredMethods ) {
                final String value = method.getAnnotation( Header.class ).value();

                if( value.isEmpty() ) {
                    throw new RuntimeException( "@Header has no value for : " + method );
                }

                final Class<?>[] parameterTypes = method.getParameterTypes();

                if( parameterTypes.length != 1 ) {
                    throw new RuntimeException( "Method should have (only) one parameter : " + method );
                }

                if( parameterTypes[ 0 ].equals( String.class ) ) {
                    if( LOGGER.isTraceEnabled() ) {
                        LOGGER.trace( "Found String value [" + value + "] for method : " + method );
                    }

                    typeInfo.addStringMethod( value, method );
                } else {
                    final TypeInfoImpl subTypeInfo = getOrCreateTypeInfoFor( parameterTypes[ 0 ] );

                    if( LOGGER.isTraceEnabled() ) {
                        LOGGER.trace( "Found value [" + value + "] for method : " + method );
                    }

                    typeInfo.addCustomTypeMethod( value, method, subTypeInfo );
                }
            }
        }

        assert typeInfo.getMethodForCustomType().size() + typeInfo.getMethodForStrings().size() > 1;

        return typeInfo;
    }

    private static Collection<Method> getHeaderMethods( final Class<?> clazz )
    {
        final List<Method> methods = XIterables.filter(
            Iterables.create( Iterators.create( clazz.getDeclaredMethods() ) ),
            new Selectable<Method>() {
                @Override
                public boolean isSelected( final Method method )
                {
                    return method.getAnnotation( Header.class ) != null;
                }} ).toList();

        for( final Type anInterface : clazz.getGenericInterfaces() ) {
            final Class<?>           subClass   = (Class<?>)anInterface;
            final Collection<Method> subMethods = getHeaderMethods( subClass );

            methods.addAll( subMethods );
        }

        return methods;
    }

    private TypeInfoImpl getOrCreateTypeInfoFor( final Class<?> clazz )
    {
        TypeInfoImpl typeInfo = typeInfos.get( clazz );

        assert GoogleContactType.class.isAssignableFrom( clazz );

        if( typeInfo == null ) {
            @SuppressWarnings("unchecked")
            final Class<GoogleContactType> googleContactTypeClass = (Class<GoogleContactType>)clazz;

            typeInfo = createTypeInfo( googleContactTypeClass );
            typeInfos.put( googleContactTypeClass, typeInfo );
        }
        return typeInfo;
    }

    public TypeInfo getTypeInfo()
    {
        return rootTypeInfo;
    }

//    public TypeInfo getTypeInfoFor( final GoogleContactType element ) throws GoogleContacAnalyserException
//    {
//        final Class<? extends GoogleContactType> clazz = element.getClass();
//
//        for( final Map.Entry<Class<GoogleContactType>, TypeInfoImpl> entry : typeInfos.entrySet() ) {
//            if( clazz.isAssignableFrom( entry.getKey() ) ) {
//                return entry.getValue();
//            }
//        }
//
//        throw new GoogleContacAnalyserException( "Can not handle class " + clazz );
//    }

    public TypeInfo getTypeInfoFor( final Class<?> clazz ) throws GoogleContacAnalyserException
    {
        final TypeInfo typeInfo = typeInfos.get( clazz );

        if( typeInfo != null ) {
            return typeInfo;
        }

        throw new GoogleContacAnalyserException( "Can not handle class " + clazz );
    }
}
