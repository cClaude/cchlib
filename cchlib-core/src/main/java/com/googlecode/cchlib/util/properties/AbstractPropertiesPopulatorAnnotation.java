package com.googlecode.cchlib.util.properties;

import java.util.Collection;

//NOT public
@SuppressWarnings("squid:S00119")
abstract class AbstractPropertiesPopulatorAnnotation<E,METHOD_OR_FIELD> //
    implements PropertiesPopulatorAnnotation<E,METHOD_OR_FIELD>
{
    private final Populator populator;

    AbstractPropertiesPopulatorAnnotation( final Populator populator )
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

    /**
     * @deprecated Use {@link #convertStringToObject(String,Class<?>)} instead
     */
    @Deprecated
    @SuppressWarnings("squid:S00100")
    protected static final Object private_convertStringToObject(
        final String   strValue, //
        final Class<?> type //
        ) throws ConvertCantNotHandleTypeException
    {
        return convertStringToObject( strValue, type );
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
            throw new ConvertCantNotHandleTypeException();
        }
    }

    @SuppressWarnings("rawtypes")
    private static Object getEnumValueUnchecked(
        final Class  enumType,
        final String  enumName
        )
    {
        @SuppressWarnings({ "unchecked", "squid:S1488" })
        final Enum<?> value = Enum.valueOf( enumType, enumName );

        return value;
    }

    private static <T> T newInstance( final Class<T> type )
    {
        try {
            return type.newInstance();
        }
        catch( InstantiationException | IllegalAccessException shouldNotOccur ) {
            throw new PropertiesPopulatorRuntimeException( shouldNotOccur );
        }
    }
}
