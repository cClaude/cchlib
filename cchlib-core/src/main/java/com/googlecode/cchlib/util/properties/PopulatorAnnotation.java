package com.googlecode.cchlib.util.properties;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

/* not public */
class PopulatorAnnotation<E> implements PropertiesPopulatorAnnotation<E>
{
    private Populator populator;

    PopulatorAnnotation( Populator populator )
    {
        this.populator = populator;
    }
    @Override
    public boolean isDefaultValueNull()
    {
        return populator.defaultValueIsNull();
    }
    @Override
    public String defaultValue()
    {
        return populator.defaultValue();
    }
    @Override
    public String toString( final Object o )
    {
        return o.toString();
    }
    @Override
    public void setValue( final Field f, final E bean, final String strValue, final Class<?> type )
        throws IllegalArgumentException,
               IllegalAccessException,
               ConvertCantNotHandleTypeException,
               PropertiesPopulatorException
    {
        f.set( bean, private_convertStringToObject( strValue, type ) );
    }
    @Override
    public void setArrayEntry( final Field f, final Object array, final int index, final String strValue, final Class<?> type)
        throws ArrayIndexOutOfBoundsException,
               IllegalArgumentException,
               ConvertCantNotHandleTypeException,
               PropertiesPopulatorException
    {
        Array.set( array, index, private_convertStringToObject( strValue, type ) );
    }

    private static Object private_convertStringToObject( // $codepro.audit.disable cyclomaticComplexity
        final String    strValue,
        final Class<?>  type
        ) throws ConvertCantNotHandleTypeException
    {
        if( strValue == null ) {
            // No value.
            return null;
            }

        if( String.class.isAssignableFrom( type ) ) {
            return strValue;
            }
        else if( boolean.class.isAssignableFrom( type ) ) {
            return Boolean.valueOf( strValue );
            }
        else if( Boolean.class.isAssignableFrom( type ) ) {
            return Boolean.valueOf( strValue );
            }
        else if( int.class.isAssignableFrom( type ) ) {
            return Integer.valueOf( strValue );
            }
        else if( Integer.class.isAssignableFrom( type ) ) {
            return Integer.valueOf( strValue );
            }
        else if( short.class.isAssignableFrom( type ) ) {
            return Short.valueOf( strValue );
            }
        else if( Short.class.isAssignableFrom( type ) ) {
            return Short.valueOf( strValue );
            }
        else if( byte.class.isAssignableFrom( type ) ) {
            return Byte.valueOf( strValue );
            }
        else if( Byte.class.isAssignableFrom( type ) ) {
            return Byte.valueOf( strValue );
            }
        else if( long.class.isAssignableFrom( type ) ) {
            return Long.valueOf( strValue );
            }
        else if( Long.class.isAssignableFrom( type ) ) {
            return Long.valueOf( strValue );
            }
        else if( float.class.isAssignableFrom( type ) ) {
            return Float.valueOf( strValue );
            }
        else if( Float.class.isAssignableFrom( type ) ) {
            return Float.valueOf( strValue );
            }
        else {
            throw new ConvertCantNotHandleTypeException();
            }
    }
}
