package com.googlecode.cchlib.util.populator;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.lang.StringHelper;
import com.googlecode.cchlib.lang.reflect.AccessibleRestorer;

//Not public
final class BeanToMap<E>
{
    private static final Logger LOGGER = Logger.getLogger( BeanToMap.class );

    private final E                  bean;
    private final Map<String,String> properties;
    private final StringBuilder      prefix;
    private final int                prefixLength;

    /* package */ BeanToMap(
        final E                  bean,
        final Map<String,String> properties,
        final String             propertiesPrefix
        )
    {
        this.bean       = bean;
        this.properties = properties;

        if( (propertiesPrefix == null) || propertiesPrefix.isEmpty() ) {
            this.prefix       = new StringBuilder();
            this.prefixLength = 0;
        }
        else {
            this.prefix       = new StringBuilder( propertiesPrefix );
            this.prefixLength = this.prefix.length();
        }
    }

    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    /* package */ void loadForField(
        final Map<Field, PopulatorAnnotation<E, Field>> fieldsMap
        ) throws PopulatorRuntimeException
    {
        for( final Entry<Field, PopulatorAnnotation<E, Field>> entry : fieldsMap.entrySet() ) {
            loadDataForField( (PopulatorAnnotationForField<E>)(entry.getValue()) );
        }
    }

    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    /* package */ void loadForMethod(
        final Map<String, PopulatorAnnotation<E, Method>> getterSetterMap
        ) throws PopulatorRuntimeException
    {
        for( final Entry<String, PopulatorAnnotation<E, Method>> entry : getterSetterMap.entrySet() ) {
            loadDataForMethod( (PopulatorAnnotationForMethod<E>)(entry.getValue()) );
        }
    }

    private void loadDataForMethod(
        final PopulatorAnnotationForMethod<E> entryValue
        )
    {
        final Method             method     = entryValue.getGetter();
        final AccessibleRestorer accessible = new AccessibleRestorer( method );

        try {
            final Object result = method.invoke( this.bean );

            if( result != null ) {
                if( method.getReturnType().isArray() ) {
                    handleArrayForMethod( entryValue, result );
                } else if( PopulatorContener.class.isAssignableFrom( method.getReturnType() ) ) {
                    handlePopulatorContenerForMethod( method, result );
                } else {
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
            final String message = "Cannot read from method: " + method
                    + " on object [" + this.bean + "] of type " + this.bean.getClass();

            LOGGER.warn( message, e );
        }
        finally {
            accessible.restore();
        }
    }

    private void handleNonArrayForMethod(
        final PopulatorAnnotationForMethod<E> entryValue,
        final Object                          result
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

    private void handleArrayForMethod(
        final PopulatorAnnotationForMethod<E> entryValue,
        final Object                          values
        )
    {
        final String name = entryValue.getAttributeName();

        setValues( entryValue, values, name );
    }

    private void loadDataForField(
        final PopulatorAnnotationForField<E> entryValue
        )
    {
        final Field              field      = entryValue.getField();
        final AccessibleRestorer accessible = new AccessibleRestorer( field );

        try {
            final Object o = field.get( this.bean );

            if( o != null ) {
                if( field.getType().isArray() ) {
                    handleArrayForField( entryValue, o );
                }
                else if( PopulatorContener.class.isAssignableFrom( field.getType() ) ) {
                    handlePopulatorContenerForField( field, o );
                } else {
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
            accessible.restore();
        }
    }

    private void handleNonArrayForField(
        final PopulatorAnnotationForField<E> entryValue,
        final Object                         value
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

        if( this.prefixLength == 0 ) {
            this.properties.put( field.getName(), strValue );
        }
        else {
            this.prefix.setLength( this.prefixLength );
            this.prefix.append( field.getName() );
            this.properties.put( this.prefix.toString(), strValue );
        }
    }

    private void handleArrayForField(
        final PopulatorAnnotationForField<E> entryValue,
        final Object                         values
        )
    {
        final String name = entryValue.getField().getName();

        setValues( entryValue, values, name );
    }

    private void setValues(
        final PopulatorAnnotation<E,?> propertiesPopulatorAnnotation,
        final Object                   values,
        final String                   name
        )
    {
        // Handle Arrays
       final int length = Array.getLength( values );

        for( int index = 0; index < length; index ++ ) {
            final Object value = Array.get( values, index );
            final String stringValue;

            if( value == null ) {
                stringValue = StringHelper.EMPTY;
            } else {
                stringValue = propertiesPopulatorAnnotation.toString( value );
            }

            setValue( name, index, stringValue );
        }
    }

    private void setValue( final String name, final int index, final String stringValue )
    {
        this.prefix.setLength( this.prefixLength );
        this.prefix.append( name );
        this.prefix.append( '.' );
        this.prefix.append( index );

        this.properties.put( this.prefix.toString(), stringValue );
    }

    private void setValue( final String name, final String stringValue )
    {
        // Handle non arrays
        if( this.prefixLength == 0 ) {
            this.properties.put( name, stringValue );
        }
        else {
            this.prefix.setLength( this.prefixLength );
            this.prefix.append( name );
            this.properties.put( this.prefix.toString(), stringValue );
        }
    }
}
