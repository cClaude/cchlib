package com.googlecode.cchlib.util.properties;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

//NOT public
final class PropertiesPersistentAnnotationForFieldImpl<E> //
    extends AbstractPropertiesPersistentAnnotation<E,Field> //
        implements PropertiesPopulatorSetter<E,Field>, PropertiesPopulatorAnnotationForField<E>
{
    private final Field field;

    PropertiesPersistentAnnotationForFieldImpl( final Persistent persistent, final Field field )
    {
        super( persistent );

        this.field = field;
    }

    @Override
    public void setValue( final E bean, final String strValue, final Class<?> type ) throws IllegalArgumentException, IllegalAccessException,
            ConvertCantNotHandleTypeException, PropertiesPopulatorException, InvocationTargetException
    {
        final Object swingObject = field.get( bean );

        setValue( swingObject, strValue );
    }

    @Override
    public void setArrayEntry( final Object array, final int index, final String strValue, final Class<?> type ) throws ArrayIndexOutOfBoundsException,
            IllegalArgumentException, ConvertCantNotHandleTypeException, PropertiesPopulatorException
    {
        throw new PersistentException( "@Persistent does not handle array" );
    }

    @Override
    public Field getMethodOrField()
    {
        return field;
    }

    @Override
    public PropertiesPopulatorSetter<E,Field> getPropertiesPopulatorSetter()
    {
        return this;
    }

    @Override
    public Field getField()
    {
        return field;
    }
}
