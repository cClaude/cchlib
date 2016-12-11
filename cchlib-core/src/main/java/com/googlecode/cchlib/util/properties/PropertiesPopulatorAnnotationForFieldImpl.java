package com.googlecode.cchlib.util.properties;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

//NOT public
final class PropertiesPopulatorAnnotationForFieldImpl<E> //
    extends AbstractPropertiesPopulatorAnnotation<E,Field> //
        implements PropertiesPopulatorAnnotationForField<E>,
                   PropertiesPopulatorSetter<E,Field>
{
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
        this.field.set( bean, convertStringToObject( strValue, type ) );
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
