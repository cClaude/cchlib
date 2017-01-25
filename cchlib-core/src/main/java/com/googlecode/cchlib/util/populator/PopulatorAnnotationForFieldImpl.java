package com.googlecode.cchlib.util.populator;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import org.apache.log4j.Logger;

//NOT public
final class PopulatorAnnotationForFieldImpl<E>
    extends AbstractPopulatorAnnotation<E,Field>
        implements PopulatorAnnotationForField<E>,
                   PopulatorSetter<E,Field>
{
    private static final Logger LOGGER = Logger.getLogger( AbstractPopulatorAnnotation.class );

    private final Field field;

    PopulatorAnnotationForFieldImpl( final Populator populator, final Field field )
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
               PopulatorRuntimeException
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
                 PopulatorRuntimeException
    {
        Array.set( array, index, convertStringToObject( strValue, type ) );
    }

    @Override
    public Field getMethodOrField()
    {
        return this.field;
    }

    @Override
    public PopulatorSetter<E,Field> getPropertiesPopulatorSetter()
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

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "PopulatorAnnotationForFieldImpl [field=" );
        builder.append( this.field );
        builder.append( "]" );
        return builder.toString();
    }
}
