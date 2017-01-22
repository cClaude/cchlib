package com.googlecode.cchlib.util.populator;

import java.lang.reflect.Method;

//NOT public
interface PopulatorAnnotationForMethod<E>
    extends PopulatorAnnotation<E,Method>
{
    Method getGetter();
    Method getSetter();
    String getAttributeName();
}
