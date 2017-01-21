package com.googlecode.cchlib.util.populator;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//NOT public
final class PropertiesPopulatorAnnotationForMethodImpl<E>
    extends AbstractPropertiesPopulatorAnnotation<E,Method>
        implements  PropertiesPopulatorAnnotationForMethod<E>,
                    PropertiesPopulatorSetter<E,Method>
{
    private final Method getter;
    private final Method setter;
    private final String attributeName;

    PropertiesPopulatorAnnotationForMethodImpl(
        final Populator populator,
        final Method getter,
        final Method setter,
        final String attributeName
        )
    {
        super( populator );

        this.getter        = getter;
        this.setter        = setter;
        this.attributeName = attributeName;
    }

    @Override
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public void setValue( final E bean, final String strValue, final Class<?> type )
        throws IllegalArgumentException,
               IllegalAccessException,
               ConvertCantNotHandleTypeException,
               PropertiesPopulatorRuntimeException,
               InvocationTargetException
    {
        final Object[] parameters = new Object[] { convertStringToObject( strValue, type ) };

        this.setter.invoke( bean, parameters );
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
    public Method getMethodOrField()
    {
        return this.setter;
    }

    @Override
    public PropertiesPopulatorSetter<E,Method> getPropertiesPopulatorSetter()
    {
        return this;
    }

    @Override
    public Method getGetter()
    {
        return this.getter;
    }

    @Override
    public Method getSetter()
    {
        return this.setter;
    }

    @Override
    public String getAttributeName()
    {
        return this.attributeName;
    }

    @Override
    public FieldOrMethod getFieldOrMethod()
    {
        return new FieldOrMethod( this.setter );
    }
}
