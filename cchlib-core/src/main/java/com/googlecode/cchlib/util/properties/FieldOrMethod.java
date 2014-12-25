package com.googlecode.cchlib.util.properties;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class FieldOrMethod implements Serializable {

    private static final long serialVersionUID = 1L;
    private Method method;
    private Field field;

    public FieldOrMethod( final Object methodOrField )
    {
        if( methodOrField instanceof Method ) {
            this.method = (Method)methodOrField;
            this.field  = null;
        } else {
            this.method  = null;
            this.field = (Field)methodOrField;
        }
    }

    public FieldOrMethod( final Method method )
    {
        this.method = method;
        this.field  = null;
    }

    public FieldOrMethod( final Field field )
    {
        this.method = null;
        this.field  = field;
    }

    public Method getMethod()
    {
        return this.method;
    }

    public Field getField()
    {
        return this.field;
    }

    public Object getFieldOrMethod()
    {
        if( this.method != null ) {
            return this.method;
        } else {
            return this.field;
        }
    }

}
