package com.googlecode.cchlib.util.populator;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import org.apache.log4j.Logger;

//NOT public
final class PropertiesPopulatorAnnotationForFieldImpl<E>
    extends AbstractPropertiesPopulatorAnnotation<E,Field>
        implements PropertiesPopulatorAnnotationForField<E>,
                   PropertiesPopulatorSetter<E,Field>
{
    private static final Logger LOGGER = Logger.getLogger( AbstractPropertiesPopulatorAnnotation.class );

    private final Field field;

    PropertiesPopulatorAnnotationForFieldImpl( final Populator populator, final Field field )
    {
        super( populator );

        this.field = field;
    }

    @Override
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public void setValue( final E bean, final String strValue, final Class<?> type )
        throws IllegalArgumentException,
               IllegalAccessException,
               ConvertCantNotHandleTypeException,
               PropertiesPopulatorRuntimeException
    {
        try {
            this.field.set( bean, convertStringToObject( strValue, type ) );
        } catch( final NumberFormatException e ) {
            final String message = "Can not set field \"" + this.field + "\" with value \"" + strValue + '"';

            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( message, e );
            } else {
                LOGGER.warn( message + " : " + e.getMessage() );
            }
        }
    }

    @Override
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public void setArrayEntry(
        final Object   array,
        final int      index,
        final String   strValue,
        final Class<?> type
        ) throws ArrayIndexOutOfBoundsException,
                 IllegalArgumentException,
                 ConvertCantNotHandleTypeException,
                 PropertiesPopulatorRuntimeException
    {
        Array.set( array, index, convertStringToObject( strValue, type ) );
    }

    @Override
    public Field getMethodOrField()
    {
        return this.field;
    }

    @Override
    public PropertiesPopulatorSetter<E,Field> getPropertiesPopulatorSetter()
    {
        return this;
    }

    @Override
    public Field getField()
    {
        return this.field;
    }

    @Override
    public FieldOrMethod getFieldOrMethod()
    {
        return new FieldOrMethod( this.field );
    }
}
