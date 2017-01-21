package com.googlecode.cchlib.util.populator;

import java.lang.reflect.Method;

//NOT public
interface PropertiesPersistentAnnotationForMethod<E>
    extends PropertiesPersistentAnnotation<E,Method>,
            PropertiesPopulatorAnnotationForMethod<E>,
            PropertiesPopulatorSetter<E, Method>
{
    // empty
}
