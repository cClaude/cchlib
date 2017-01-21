package com.googlecode.cchlib.util.populator;

import java.lang.reflect.Method;

//NOT public
interface PropertiesPopulatorAnnotationForMethod<E>
    extends PropertiesPopulatorAnnotation<E,Method>
{
    Method getGetter();
    Method getSetter();
    String getAttributeName();
}
