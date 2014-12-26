package com.googlecode.cchlib.util.properties;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//NOT public
interface PropertiesPopulatorSetter<E,METHOD_OR_FIELD> {
    
    /**
     * Set value using a String. Best effort will be done to transform
     * <code>strValue</code> to giving <code>type</code>
     * 
     * @param bean      Bean to use
     * @param strValue  Value to set
     * @param type      Type to use to transform String.
     * 
     * @throws IllegalArgumentException if any
     * @throws IllegalAccessException if any
     * @throws ConvertCantNotHandleTypeException TODOC
     * @throws PropertiesPopulatorException TODOC
     * @throws InvocationTargetException if any
     */
    void setValue( E bean, String strValue, Class<?> type ) //
            throws IllegalArgumentException, IllegalAccessException, //
                ConvertCantNotHandleTypeException, PropertiesPopulatorException, //
                InvocationTargetException;

    void setArrayEntry( Object array, int index, String strValue, Class<?> type ) //
            throws ArrayIndexOutOfBoundsException, IllegalArgumentException, //
                ConvertCantNotHandleTypeException, PropertiesPopulatorException;

    /** return a {@link Method} or a {@link Field} to use to populate value */
    METHOD_OR_FIELD getMethodOrField();

    /** return a {@link FieldOrMethod} to use to populate value */
    FieldOrMethod getFieldOrMethod();
}
