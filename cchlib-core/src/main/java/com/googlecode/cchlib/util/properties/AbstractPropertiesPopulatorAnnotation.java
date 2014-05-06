package com.googlecode.cchlib.util.properties;


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
        return populator.defaultValueIsNull();
    }

    @Override
    public final String defaultValue()
    {
        return populator.defaultValue();
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
        } else {
            throw new ConvertCantNotHandleTypeException();
        }
    }
}
