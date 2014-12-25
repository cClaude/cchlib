package com.googlecode.cchlib.util.properties;

import java.lang.reflect.InvocationTargetException;

//NOT public
interface PropertiesPopulatorSetter<E,METHOD_OR_FIELD> {
    void setValue( E bean, String strValue, Class<?> type ) //
            throws IllegalArgumentException, IllegalAccessException, //
                ConvertCantNotHandleTypeException, PropertiesPopulatorException, //
                InvocationTargetException;

        void setArrayEntry( Object array, int index, String strValue, Class<?> type ) //
            throws ArrayIndexOutOfBoundsException, IllegalArgumentException, //
                ConvertCantNotHandleTypeException, PropertiesPopulatorException;

        METHOD_OR_FIELD getMethodOrField();
        FieldOrMethod   getFieldOrMethod();
}
