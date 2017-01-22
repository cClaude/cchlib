package com.googlecode.cchlib.util.populator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

//NOT public
@SuppressWarnings("squid:S00119") // Type one char only ! Why ?
interface PopulatorSetter<E,METHOD_OR_FIELD extends Member>
{
    /**
     * Set value using a String. Best effort will be done to transform
     * {@code strValue} to giving {@code type}
     *
     * @param bean
     *            Bean to use
     * @param strValue
     *            Value to set
     * @param type
     *            Type to use to transform String.
     *
     * @throws IllegalArgumentException
     *             if any
     * @throws IllegalAccessException
     *             if any
     * @throws ConvertCantNotHandleTypeException
     *             if any
     * @throws PopulatorRuntimeException
     *             if any
     * @throws InvocationTargetException
     *             if any
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck","squid:S1160"})
    void setValue( E bean, String strValue, Class<?> type )
        throws IllegalArgumentException,
               IllegalAccessException,
               ConvertCantNotHandleTypeException,
               PopulatorRuntimeException,
               InvocationTargetException;

    /**
     * NEEDDOC
     *
     * @param array
     *            NEEDDOC
     * @param index
     *            NEEDDOC
     * @param strValue
     *            NEEDDOC
     * @param type
     *            NEEDDOC
     * @throws ArrayIndexOutOfBoundsException
     *             if any
     * @throws IllegalArgumentException
     *             if any
     * @throws ConvertCantNotHandleTypeException
     *             if any
     * @throws PopulatorRuntimeException
     *             if any
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    void setArrayEntry( Object array, int index, String strValue, Class<?> type )
        throws ArrayIndexOutOfBoundsException,
               IllegalArgumentException,
               ConvertCantNotHandleTypeException,
               PopulatorRuntimeException;

    /**
     * return a {@link Method} or a {@link Field} to use to populate value
     * @return a solution to populate value
     */
    METHOD_OR_FIELD getMethodOrField();

    /**
     * return a {@link FieldOrMethod} to use to populate value
     * @return a {@link FieldOrMethod} to use to populate value
     */
    FieldOrMethod getFieldOrMethod();
}
