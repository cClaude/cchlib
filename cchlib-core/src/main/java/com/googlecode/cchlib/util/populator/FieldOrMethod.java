package com.googlecode.cchlib.util.populator;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import javax.annotation.Nonnull;
import com.googlecode.cchlib.lang.reflect.SerializableField;
import com.googlecode.cchlib.lang.reflect.SerializableMethod;

/**
 * A {@link Serializable} object that contain a {@link Field}
 * or a {@link Method}
 */
public final class FieldOrMethod implements Serializable
{
    private static final long serialVersionUID = 1L;

    private final SerializableMethod method;
    private final SerializableField field;

    /**
     * Create {@link FieldOrMethod} for a {@link Method}
     *
     * @param method
     *            The method
     */
    public FieldOrMethod( @Nonnull final Method method )
    {
        if( method == null ) {
            throw new NullPointerException();
        }
        this.method = new SerializableMethod( method );
        this.field  = null;
    }

    /**
     * Create {@link FieldOrMethod} for a {@link Field}
     *
     * @param field
     *            The field
     */
    public FieldOrMethod( @Nonnull final Field field )
    {
        if( field == null ) {
            throw new NullPointerException();
        }
        this.method = null;
        this.field  = new SerializableField( field );
    }

    /**
     * Returns the method or null if contain a field
     * @return the method or null if contain a field
     */
    public Method getMethod()
    {
        return this.method.getMethod();
    }

    /**
     * Returns the field or null if contain a method
     * @return the field or null if contain a method
     */
    public Field getField()
    {
        return this.field.getField();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@link Method#toString()} or {@link Field#toString()}
     */
    @Override
    public String toString()
    { // Required to build PopulatorException message
        return getMember().toString();
    }

    /**
     * Returns the field or the method has a {@link Member}
     * @return the field or the method has a {@link Member}
     */
    @Nonnull
    public Member getMember()
    {
        if( this.method != null ) {
            return this.method.getMethod();
        } else {
            return this.field.getField();
        }
    }
}
