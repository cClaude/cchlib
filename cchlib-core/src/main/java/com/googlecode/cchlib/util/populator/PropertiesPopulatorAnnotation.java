package com.googlecode.cchlib.util.populator;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

/**
 *
 * @param <E> Type of the object to populate
 * @param <METHOD_OR_FIELD> Type {@link Method} or {@link Field}
 */
@SuppressWarnings("squid:S00119")
//NOT public
interface PropertiesPopulatorAnnotation<E,METHOD_OR_FIELD extends Member>
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
     * @throws PropertiesPopulatorRuntimeException if any
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    String toString( Object o ) throws PropertiesPopulatorRuntimeException;

    /**
     * Retrieve setter for this entry
     * @return a {@link PropertiesPopulatorSetter} for this entry
     */
    PropertiesPopulatorSetter<E,METHOD_OR_FIELD> getPropertiesPopulatorSetter();
}
