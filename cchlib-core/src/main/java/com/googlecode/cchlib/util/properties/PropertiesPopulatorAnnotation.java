package com.googlecode.cchlib.util.properties;

import java.lang.reflect.Field;

/* not public */
interface PropertiesPopulatorAnnotation<E>
{
    /**
     * Return annotation value
     */
    boolean isDefaultValueNull();
    /**
     * Return annotation value
     */
    String defaultValue();
    /**
     * Return object value has a String
     */
    String toString( Object o ) throws PropertiesPopulatorException;
    /**
     *
     * @param f
     * @param bean
     * @param strValue
     * @param type
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws ConvertCantNotHandleTypeException
     * @throws PropertiesPopulatorException
     */
    void setValue( Field f, E bean, String strValue, Class<?> type)
        throws IllegalArgumentException,
               IllegalAccessException,
               ConvertCantNotHandleTypeException,
               PropertiesPopulatorException;
    /**
     *
     * @param f
     * @param array
     * @param index
     * @param strValue
     * @param type
     * @throws ArrayIndexOutOfBoundsException
     * @throws IllegalArgumentException
     * @throws ConvertCantNotHandleTypeException
     * @throws PropertiesPopulatorException
     */
    void setArrayEntry( Field f, Object array, int index, String strValue, Class<?> type )
        throws ArrayIndexOutOfBoundsException,
               IllegalArgumentException,
               ConvertCantNotHandleTypeException,
               PropertiesPopulatorException;
}