package com.googlecode.cchlib.util.populator;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//NOT public
final class PopulatorAnnotationForMethodImpl<E>
    extends AbstractPopulatorAnnotation<E,Method>
        implements  PopulatorAnnotationForMethod<E>,
                    PopulatorSetter<E,Method>
{
    private final Method getter;
    private final Method setter;
    private final String attributeName;

    PopulatorAnnotationForMethodImpl(
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
               PopulatorRuntimeException,
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
                 PopulatorRuntimeException
    {
        Array.set( array, index, convertStringToObject( strValue, type ) );
    }

    @Override
    public Method getMethodOrField()
    {
        return this.setter;
    }

    @Override
    public PopulatorSetter<E,Method> getPropertiesPopulatorSetter()
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

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "PopulatorAnnotationForMethodImpl [attributeName=" );
        builder.append( this.attributeName );
        builder.append( ", getter=" );
        builder.append( this.getter );
        builder.append( ", setter=" );
        builder.append( this.setter );
        builder.append( "]" );
        return builder.toString();
    }
}
