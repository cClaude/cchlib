package com.googlecode.cchlib.util.properties;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PropertiesPersistentAnnotationForMethodImpl<E> //
    extends AbstractPropertiesPersistentAnnotation<E,Method> //
        implements PropertiesPersistentAnnotationForMethod<E>
{
    private final Method method;
    private final String attributeName;

    PropertiesPersistentAnnotationForMethodImpl( //
        final Persistent persistent, //
        final Method method, //
        final String attributeName //
        )
    {
        super( persistent );

        this.method        = method;
        this.attributeName = attributeName;
    }

    @Override
    public PropertiesPopulatorSetter<E, Method> getPropertiesPopulatorSetter()
    {
        return this;
    }

    @Override
    public void setValue( final E bean, final String strValue, final Class<?> type ) throws IllegalArgumentException, IllegalAccessException,
            ConvertCantNotHandleTypeException, PropertiesPopulatorException, InvocationTargetException
    {
        final Object swingObject = method.invoke( bean );

        setValue( swingObject, strValue );
    }

    @Override
    public void setArrayEntry( final Object array, final int index, final String strValue, final Class<?> type ) throws ArrayIndexOutOfBoundsException,
            IllegalArgumentException, ConvertCantNotHandleTypeException, PropertiesPopulatorException
    {
        throw new PersistentException( "@Persistent does not handle array" );
    }

    @Override
    public Method getMethodOrField()
    {
        return method;
    }

    @Override
    public Method getGetter()
    {
        return method;
    }

    @Override
    public Method getSetter()
    {
        return method;
    }

    @Override
    public String getAttributeName()
    {
        return attributeName;
    }

}
