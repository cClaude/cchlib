package com.googlecode.cchlib.util.properties;

import java.lang.reflect.Field;

//Not public
interface PropertiesPopulatorAnnotationForField<E> //
    extends PropertiesPopulatorAnnotation<E,Field>
{
    Field getField();
}
