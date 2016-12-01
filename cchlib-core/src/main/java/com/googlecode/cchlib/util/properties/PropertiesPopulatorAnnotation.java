package com.googlecode.cchlib.util.properties;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * NEEDDOC
 *
 * @param <E> Type of the object to populate
 * @param <METHOD_OR_FIELD> Type {@link Method} or {@link Field}
 */
//NOT public
@SuppressWarnings("squid:S00119")
interface PropertiesPopulatorAnnotation<E,METHOD_OR_FIELD>
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
