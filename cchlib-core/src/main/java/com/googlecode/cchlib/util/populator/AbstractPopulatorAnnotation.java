package com.googlecode.cchlib.util.populator;

import java.lang.reflect.Member;
import java.util.Collection;

//NOT public
@SuppressWarnings("squid:S00119") // naming convention
abstract class AbstractPopulatorAnnotation<E,METHOD_OR_FIELD extends Member>
    implements PopulatorAnnotation<E,METHOD_OR_FIELD>
{
    private final Populator populator;

    AbstractPopulatorAnnotation( final Populator populator )
    {
        this.populator = populator;
    }

    @Override
    public final boolean isDefaultValueNull()
    {
        return this.populator.defaultValueIsNull();
    }

    @Override
    public final String defaultValue()
    {
        return this.populator.defaultValue();
    }

    @Override
    public final String toString( final Object o )
    {
        return o.toString();
    }

    @SuppressWarnings({ "squid:MethodCyclomaticComplexity", "squid:S1871" })
    protected static final Object convertStringToObject(
        final String   strValue, //
        final Class<?> type //
        ) throws ConvertCantNotHandleTypeException
    {
        if( strValue == null ) {
            // No value.
            return null;
        }

        if( String.class.isAssignableFrom( type ) ) {
            return strValue;
        } else if( boolean.class.isAssignableFrom( type ) ) {
            return Boolean.valueOf( strValue );
        } else if( Boolean.class.isAssignableFrom( type ) ) {
            return Boolean.valueOf( strValue );
        } else if( int.class.isAssignableFrom( type ) ) {
            return Integer.valueOf( strValue );
        } else if( Integer.class.isAssignableFrom( type ) ) {
            return Integer.valueOf( strValue );
        } else if( short.class.isAssignableFrom( type ) ) {
            return Short.valueOf( strValue );
        } else if( Short.class.isAssignableFrom( type ) ) {
            return Short.valueOf( strValue );
        } else if( byte.class.isAssignableFrom( type ) ) {
            return Byte.valueOf( strValue );
        } else if( Byte.class.isAssignableFrom( type ) ) {
            return Byte.valueOf( strValue );
        } else if( long.class.isAssignableFrom( type ) ) {
            return Long.valueOf( strValue );
        } else if( Long.class.isAssignableFrom( type ) ) {
            return Long.valueOf( strValue );
        } else if( float.class.isAssignableFrom( type ) ) {
            return Float.valueOf( strValue );
        } else if( Float.class.isAssignableFrom( type ) ) {
            return Float.valueOf( strValue );
        } else if( Enum.class.isAssignableFrom( type ) ) {
            return getEnumValueUnchecked( type, strValue );
        } else if( strValue.isEmpty() ) {
            if( Collection.class.isAssignableFrom( type ) && !type.isInterface() ) {
                return newInstance( type );
            } else {
                return null;
            }
        } else {
            throw new ConvertCantNotHandleTypeException(
                    "Value is \"" + strValue + "\", expected type is " + type
                    );
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static Object getEnumValueUnchecked(
        final Class  enumType,
        final String enumName
        )
    {
        return Enum.valueOf( enumType, enumName );
    }

    private static <T> T newInstance( final Class<T> type )
    {
        try {
            return type.newInstance();
        }
        catch( InstantiationException | IllegalAccessException shouldNotOccur ) {
            throw new PopulatorRuntimeException( shouldNotOccur );
        }
    }
}
