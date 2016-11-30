package com.googlecode.cchlib.util.properties;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import org.apache.log4j.Logger;

//Not public
class PropertiesToBean<E>
{
    private static final Logger LOGGER = Logger.getLogger( PropertiesToBean.class );

    private final Properties    properties;
    private final E             bean;
    private final StringBuilder prefix;
    private final int           prefixLength;

    public PropertiesToBean(
        final String     propertiesPrefix,
        final Properties properties,
        final E          bean
        )
    {
        this.properties = properties;
        this.bean       = bean;

        if( (propertiesPrefix == null) || propertiesPrefix.isEmpty() ) {
            this.prefix = null;
            this.prefixLength = 0;
            }
        else {
            this.prefix = new StringBuilder( propertiesPrefix );
            this.prefixLength = this.prefix.length();
            }
     }

    public void populateMethods( final Map<String, PropertiesPopulatorAnnotation<E, Method>> getterSetterMap)
    {
        for( final Entry<String, PropertiesPopulatorAnnotation<E, Method>> entry : getterSetterMap.entrySet() ) {
            final PropertiesPopulatorAnnotation<E, Method> value = entry.getValue();

            if( value instanceof PropertiesPersistentAnnotationForMethod ) {
                final PropertiesPersistentAnnotationForMethod<E> entryValue = (PropertiesPersistentAnnotationForMethod<E>)value;
                final Method getter = entryValue.getGetter();

                assert getter.getParameterCount() == 0;

                final String defaultValue  = getDefaultValue( value );
                final String attributeName = entryValue.getAttributeName();
                final String strValue      = getStringValue( attributeName, defaultValue );

                try {
                    entryValue.setValue( this.bean, strValue, getter.getReturnType() );
                }
                catch( IllegalArgumentException | IllegalAccessException | PropertiesPopulatorRuntimeException | InvocationTargetException | ConvertCantNotHandleTypeException e ) {
                    LOGGER.warn( "Cannot set field for Method: " + getter + " * strValue=[" + strValue + "] / defaultValue=[" + defaultValue + ']', e );
                }
                }
            else {
                final PropertiesPopulatorAnnotationForMethod<E> entryValue = (PropertiesPopulatorAnnotationForMethod<E>)value;
                final Method setter = entryValue.getSetter();

                assert setter.getParameterCount() == 1;

                final Class<?> type = setter.getParameters()[ 0 ].getType();

                if( type.isArray() ) {
                    handleArrayMethod( entry.getKey(), entryValue, this.prefix, this.prefixLength );
                    }
                else {
                    handleNonArrayMethod( entry.getKey(), entryValue, type );
                    }
            }
        }
    }

    private void handleNonArrayMethod( //
        final String attributeName,
        final PropertiesPopulatorAnnotationForMethod<E> value,
        final Class<?> type //
        )
    {
        final String defaultValue  = getDefaultValue( value );
        final String strValue      = getStringValue( attributeName, defaultValue );
        final Method setter        = value.getSetter();

        final boolean setterIsAccessible = setter.isAccessible();

        try {
            setter.setAccessible( true );

            value.getPropertiesPopulatorSetter().setValue( this.bean, strValue, type );
            }
        catch( final ConvertCantNotHandleTypeException ccnhte ) {
            if( ! tryToSetUsingConstructor( value, type, strValue ) ) {
                throw new PropertiesPopulatorRuntimeException( "Bad value [" + strValue + "] for method " + setter, ccnhte );
            }
            }
        catch( IllegalAccessException | IllegalArgumentException | InvocationTargetException e ) {
            // ignore !
            LOGGER.fatal( "Cannot set field for Method: " + setter + " * strValue=[" + strValue + "] / defaultValue=[" + defaultValue + ']', e );
            }
        finally {
            setter.setAccessible( setterIsAccessible );
            }
    }

