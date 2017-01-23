package com.googlecode.cchlib.util.populator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

//NOT public
final class PersistentAnnotationForFieldImpl<E>
    extends AbstractPersistentAnnotation<E,Field>
        implements PopulatorSetter<E,Field>,
                   PopulatorAnnotationForField<E>
{
    private final Field field;

    PersistentAnnotationForFieldImpl(
        final Persistent         persistent,
        final PersistentResolver resolver,
        final Field              field
        )
    {
        super( persistent, resolver );

        this.field = field;
    }

    @Override
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public void setValue( final E bean, final String strValue, final Class<?> type )
        throws IllegalArgumentException,
               IllegalAccessException,
               ConvertCantNotHandleTypeException,
               PopulatorRuntimeException,
               InvocationTargetException
    {
        final Object swingObject = this.field.get( bean );

        setValue( swingObject, strValue );
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
        throw new PersistentException( "@Persistent does not handle array" );
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
}
