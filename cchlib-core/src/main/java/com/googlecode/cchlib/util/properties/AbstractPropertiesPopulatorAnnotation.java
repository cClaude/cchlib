package com.googlecode.cchlib.util.properties;

import java.util.Collection;


//NOT public
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

    protected static Object private_convertStringToObject( // // $codepro.audit.disable cyclomaticComplexity
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
            @SuppressWarnings({ "unchecked", "rawtypes" })
            final Enum value = Enum.valueOf( (Class)type, strValue );

            return value;
        } else if( strValue.isEmpty() ) {
            if( Collection.class.isAssignableFrom( type ) && !type.isInterface() ) {
                try {
                    return type.newInstance();
                }
                catch( InstantiationException | IllegalAccessException shouldNotOccur ) {
                    throw new RuntimeException( shouldNotOccur );
                }
            } else {
                return null;
            }
        } else {
            throw new ConvertCantNotHandleTypeException();
        }
    }
}
