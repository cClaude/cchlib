package com.googlecode.cchlib.util.properties;

import java.lang.reflect.Field;

public interface PropertiesPopulatorAnnotationForField<E> //
    extends PropertiesPopulatorAnnotation<E,Field>
{
    Field getField();
}