    /** try to find a constructor based on String */
    private boolean tryToSetUsingConstructor( final PropertiesPopulatorAnnotationForMethod<E> value, final Class<?> type, final String strValue )
    {
        try {
            final Object realValue = type.getConstructor( String.class ).newInstance( strValue );

            setValue( value.getPropertiesPopulatorSetter().getMethodOrField(), realValue );

            return true;
            }
        catch( InstantiationException | IllegalAccessException //
                | IllegalArgumentException | InvocationTargetException //
                | NoSuchMethodException | SecurityException newInstanceException ) {
            LOGGER.warn( "Can not find a constructor for " + type + " using String[" + strValue + ']', newInstanceException );
            return false;
            }
    }

    private void setValue( final Method method, final Object realValue ) //
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        method.invoke( this.bean, realValue );
    }

    private void handleArrayMethod( //
        final String attributeName, //
        final PropertiesPopulatorAnnotationForMethod<E> propertiesPopulatorAnnotation, //
        final StringBuilder prefix, //
        final int prefixLength //
        )
    {
        final Method        method      = propertiesPopulatorAnnotation.getGetter();
        final List<String> stringValues = getStringValues( prefix, prefixLength, attributeName );

        final boolean methodIsAccessible = method.isAccessible();

        try {
            method.setAccessible( true );

            Object array = method.invoke( this.bean );

            final PropertiesPopulatorSetter<E,Method> propertiesPopulatorSetter = propertiesPopulatorAnnotation.getPropertiesPopulatorSetter();

            final int       length          = stringValues.size();
            final Class<?>  arrayType       = method.getReturnType();
            final Class<?>  componentType   = arrayType.getComponentType();

            if( (array == null) || (length != Array.getLength( array )) ) {
                array = Array.newInstance( componentType, length );
                //field.set( bean, array );
                propertiesPopulatorAnnotation.getSetter().invoke( this.bean, array );
                }

            populateArray( array, arrayType, length, stringValues, propertiesPopulatorSetter, componentType );
            }
        catch( IllegalArgumentException | IllegalAccessException | InvocationTargetException e ) {
            // ignore !
            LOGGER.warn( "Cannot set Method:" + method, e );
            }
        finally {
            method.setAccessible( methodIsAccessible );
            }
    }

    public void populateFields( final Map<Field, PropertiesPopulatorAnnotation<E, Field>> fieldsMap )
    {
        for( final Entry<Field, PropertiesPopulatorAnnotation<E, Field>> entry : fieldsMap.entrySet() ) {
            final Field     field = entry.getKey();
            final Class<?>  type  = field.getType();
            final PropertiesPopulatorAnnotationForField<E> entryValue = (PropertiesPopulatorAnnotationForField<E>)(entry.getValue());

            if( type.isArray() ) {
                handleArrayField( entryValue, this.prefix, this.prefixLength );
                }
            else {
                handleNonArrayField( entryValue, type );
                }
            }
    }

    private void handleNonArrayField(
        final PropertiesPopulatorAnnotationForField<E>  entryValue,
        final Class<?>                                  type
        )
    {
        final String defaultValue = getDefaultValue( entryValue );
        final Field field         = entryValue.getField();
        final String strValue     = getStringValue( field.getName(), defaultValue );

        final boolean fieldIsAccessible = field.isAccessible();

        try {
            field.setAccessible( true );

            if( PopulatorContener.class.isAssignableFrom( type ) ) {
                final Object o = field.get( this.bean );

                if( o == null ) {
                    throw new PopulatorException( "Can't handle null for PopulatorContener field", field, field.getType() );
                    }
                final PopulatorContener contener = PopulatorContener.class.cast( o );

                contener.setConvertToString( strValue );
                }
            else {
                try {
                    entryValue.getPropertiesPopulatorSetter().setValue( this.bean, strValue, type );
                    }
                catch( final ConvertCantNotHandleTypeException | PropertiesPopulatorRuntimeException | InvocationTargetException e ) {
                    throw new PopulatorException( "Bad type for field", field, field.getType() );
                    }
                }
            }
        catch( IllegalAccessException | IllegalArgumentException e ) {
            // ignore !
            LOGGER.warn( "Cannot set field:" + field, e );
            }
        finally {
            field.setAccessible( fieldIsAccessible );
            }
    }

    private String getStringValue( final String attributName, final String defaultValue )
    {
        final String strValue;

        if( this.prefixLength == 0 ) {
            strValue = this.properties.getProperty( attributName, defaultValue );
            }
        else {
            this.prefix.setLength( this.prefixLength );
            this.prefix.append( attributName );
            strValue = this.properties.getProperty( this.prefix.toString(), defaultValue );
            }

        return strValue;
    }

    private String getDefaultValue( final PropertiesPopulatorAnnotation<E,?> ppa )
    {
        final String defaultValue;

        if( ppa.isDefaultValueNull() ) {
            defaultValue = null;
            }
        else {
            defaultValue = ppa.defaultValue();
            }

        return defaultValue;
    }

    private void handleArrayField(
        final PropertiesPopulatorAnnotationForField<E>  propertiesPopulatorAnnotation,
        final StringBuilder                             prefix,
        final int                                       prefixLength
        ) throws ArrayIndexOutOfBoundsException, PropertiesPopulatorRuntimeException
    {
        final Field        field        = propertiesPopulatorAnnotation.getField();
        final List<String> stringValues = getStringValues( prefix, prefixLength, field.getName() );


        final boolean fieldIsAccessible = field.isAccessible();

        try {
            field.setAccessible( true );

            Object array = field.get( this.bean );
            final PropertiesPopulatorSetter<E,Field> propertiesPopulatorSetter = propertiesPopulatorAnnotation.getPropertiesPopulatorSetter();

            final int       length          = stringValues.size();
            final Class<?>  arrayType       = field.getType();
            final Class<?>  componentType   = arrayType.getComponentType();

            if( (array == null) || (length != Array.getLength( array )) ) {
                array = Array.newInstance( componentType, length );
                field.set( this.bean, array );
                }

            populateArray( array, arrayType, length, stringValues, propertiesPopulatorSetter, componentType );
            }
        catch( IllegalArgumentException | IllegalAccessException e ) {
            // ignore !
            LOGGER.warn( "Cannot set field:" + field, e );
            }
        finally {
            field.setAccessible( fieldIsAccessible );
            }
    }

    private void populateArray( //
            final Object array,
            final Class<?> arrayType,
            final int length,
            final List<String> stringValues,
            final PropertiesPopulatorSetter<E,?> propertiesPopulatorSetter,
            final Class<?> componentType
            )
    {
        for( int i = 0; i < length; i ++ ) {
            try {
                propertiesPopulatorSetter.setArrayEntry( array, i, stringValues.get( i ), componentType );
                }
            catch( final ConvertCantNotHandleTypeException e ) {
                throw new PopulatorException(
                        "Bad type for field",
                        propertiesPopulatorSetter.getFieldOrMethod(),
                        arrayType,
                        e
                        );
                }
            }
    }

    private List<String> getStringValues( //
        StringBuilder prefix, //
        final int prefixLength, //
        final String name //
        )
    {
        final List<String>  values    = new ArrayList<>();

        // TODO: handle default values ???

        if( prefix == null ) {
            assert prefixLength == 0;

            prefix = new StringBuilder();
            }

        // Put arrays values in a list of strings
        for(int i=0;;i++ ) {
            prefix.setLength( prefixLength );
            prefix.append(  name );
            prefix.append( '.' );
            prefix.append( i );

            final String strValue = this.properties.getProperty( prefix.toString() );

            if( strValue == null ) {
                break;
                }
            else {
                values.add( strValue );
                }
            }
        return values;
    }
}
