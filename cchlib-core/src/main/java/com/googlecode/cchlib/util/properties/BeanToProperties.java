package com.googlecode.cchlib.util.properties;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.lang.StringHelper;

//Not public
final class BeanToProperties<E>
{
    private static final Logger LOGGER = Logger.getLogger( BeanToProperties.class );

    private final E bean;
    private final Properties properties;

    private final StringBuilder prefix;
    private final int prefixLength;

    /* package */ BeanToProperties(
        final E                                            bean,
        final Properties                                   properties,
        final String                                       propertiesPrefix
        )
    {
        this.bean        = bean;
        this.properties  = properties;

        if( (propertiesPrefix == null) || propertiesPrefix.isEmpty() ) {
            prefix       = new StringBuilder();
            prefixLength = 0;
            }
        else {
            prefix       = new StringBuilder( propertiesPrefix );
            prefixLength = prefix.length();
            }
    }

    /* package */ void loadForField( final Map<Field, PropertiesPopulatorAnnotation<E, Field>> fieldsMap ) throws PropertiesPopulatorRuntimeException
    {
        for( final Entry<Field, PropertiesPopulatorAnnotation<E, Field>> entry : fieldsMap.entrySet() ) {
            final PropertiesPopulatorAnnotationForField<E> entryValue = (PropertiesPopulatorAnnotationForField<E>)(entry.getValue());

            loadDataForField( entryValue );
            }
    }

    /* package */ void loadForMethod( final Map<String, PropertiesPopulatorAnnotation<E, Method>> getterSetterMap ) throws PropertiesPopulatorRuntimeException
    {
        for( final Entry<String, PropertiesPopulatorAnnotation<E, Method>> entry : getterSetterMap.entrySet() ) {
            final PropertiesPopulatorAnnotationForMethod<E> entryValue = (PropertiesPopulatorAnnotationForMethod<E>)(entry.getValue());

            loadDataForMethod( entryValue );
            }
    }

    private void loadDataForMethod( //
        final PropertiesPopulatorAnnotationForMethod<E> entryValue
        )
    {
        final Method  method             = entryValue.getGetter();
        final boolean methodIsAccessible = method.isAccessible();

        try {
            method.setAccessible( true );

            final Object result = method.invoke( bean);

            if( result != null ) {
                if( method.getReturnType().isArray() ) {
                    handleArrayForMethod( entryValue, result );
                    }
                else if( PopulatorContener.class.isAssignableFrom( method.getReturnType() ) ) {
                    handlePopulatorContenerForMethod( method, result );
                    }
                else {
                    handleNonArrayForMethod( entryValue, result );
                    }
                }
            else {
                // Ignore null entries
                if( LOGGER.isTraceEnabled() ) {
                    LOGGER.trace( "Ignore null value from method " + method );
                    }
                }
            }
        catch( IllegalArgumentException | IllegalAccessException | InvocationTargetException e ) {
            // ignore !
            LOGGER.warn( "Cannot read method:" + method, e );
            }
        finally {
            method.setAccessible( methodIsAccessible );
            }
    }

    private void handleNonArrayForMethod( //
            final PropertiesPopulatorAnnotationForMethod<E> entryValue, //
            final Object result //
            )
    {
        final String name        = entryValue.getAttributeName();
        final String stringValue = entryValue.toString( result );

        setValue( name, stringValue );
    }

    private void handlePopulatorContenerForMethod( final Method method, final Object o )
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }

    private void handleArrayForMethod( //
        final PropertiesPopulatorAnnotationForMethod<E> entryValue, //
        final Object values //
        )
    {
        final String name = entryValue.getAttributeName();

        setValues( entryValue, values, name );
    }

    private void loadDataForField( //
        final PropertiesPopulatorAnnotationForField<E> entryValue
        )
    {
        final Field   field             = entryValue.getField();
        final boolean fieldIsAccessible = field.isAccessible();

        try {
            field.setAccessible( true );

            final Object o = field.get( bean );

            if( o != null ) {
                if( field.getType().isArray() ) {
                    handleArrayForField( entryValue, o );
                    }
                else if( PopulatorContener.class.isAssignableFrom( field.getType() ) ) {
                    handlePopulatorContenerForField( field, o );
                    }
                else {
                    handleNonArrayForField( entryValue, o );
                    }
                }
            else {
                // Ignore null entries
                if( LOGGER.isTraceEnabled() ) {
                    LOGGER.trace( "Ignore null value from field " + field );
                    }
                }
            }
        catch( IllegalArgumentException | IllegalAccessException e ) {
            // ignore !
            LOGGER.warn( "Cannot read field:" + field, e );
            }
        finally {
            field.setAccessible( fieldIsAccessible );
            }
    }

    private void handleNonArrayForField(
        final PropertiesPopulatorAnnotationForField<E> entryValue,
        final Object value
        )
    {
        final String name        = entryValue.getField().getName();
        final String stringValue = entryValue.toString( value );

        setValue( name, stringValue );
    }

    private void handlePopulatorContenerForField(
        final Field  field,
        final Object objectValue
        )
    {
        final String strValue = PopulatorContener.class.cast( objectValue ).getConvertToString();

        if( prefixLength == 0 ) {
            properties.put( field.getName(), strValue );
            }
        else {
            prefix.setLength( prefixLength );
            prefix.append( field.getName() );
            properties.put( prefix.toString(), strValue );
            }
    }

    private void handleArrayForField(
        final PropertiesPopulatorAnnotationForField<E> entryValue,
        final Object values
        )
    {
        final String name = entryValue.getField().getName();

        setValues( entryValue, values, name );
    }

    private void setValues( //
            final PropertiesPopulatorAnnotation<E,?> propertiesPopulatorAnnotation, //
            final Object values, //
            final String name //
            )
    {
        // Handle Arrays
       final int length = Array.getLength( values );

        for( int i = 0; i < length; i ++ ) {
            final Object value = Array.get( values, i );
            final String stringValue;

            if( value == null ) {
                stringValue = StringHelper.EMPTY;
                }
            else {
                stringValue =  propertiesPopulatorAnnotation.toString( value );
                }

            setValue( name, i, stringValue );
            }
    }

    private void setValue( final String name, final int i, final String stringValue )
    {
        prefix.setLength( prefixLength );
        prefix.append( name );
        prefix.append( '.' );
        prefix.append( i );

        properties.put( prefix.toString(), stringValue );
    }

    private void setValue( final String name, final String stringValue )
    {
        // Handle non arrays
        if( prefixLength == 0 ) {
            properties.put( name, stringValue );
            }
        else {
            prefix.setLength( prefixLength );
            prefix.append( name );
            properties.put( prefix.toString(), stringValue );
            }
    }
}

