package com.googlecode.cchlib.util.populator;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

/**
 *
 * @param <T> Type of the object to populate
 * @param <A> Type of a class {@link Member} (formally a {@link AnnotatedElement} : {@link Method} or {@link Field})
 */
public interface PopulatorAnnotation<T,A extends AnnotatedElement>
{// Only interface need to be public
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
    PopulatorSetter<T,A> getPropertiesPopulatorSetter();
}
