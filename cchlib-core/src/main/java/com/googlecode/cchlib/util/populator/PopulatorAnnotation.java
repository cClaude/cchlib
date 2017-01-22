package com.googlecode.cchlib.util.populator;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

/**
 *
 * @param <E> Type of the object to populate
 * @param <METHOD_OR_FIELD> Type {@link Method} or {@link Field}
 */
@SuppressWarnings("squid:S00119") // naming convention
//NOT public
interface PopulatorAnnotation<E,METHOD_OR_FIELD extends Member>
{
    /**
     * Return annotation value
     * @return true if default value is be null
     */
    boolean isDefaultValueNull();

    /**
     * Return annotation value
     * @return annotation value
     */
    String defaultValue();

    /**
     * Return object value has a String
     *
     * @param o object to convert
     * @return object value has a String
     * @throws PopulatorRuntimeException if any
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    String toString( Object o ) throws PopulatorRuntimeException;

    /**
     * Retrieve setter for this entry
     * @return a {@link PopulatorSetter} for this entry
     */
    PopulatorSetter<E,METHOD_OR_FIELD> getPropertiesPopulatorSetter();
}
