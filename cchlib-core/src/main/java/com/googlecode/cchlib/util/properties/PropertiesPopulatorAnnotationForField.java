package com.googlecode.cchlib.util.properties;

import java.lang.reflect.Field;

/**
 * NEEDDOC
 *
 * @param <E> NEEDDOC
 */
public interface PropertiesPopulatorAnnotationForField<E> //
    extends PropertiesPopulatorAnnotation<E,Field>
{
    /**
     * NEEDDOC
     * @return NEEDDOC
     */
    Field getField();
}
