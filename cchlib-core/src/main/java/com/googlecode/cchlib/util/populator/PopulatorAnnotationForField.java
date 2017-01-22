package com.googlecode.cchlib.util.populator;

import java.lang.reflect.Field;

//Not public
interface PopulatorAnnotationForField<E>
    extends PopulatorAnnotation<E,Field>
{
    Field getField();
}
