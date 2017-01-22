package com.googlecode.cchlib.util.populator;

import java.lang.reflect.Method;

//NOT public
interface PersistentAnnotationForMethod<E>
    extends PersistentAnnotation<E,Method>,
            PopulatorAnnotationForMethod<E>,
            PopulatorSetter<E, Method>
{
    // empty
}
