package com.googlecode.cchlib.util.populator;

import java.lang.reflect.Field;

//Not public
interface PropertiesPopulatorAnnotationForField<E> //
    extends PropertiesPopulatorAnnotation<E,Field>
{
    Field getField();
}
