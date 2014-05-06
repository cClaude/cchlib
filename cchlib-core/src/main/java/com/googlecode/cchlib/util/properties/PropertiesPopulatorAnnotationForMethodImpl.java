package com.googlecode.cchlib.util.properties;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//NOT public
final class PropertiesPopulatorAnnotationForMethodImpl<E> //
    extends AbstractPropertiesPopulatorAnnotation<E,Method> //
        implements PropertiesPopulatorAnnotationForMethod<E>, PropertiesPopulatorSetter<E,Method>
{
    private final Method getter;
    private final Method setter;
    private final String attributeName;

    PropertiesPopulatorAnnotationForMethodImpl( //
        final Populator populator, //
        final Method getter, //
        final Method setter, //
        final String attributeName //
        )
    {
        super( populator );

        this.getter        = getter;
        this.setter        = setter;
        this.attributeName = attributeName;
    }

    @Override
    public void setValue( final E bean, final String strValue, final Class<?> type ) throws IllegalArgumentException, IllegalAccessException,
            ConvertCantNotHandleTypeException, PropertiesPopulatorException, InvocationTargetException
    {
        final Object[] parameters = new Object[] { private_convertStringToObject( strValue, type ) };

        setter.invoke( bean, parameters );
    }

    @Override
    public void setArrayEntry( final Object array, final int index, final String strValue, final Class<?> type ) throws ArrayIndexOutOfBoundsException,
            IllegalArgumentException, ConvertCantNotHandleTypeException, PropertiesPopulatorException
    {
        Array.set( array, index, private_convertStringToObject( strValue, type ) );
    }

    @Override
    public Method getMethodOrField()
    {
        return setter;
    }

    @Override
    public PropertiesPopulatorSetter<E,Method> getPropertiesPopulatorSetter()
    {
        return this;
    }

    @Override
    public Method getGetter()
    {
        return getter;
    }

    @Override
    public Method getSetter()
    {
        return setter;
    }

    @Override
    public String getAttributeName()
    {
        return attributeName;
    }
}
