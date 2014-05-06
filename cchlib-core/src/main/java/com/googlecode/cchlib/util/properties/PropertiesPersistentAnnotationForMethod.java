package com.googlecode.cchlib.util.properties;

import java.lang.reflect.Method;

public interface PropertiesPersistentAnnotationForMethod<E> //
    extends PropertiesPersistentAnnotation<E,Method>, //
            PropertiesPopulatorAnnotationForMethod<E>, //
            PropertiesPopulatorSetter<E, Method> //
{
    // empty
}
