package com.googlecode.cchlib.util.populator;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import com.googlecode.cchlib.lang.reflect.SerializableField;
import com.googlecode.cchlib.lang.reflect.SerializableMethod;

//Not public
final class FieldOrMethod implements Serializable
{
    private static final long serialVersionUID = 1L;
    private SerializableMethod method;
    private SerializableField field;

    public FieldOrMethod( final Member methodOrField )
    {
        if( methodOrField instanceof Method ) {
            this.method = new SerializableMethod( (Method)methodOrField );
            this.field  = null;
        } else {
            this.method = null;
            this.field  = new SerializableField( (Field)methodOrField );
        }
    }

    public FieldOrMethod( final Method method )
    {
        this.method = new SerializableMethod( method );
        this.field  = null;
    }

    public FieldOrMethod( final Field field )
    {
        this.method = null;
        this.field  = new SerializableField( field );
    }

    public Method getMethod()
    {
        return this.method.getMethod();
    }

    public Field getField()
    {
        return this.field.getField();
    }

    public Object getFieldOrMethod()
    {
        if( this.method != null ) {
            return this.method.getMethod();
        } else {
            return this.field.getField();
        }
    }
}
